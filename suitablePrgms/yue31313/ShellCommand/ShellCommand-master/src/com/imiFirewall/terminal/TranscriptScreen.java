package com.imiFirewall.terminal;

import android.graphics.Canvas;

/**
 * A TranscriptScreen is a screen that remembers data that's been scrolled. The old data is stored
 * in a ring buffer to minimize the amount of copying that needs to be done. The transcript does its
 * own drawing, to avoid having to expose its internal data structures.
 */
public class TranscriptScreen implements Screen {

  /**
   * The width of the transcript, in characters. Fixed at initialization.
   */
  private int mColumns;

  /**
   * The total number of rows in the transcript and the screen. Fixed at initialization.
   */
  private int mTotalRows;

  /**
   * The number of rows in the active portion of the transcript. Doesn't include the screen.
   */
  private int mActiveTranscriptRows;

  /**
   * Which row is currently the topmost line of the transcript. Used to implement a circular buffer.
   */
  private int mHead;

  /**
   * The number of active rows, includes both the transcript and the screen.
   */
  private int mActiveRows;

  /**
   * The number of rows in the screen.
   */
  private int mScreenRows;

  /**
   * The data for both the screen and the transcript. The first mScreenRows * mLineWidth characters
   * are the screen, the rest are the transcript. The low byte encodes the ASCII character, the high
   * byte encodes the foreground and background colors, plus underline and bold.
   */
  private char[] mData;

  /**
   * The data's stored as color-encoded chars, but the drawing routines require chars, so we need a
   * temporary buffer to hold a row's worth of characters.
   */
  private char[] mRowBuffer;

  /**
   * Flags that keep track of whether the current line logically wraps to the next line. This is
   * used when resizing the screen and when copying to the clipboard or an email attachment
   */

  private boolean[] mLineWrap;

  /**
   * Convert a row value from the public external coordinate system to our internal private
   * coordinate system. External coordinate system: -mActiveTranscriptRows to mScreenRows-1, with
   * the screen being 0..mScreenRows-1 Internal coordinate system: 0..mScreenRows-1 rows of mData
   * are the visible rows. mScreenRows..mActiveRows - 1 are the transcript, stored as a circular
   * buffer.
   *
   * @param row
   *          a row in the external coordinate system.
   * @return The row corresponding to the input argument in the private coordinate system.
   */
  private int externalToInternalRow(int row) {
    if (row < -mActiveTranscriptRows || row >= mScreenRows) {
      throw new IllegalArgumentException("Invalid row: " + row);
    }
    if (row >= 0) {
      return row; // This is a visible row.
    }
    return mScreenRows + ((mHead + mActiveTranscriptRows + row) % mActiveTranscriptRows);
  }

  /**
   * Scroll the screen down one line. To scroll the whole screen of a 24 line screen, the arguments
   * would be (0, 24).
   *
   * @param topMargin
   *          First line that is scrolled.
   * @param bottomMargin
   *          One line after the last line that is scrolled.
   */
  public void scroll(int topMargin, int bottomMargin, int foreColor, int backColor) {
    if (topMargin > bottomMargin - 2 || topMargin > mScreenRows - 2 || bottomMargin > mScreenRows) {
      throw new IllegalArgumentException();
    }

    // Adjust the transcript so that the last line of the transcript
    // is ready to receive the newly scrolled data
    consistencyCheck();
    int expansionRows = Math.min(1, mTotalRows - mActiveRows);
    int rollRows = 1 - expansionRows;
    mActiveRows += expansionRows;
    mActiveTranscriptRows += expansionRows;
    if (mActiveTranscriptRows > 0) {
      mHead = (mHead + rollRows) % mActiveTranscriptRows;
    }
    consistencyCheck();

    // Block move the scroll line to the transcript
    int topOffset = getOffset(topMargin);
    int destOffset = getOffset(-1);
    System.arraycopy(mData, topOffset, mData, destOffset, mColumns);

    int topLine = externalToInternalRow(topMargin);
    int destLine = externalToInternalRow(-1);
    System.arraycopy(mLineWrap, topLine, mLineWrap, destLine, 1);

    // Block move the scrolled data up
    int numScrollChars = (bottomMargin - topMargin - 1) * mColumns;
    System.arraycopy(mData, topOffset + mColumns, mData, topOffset, numScrollChars);
    int numScrollLines = (bottomMargin - topMargin - 1);
    System.arraycopy(mLineWrap, topLine + 1, mLineWrap, topLine, numScrollLines);

    // Erase the bottom line of the scroll region
    blockSet(0, bottomMargin - 1, mColumns, 1, ' ', foreColor, backColor);
    mLineWrap[externalToInternalRow(bottomMargin - 1)] = false;
  }

  private void consistencyCheck() {
    checkPositive(mColumns);
    checkPositive(mTotalRows);
    checkRange(0, mActiveTranscriptRows, mTotalRows);
    if (mActiveTranscriptRows == 0) {
      checkEqual(mHead, 0);
    } else {
      checkRange(0, mHead, mActiveTranscriptRows - 1);
    }
    checkEqual(mScreenRows + mActiveTranscriptRows, mActiveRows);
    checkRange(0, mScreenRows, mTotalRows);

    checkEqual(mTotalRows, mLineWrap.length);
    checkEqual(mTotalRows * mColumns, mData.length);
    checkEqual(mColumns, mRowBuffer.length);
  }

  /**
   * Block copy characters from one position in the screen to another. The two positions can
   * overlap. All characters of the source and destination must be within the bounds of the screen,
   * or else an InvalidParemeterException will be thrown.
   *
   * @param sx
   *          source X coordinate
   * @param sy
   *          source Y coordinate
   * @param w
   *          width
   * @param h
   *          height
   * @param dx
   *          destination X coordinate
   * @param dy
   *          destination Y coordinate
   */
  public void blockCopy(int sx, int sy, int w, int h, int dx, int dy) {
    if (sx < 0 || sx + w > mColumns || sy < 0 || sy + h > mScreenRows || dx < 0
        || dx + w > mColumns || dy < 0 || dy + h > mScreenRows) {
      throw new IllegalArgumentException();
    }
    if (sy <= dy) {
      // Move in increasing order
      for (int y = 0; y < h; y++) {
        int srcOffset = getOffset(sx, sy + y);
        int dstOffset = getOffset(dx, dy + y);
        System.arraycopy(mData, srcOffset, mData, dstOffset, w);
      }
    } else {
      // Move in decreasing order
      for (int y = 0; y < h; y++) {
        int y2 = h - (y + 1);
        int srcOffset = getOffset(sx, sy + y2);
        int dstOffset = getOffset(dx, dy + y2);
        System.arraycopy(mData, srcOffset, mData, dstOffset, w);
      }
    }
  }

  /**
   * Block set characters. All characters must be within the bounds of the screen, or else and
   * InvalidParemeterException will be thrown. Typically this is called with a "val" argument of 32
   * to clear a block of characters.
   *
   * @param sx
   *          source X
   * @param sy
   *          source Y
   * @param w
   *          width
   * @param h
   *          height
   * @param val
   *          value to set.
   */
  public void blockSet(int sx, int sy, int w, int h, int val, int foreColor, int backColor) {
    if (sx < 0 || sx + w > mColumns || sy < 0 || sy + h > mScreenRows) {
      throw new IllegalArgumentException();
    }
    char[] data = mData;
    char encodedVal = encode(val, foreColor, backColor);
    for (int y = 0; y < h; y++) {
      int offset = getOffset(sx, sy + y);
      for (int x = 0; x < w; x++) {
        data[offset + x] = encodedVal;
      }
    }
  }

  private String internalGetTranscriptText(boolean stripColors) {
    StringBuilder builder = new StringBuilder();
    char[] rowBuffer = mRowBuffer;
    char[] data = mData;
    int columns = mColumns;
    for (int row = -mActiveTranscriptRows; row < mScreenRows; row++) {
      int offset = getOffset(row);
      int lastPrintingChar = -1;
      for (int column = 0; column < columns; column++) {
        char c = data[offset + column];
        if (stripColors) {
          c = (char) (c & 0xff);
        }
        if ((c & 0xff) != ' ') {
          lastPrintingChar = column;
        }
        rowBuffer[column] = c;
      }
      if (mLineWrap[externalToInternalRow(row)]) {
        builder.append(rowBuffer, 0, columns);
      } else {
        builder.append(rowBuffer, 0, lastPrintingChar + 1);
        builder.append('\n');
      }
    }
    return builder.toString();
  }
}
