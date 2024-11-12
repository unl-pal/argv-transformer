package com.imiFirewall.terminal;

/**
 * The state machine for a modifier key. Can be pressed, released, locked, and so on.
 */
class ModifierKey {
  private int mState;
  private static final int UNPRESSED = 0;
  private static final int PRESSED = 1;
  private static final int RELEASED = 2;
  private static final int USED = 3;
  private static final int LOCKED = 4;
}