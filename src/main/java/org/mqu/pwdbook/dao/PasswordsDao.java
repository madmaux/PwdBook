package org.mqu.pwdbook.dao;

import org.mqu.pwdbook.exceptions.DBExceptions;
import org.mqu.pwdbook.model.Pwd;
import org.mqu.pwdbook.model.PwdContainer;

public interface PasswordsDao {
  public boolean save(PwdContainer pwdContainer) throws DBExceptions.DAOObjectNotSaved;

  public PwdContainer findAll() throws DBExceptions.DAOObjectNotFoundException;

  public Pwd findByName(String name) throws DBExceptions.DAOObjectNotFoundException;
}
