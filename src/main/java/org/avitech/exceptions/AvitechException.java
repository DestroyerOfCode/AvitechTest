package org.avitech.exceptions;

public class AvitechException extends RuntimeException {

  public AvitechException() {
    super();
  }

  public AvitechException(String message) {
    super(message);
  }

  public AvitechException(String message, Throwable cause) {
    super(message, cause);
  }

  public AvitechException(Throwable cause) {
    super(cause);
  }
}
