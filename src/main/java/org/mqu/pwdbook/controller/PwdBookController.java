package org.mqu.pwdbook.controller;

import org.mqu.pwdbook.exceptions.DBExceptions;
import org.mqu.pwdbook.model.PwdContainer;

public interface PwdBookController {
  public PwdContainer load(String key) throws DBExceptions.ControllerInvalidKeyException;

  public boolean save(PwdContainer pwdContainer, String key) throws DBExceptions.ControllerCantSaveException;
}