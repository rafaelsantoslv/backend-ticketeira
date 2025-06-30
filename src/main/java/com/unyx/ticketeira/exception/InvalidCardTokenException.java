package com.unyx.ticketeira.exception;

public class InvalidCardTokenException extends PaymentException {
  public InvalidCardTokenException(String message) {
    super(message);
  }

  public InvalidCardTokenException(String message, Throwable cause) {
    super(message, cause);
  }
}
