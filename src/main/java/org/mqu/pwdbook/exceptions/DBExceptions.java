package org.mqu.pwdbook.exceptions;

public class DBExceptions {
  public static class ControllerExcpetion extends BaseException {
    private static final long serialVersionUID = -4740753406971325193L;

    public ControllerExcpetion() {
      super();
    }

    public ControllerExcpetion(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
      super(message, cause, enableSuppression, writableStackTrace);
    }

    public ControllerExcpetion(String message, Throwable cause) {
      super(message, cause);
    }

    public ControllerExcpetion(String message) {
      super(message);
    }

    public ControllerExcpetion(Throwable cause) {
      super(cause);
    }
  }

  public static class ControllerCantSaveException extends BaseException {
    private static final long serialVersionUID = -6295432283066410457L;

    public ControllerCantSaveException() {
      super();
    }

    public ControllerCantSaveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
      super(message, cause, enableSuppression, writableStackTrace);
    }

    public ControllerCantSaveException(String message, Throwable cause) {
      super(message, cause);
    }

    public ControllerCantSaveException(String message) {
      super(message);
    }

    public ControllerCantSaveException(Throwable cause) {
      super(cause);
    }

  }

  public static class ControllerInvalidKeyException extends BaseException {
    private static final long serialVersionUID = 5550350572924549615L;

    public ControllerInvalidKeyException() {
      super();
    }

    public ControllerInvalidKeyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
      super(message, cause, enableSuppression, writableStackTrace);
    }

    public ControllerInvalidKeyException(String message, Throwable cause) {
      super(message, cause);
    }

    public ControllerInvalidKeyException(String message) {
      super(message);
    }

    public ControllerInvalidKeyException(Throwable cause) {
      super(cause);
    }
  }

  public static class DAOObjectNotSaved extends BaseException {
    private static final long serialVersionUID = -8185087346227330338L;

    public DAOObjectNotSaved() {
      super();
    }

    public DAOObjectNotSaved(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
      super(message, cause, enableSuppression, writableStackTrace);
    }

    public DAOObjectNotSaved(String message, Throwable cause) {
      super(message, cause);
    }

    public DAOObjectNotSaved(String message) {
      super(message);
    }

    public DAOObjectNotSaved(Throwable cause) {
      super(cause);
    }
  }

  public static class DAOObjectNotFoundException extends BaseException {
    private static final long serialVersionUID = 5423940847315406564L;

    public DAOObjectNotFoundException() {
      super();
    }

    public DAOObjectNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
      super(message, cause, enableSuppression, writableStackTrace);
    }

    public DAOObjectNotFoundException(String message, Throwable cause) {
      super(message, cause);
    }

    public DAOObjectNotFoundException(String message) {
      super(message);
    }

    public DAOObjectNotFoundException(Throwable cause) {
      super(cause);
    }

  }
}
