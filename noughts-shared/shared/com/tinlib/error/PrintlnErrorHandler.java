package com.tinlib.error;

public class PrintlnErrorHandler implements ErrorHandler {
  @Override
  public void error(String message, Object[] args) {
    System.err.println("[ERROR]: " + String.format(message, args));
  }
}
