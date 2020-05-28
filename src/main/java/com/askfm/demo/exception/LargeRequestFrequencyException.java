package com.askfm.demo.exception;

public class LargeRequestFrequencyException extends RuntimeException {
  public LargeRequestFrequencyException(String message) {
    super(message);
  }
}
