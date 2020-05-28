package com.askfm.demo.exception;

public class BlackListedUserException extends RuntimeException {
  public BlackListedUserException(String message) {
    super(message);
  }
}
