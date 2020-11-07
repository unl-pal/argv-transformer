package com.imiFirewall.terminal;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import android.util.Log;

/**
 * Renders text into a screen. Contains all the terminal-specific knowlege and state. Emulates a
 * subset of the X Window System xterm terminal, which in turn is an emulator for a subset of the
 * Digital Equipment Corporation vt100 terminal. Missing functionality: text attributes (bold,
 * underline, reverse video, color) alternate screen cursor key and keypad escape sequences.
 */
class TerminalEmulator {

  /**
   * The cursor row. Numbered 0..mRows-1.
   */
  private int mCursorRow;

  /**
   * The cursor column. Numbered 0..mColumns-1.
   */
  private int mCursorCol;

  /**
   * The number of character rows in the terminal screen.
   */
  private int mRows;

  /**
   * The number of character columns in the terminal screen.
   */
  private int mColumns;

  /**
   * Used to send data to the remote process. Needed to implement the various "report" escape
   * sequences.
   */
  private final FileOutputStream mTermOut;

  /**
   * Stores the characters that appear on the screen of the emulated terminal.
   */
  private final Screen mScreen;

  /**
   * Keeps track of the current argument of the current escape sequence. Ranges from 0 to
   * MAX_ESCAPE_PARAMETERS-1. (Typically just 0 or 1.)
   */
  private int mArgIndex;

  /**
   * The number of parameter arguments. This name comes from the ANSI standard for terminal escape
   * codes.
   */
  private static final int MAX_ESCAPE_PARAMETERS = 16;

  /**
   * Holds the arguments of the current escape sequence.
   */
  private final int[] mArgs = new int[MAX_ESCAPE_PARAMETERS];

  // Escape processing states:

  /**
   * Escape processing state: Not currently in an escape sequence.
   */
  private static final int ESC_NONE = 0;

  /**
   * Escape processing state: Have seen an ESC character
   */
  private static final int ESC = 1;

  /**
   * Escape processing state: Have seen ESC POUND
   */
  private static final int ESC_POUND = 2;

  /**
   * Escape processing state: Have seen ESC and a character-set-select char
   */
  private static final int ESC_SELECT_LEFT_PAREN = 3;

  /**
   * Escape processing state: Have seen ESC and a character-set-select char
   */
  private static final int ESC_SELECT_RIGHT_PAREN = 4;

  /**
   * Escape processing state: ESC [
   */
  private static final int ESC_LEFT_SQUARE_BRACKET = 5;

  /**
   * Escape processing state: ESC [ ?
   */
  private static final int ESC_LEFT_SQUARE_BRACKET_QUESTION_MARK = 6;

  /**
   * True if the current escape sequence should continue, false if the current escape sequence
   * should be terminated. Used when parsing a single character.
   */
  private boolean mContinueSequence;

  /**
   * The current state of the escape sequence state machine.
   */
  private int mEscapeState;

  /**
   * Saved state of the cursor row, Used to implement the save/restore cursor position escape
   * sequences.
   */
  private int mSavedCursorRow;

  /**
   * Saved state of the cursor column, Used to implement the save/restore cursor position escape
   * sequences.
   */
  private int mSavedCursorCol;

  // DecSet booleans

  /**
   * This mask indicates 132-column mode is set. (As opposed to 80-column mode.)
   */
  private static final int K_132_COLUMN_MODE_MASK = 1 << 3;

  /**
   * This mask indicates that origin mode is set. (Cursor addressing is relative to the absolute
   * screen size, rather than the currently set top and bottom margins.)
   */
  private static final int K_ORIGIN_MODE_MASK = 1 << 6;

  /**
   * Holds multiple DECSET flags. The data is stored this way, rather than in separate booleans, to
   * make it easier to implement the save-and-restore semantics. The various k*ModeMask masks can be
   * used to extract and modify the individual flags current states.
   */
  private int mDecFlags;

  /**
   * Saves away a snapshot of the DECSET flags. Used to implement save and restore escape sequences.
   */
  private int mSavedDecFlags;

  // Modes set with Set Mode / Reset Mode

  /**
   * True if insert mode (as opposed to replace mode) is active. In insert mode new characters are
   * inserted, pushing existing text to the right.
   */
  private boolean mInsertMode;

  /**
   * An array of tab stops. mTabStop[i] is true if there is a tab stop set for column i.
   */
  private boolean[] mTabStops;

  // The margins allow portions of the screen to be locked.

  /**
   * The top margin of the screen, for scrolling purposes. Ranges from 0 to mRows-2.
   */
  private int mTopMargin;

  /**
   * The bottom margin of the screen, for scrolling purposes. Ranges from mTopMargin + 2 to mRows.
   * (Defines the first row after the scrolling region.
   */
  private int mBottomMargin;

  /**
   * True if the next character to be emitted will be automatically wrapped to the next line. Used
   * to disambiguate the case where the cursor is positioned on column mColumns-1.
   */
  private boolean mAboutToAutoWrap;

  /**
   * Used for debugging, counts how many chars have been processed.
   */
  private int mProcessedCharCount;

  /**
   * Foreground color, 0..7, mask with 8 for bold
   */
  private int mForeColor;

  /**
   * Background color, 0..7, mask with 8 for underline
   */
  private int mBackColor;

  private boolean mInverseColors;

  private boolean mbKeypadApplicationMode;

  public void updateSize(int columns, int rows) {
    if (mRows == rows && mColumns == columns) {
      return;
    }

    // Save transcript text for replay after size update.
    String transcriptText = mScreen.getTranscriptText();
    mScreen.resize(columns, rows, mForeColor, mBackColor);

    mRows = rows;
    mColumns = columns;
    mTopMargin = 0;
    mBottomMargin = mRows;
    mCursorRow = 0;
    mCursorCol = 0;
    mAboutToAutoWrap = false;

    boolean[] oldTabStops = mTabStops;
    // Reinitialize with default tab stops.
    setDefaultTabStops();
    // Update with any existing tab stops that fit into the current display.
    System.arraycopy(oldTabStops, 0, mTabStops, 0, Math.min(oldTabStops.length, mTabStops.length));

    // Replay old transcript text.
    // TODO(damonkohler): Theoretically, this whole bit could be replaced with this simple append:
    // append(transcriptText.toCharArray(), 0, transcriptText.length());
    // However, it seems that the transcript text includes a lot of empty lines...
    int end = transcriptText.length() - 1;
    while ((end >= 0) && transcriptText.charAt(end) == '\n') {
      end--;
    }
    for (int i = 0; i <= end; i++) {
      byte c = (byte) transcriptText.charAt(i);
      if (c == '\n') {
        setCursorCol(0);
        doLinefeed();
      } else {
        emit(c);
      }
    }
  }

  /**
   * Accept bytes (typically from the pty) and process them.
   *
   * @param buffer
   *          a byte array containing the bytes to be processed
   * @param offset
   *          the first index of the array to process
   * @param length
   *          the number of bytes in the array to process
   */
  public void append(byte[] buffer, int offset, int length) {
    for (int i = 0; i < length; i++) {
      byte c = buffer[offset + i];
      try {
        if (Terminal.LOG_CHARACTERS_FLAG) {
          if (c < 32 || c > 126) {
            c = ' ';
          }
          Log.w(Terminal.TAG, "'" + c + "' (" + Integer.toString(c) + ")");
        }
        process(c);
        mProcessedCharCount++;
      } catch (Exception e) {
        Log.e(Terminal.TAG, "Exception while processing character "
            + Integer.toString(mProcessedCharCount) + " code " + Integer.toString(c), e);
      }
    }
  }

  private void doEscLSBQuest(byte c) {
    int mask = getDecFlagsMask(getArg0(0));
    switch (c) {
      case 'h': // Esc [ ? Pn h - DECSET
        mDecFlags |= mask;
        break;

      case 'l': // Esc [ ? Pn l - DECRST
        mDecFlags &= ~mask;
        break;

      case 'r': // Esc [ ? Pn r - restore
        mDecFlags = (mDecFlags & ~mask) | (mSavedDecFlags & mask);
        break;

      case 's': // Esc [ ? Pn s - save
        mSavedDecFlags = (mSavedDecFlags & ~mask) | (mDecFlags & mask);
        break;

      default:
        parseArg(c);
        break;
    }

    // 132 column mode
    if ((mask & K_132_COLUMN_MODE_MASK) != 0) {
      // We don't actually set 132 cols, but we do want the
      // side effect of clearing the screen and homing the cursor.
      blockClear(0, 0, mColumns, mRows);
      setCursorRowCol(0, 0);
    }

    // origin mode
    if ((mask & K_ORIGIN_MODE_MASK) != 0) {
      // Home the cursor.
      setCursorPosition(0, 0);
    }
  }

  private void doEsc(byte c) {
    switch (c) {
      case '#':
        continueSequence(ESC_POUND);
        break;

      case '(':
        continueSequence(ESC_SELECT_LEFT_PAREN);
        break;

      case ')':
        continueSequence(ESC_SELECT_RIGHT_PAREN);
        break;

      case '7': // DECSC save cursor
        mSavedCursorRow = mCursorRow;
        mSavedCursorCol = mCursorCol;
        break;

      case '8': // DECRC restore cursor
        setCursorRowCol(mSavedCursorRow, mSavedCursorCol);
        break;

      case 'D': // INDEX
        doLinefeed();
        break;

      case 'E': // NEL
        setCursorCol(0);
        doLinefeed();
        break;

      case 'F': // Cursor to lower-left corner of screen
        setCursorRowCol(0, mBottomMargin - 1);
        break;

      case 'H': // Tab set
        mTabStops[mCursorCol] = true;
        break;

      case 'M': // Reverse index
        if (mCursorRow == 0) {
          mScreen.blockCopy(0, mTopMargin + 1, mColumns, mBottomMargin - (mTopMargin + 1), 0,
              mTopMargin);
          blockClear(0, mBottomMargin - 1, mColumns);
        } else {
          mCursorRow--;
        }

        break;

      case 'N': // SS2
        unimplementedSequence(c);
        break;

      case '0': // SS3
        unimplementedSequence(c);
        break;

      case 'P': // Device control string
        unimplementedSequence(c);
        break;

      case 'Z': // return terminal ID
        sendDeviceAttributes();
        break;

      case '[':
        continueSequence(ESC_LEFT_SQUARE_BRACKET);
        break;

      case '=': // DECKPAM
        mbKeypadApplicationMode = true;
        break;

      case '>': // DECKPNM
        mbKeypadApplicationMode = false;
        break;

      default:
        unknownSequence(c);
        break;
    }
  }

  private void selectGraphicRendition() {
    for (int i = 0; i <= mArgIndex; i++) {
      int code = mArgs[i];
      if (code < 0) {
        if (mArgIndex > 0) {
          continue;
        } else {
          code = 0;
        }
      }
      if (code == 0) { // reset
        mInverseColors = false;
        mForeColor = 7;
        mBackColor = 0;
      } else if (code == 1) { // bold
        mForeColor |= 0x8;
      } else if (code == 4) { // underscore
        mBackColor |= 0x8;
      } else if (code == 7) { // inverse
        mInverseColors = true;
      } else if (code >= 30 && code <= 37) { // foreground color
        mForeColor = (mForeColor & 0x8) | (code - 30);
      } else if (code >= 40 && code <= 47) { // background color
        mBackColor = (mBackColor & 0x8) | (code - 40);
      } else {
        if (Terminal.LOG_UNKNOWN_ESCAPE_SEQUENCES) {
          Log.w(Terminal.TAG, String.format("SGR unknown code %d", code));
        }
      }
    }
  }

  private void setCursorPosition(int x, int y) {
    int effectiveTopMargin = 0;
    int effectiveBottomMargin = mRows;
    if ((mDecFlags & K_ORIGIN_MODE_MASK) != 0) {
      effectiveTopMargin = mTopMargin;
      effectiveBottomMargin = mBottomMargin;
    }
    int newRow =
        Math.max(effectiveTopMargin, Math.min(effectiveTopMargin + y, effectiveBottomMargin - 1));
    int newCol = Math.max(0, Math.min(x, mColumns - 1));
    setCursorRowCol(newRow, newCol);
  }

  /**
   * Process the next ASCII character of a parameter.
   *
   * @param c
   *          The next ASCII character of the paramater sequence.
   */
  private void parseArg(byte c) {
    if (c >= '0' && c <= '9') {
      if (mArgIndex < mArgs.length) {
        int oldValue = mArgs[mArgIndex];
        int thisDigit = c - '0';
        int value;
        if (oldValue >= 0) {
          value = oldValue * 10 + thisDigit;
        } else {
          value = thisDigit;
        }
        mArgs[mArgIndex] = value;
      }
      continueSequence();
    } else if (c == ';') {
      if (mArgIndex < mArgs.length) {
        mArgIndex++;
      }
      continueSequence();
    } else {
      unknownSequence(c);
    }
  }

  /**
   * Send an ASCII character to the screen.
   *
   * @param c
   *          the ASCII character to display.
   */
  private void emit(byte c) {
    boolean autoWrap = autoWrapEnabled();

    if (autoWrap) {
      if (mCursorCol == mColumns - 1 && mAboutToAutoWrap) {
        mScreen.setLineWrap(mCursorRow);
        mCursorCol = 0;
        if (mCursorRow + 1 < mBottomMargin) {
          mCursorRow++;
        } else {
          scroll();
        }
      }
    }

    if (mInsertMode) { // Move character to right one space
      int destCol = mCursorCol + 1;
      if (destCol < mColumns) {
        mScreen.blockCopy(mCursorCol, mCursorRow, mColumns - destCol, 1, destCol, mCursorRow);
      }
    }

    mScreen.set(mCursorCol, mCursorRow, c, getForeColor(), getBackColor());

    if (autoWrap) {
      mAboutToAutoWrap = (mCursorCol == mColumns - 1);
    }

    mCursorCol = Math.min(mCursorCol + 1, mColumns - 1);
  }
}
