package com.askfm.demo.exception;

public class LoanNotFoundException extends RuntimeException {
  public LoanNotFoundException(String message) {
    super(message);
  }
}
