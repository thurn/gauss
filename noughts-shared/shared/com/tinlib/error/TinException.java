package com.tinlib.error;

public class TinException extends RuntimeException {
  public TinException(String message) {
    super(message);
  }

  public TinException(String message, Object... args) {
    super(String.format(message, args));
  }
}
