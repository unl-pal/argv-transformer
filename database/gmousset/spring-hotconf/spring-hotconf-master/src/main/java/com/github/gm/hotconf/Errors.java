package com.github.gm.hotconf;

/**
 * @author Gwendal Mousset
 *
 */
public enum Errors {

  /** The property requested is unknown. */
  UNKNOWN_PROPERTY("unknown property: "),
  /** The value is not a number. */
  NOT_A_NUMBER("Not a number: "),
  /** An unexpected error has occured. */
  UNEXPECTED_ERROR("unexpecting error: ");
  
  /** Error message. */
  private String message;
  
  /**
   * Private constructor.
   * @param pMessage The error message.
   */
  private Errors(final String pMessage) {
    this.message = pMessage;
  }

  /**
   * @return The error message.
   */
  public String getMessage() {
    return message;
  }
}
