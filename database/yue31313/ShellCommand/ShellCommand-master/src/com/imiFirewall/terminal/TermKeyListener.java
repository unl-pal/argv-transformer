package com.imiFirewall.terminal;

import android.view.KeyEvent;

/**
 * An ASCII key listener. Supports control characters and escape. Keeps track of the current state
 * of the alt, shift, and control keys.
 */
class TermKeyListener {

  private final ModifierKey mAltKey = new ModifierKey();
  private final ModifierKey mShiftKey = new ModifierKey();
  private final ModifierKey mControlKey = new ModifierKey(); 
  private final ModifierKey mCapKey = new ModifierKey();

  

  public int mapControlChar(int ch) {
      int result = ch;
      if (mControlKey.isActive()) {
          // Search is the control key.
          if (result >= 'a' && result <= 'z') {
              result = (char) (result - 'a' + '\001');
          } else if (result == ' ') {
              result = 0;
          } else if ((result == '[') || (result == '1')) {
              result = 27;
          } else if ((result == '\\') || (result == '.')) {
              result = 28;
          } else if ((result == ']') || (result == '0')) {
              result = 29;
          } else if ((result == '^') || (result == '6')) {
              result = 30; // control-^
          } else if ((result == '_') || (result == '5')) {
              result = 31;
          }
      }

      if (result > -1) {
          mAltKey.adjustAfterKeypress();
          mCapKey.adjustAfterKeypress();
          mControlKey.adjustAfterKeypress();
      }
      return result;
  }
}
