package org.mqu.pwdbook.exceptions;

public abstract class BaseException extends Exception {
  private static final long serialVersionUID = 7079445554579192351L;

  public BaseException() {
    super();
  }

  public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public BaseException(String message, Throwable cause) {
    super(message, cause);
  }

  public BaseException(String message) {
    super(message);
  }

  public BaseException(Throwable cause) {
    super(cause);
  }

}
