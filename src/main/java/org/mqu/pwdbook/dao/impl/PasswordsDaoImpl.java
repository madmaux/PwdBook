package org.mqu.pwdbook.dao.impl;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mqu.pwdbook.dao.BaseDao;
import org.mqu.pwdbook.dao.PasswordsDao;
import org.mqu.pwdbook.exceptions.DBExceptions;
import org.mqu.pwdbook.model.Pwd;
import org.mqu.pwdbook.model.PwdContainer;

import com.google.gson.Gson;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component("passwordsDao")
public class PasswordsDaoImpl extends BaseDao implements PasswordsDao {
  private static final Logger logger = LogManager.getLogger(PasswordsDaoImpl.class);

  @Override
  public boolean save(PwdContainer pwdContainer) throws DBExceptions.DAOObjectNotSaved {
    boolean result = false;
    String json = (new Gson()).toJson(pwdContainer);
    try (FileWriter writer = new FileWriter(FILE_PATH + "/" + FILE_NAME)) {
      logger.info("Saving at: {}", FILE_PATH + "/" + FILE_NAME);
      writer.write(json);
      result = true;
      logger.info("Saved");
    } catch (IOException e) {
      throw new DBExceptions.DAOObjectNotSaved("Can't save data", e);
    }
    return result;
  }

  @Override
  @Cacheable("pwdContainerCache")
  public PwdContainer findAll() throws DBExceptions.DAOObjectNotFoundException {
    logger.info("Finding all objects");
    try (BufferedReader openedFile = this.open()) {
      PwdContainer pwc = (new Gson()).fromJson(openedFile, PwdContainer.class);
      logger.info("Found objects");
      return pwc;
    } catch (IOException e) {
      throw new DBExceptions.DAOObjectNotFoundException("Could not find all objects", e);
    }
  }

  @Override
  public Pwd findByName(String named) throws DBExceptions.DAOObjectNotFoundException {
    logger.info("Finding: {}", named);
    try (BufferedReader openedFile = this.open()) {
      PwdContainer pwc = (new Gson()).fromJson(openedFile, PwdContainer.class);
      Pwd result = pwc.getPassword().stream().filter(pwd -> pwd.getName().equals(named)).findFirst().orElse(null);
      if (result != null)
        logger.info("Found object");
      return result;
    } catch (IOException e) {
      throw new DBExceptions.DAOObjectNotFoundException("Could not find named objec", e);
    }
  }
}
