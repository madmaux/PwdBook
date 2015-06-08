package org.mqu.pwdbook.controller.impl;

import static org.mqu.pwdbook.utils.EncryptDecryptUtil.decrypt;
import static org.mqu.pwdbook.utils.EncryptDecryptUtil.encrypt;
import static org.mqu.pwdbook.utils.EncryptDecryptUtil.generatekey;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mqu.pwdbook.controller.PwdBookController;
import org.mqu.pwdbook.dao.PasswordsDao;
import org.mqu.pwdbook.exceptions.DBExceptions;
import org.mqu.pwdbook.exceptions.DBExceptions.ControllerCantRemovePasswordException;
import org.mqu.pwdbook.exceptions.DBExceptions.ControllerCantSaveException;
import org.mqu.pwdbook.model.Pwd;
import org.mqu.pwdbook.model.PwdContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class PwdBookControllerImpl implements PwdBookController {
  @Autowired
  @Qualifier("passwordsDao")
  private PasswordsDao passwordsDao;
  private static final Logger logger = LogManager.getLogger(PwdBookControllerImpl.class);

  private AtomicBoolean flagError = new AtomicBoolean(false);

  @Override
  public Optional<PwdContainer> load(String key) throws DBExceptions.ControllerInvalidKeyException {
    try {
      PwdContainer orgPwdContainer = passwordsDao.findAll();
      PwdContainer pwdContainer = new PwdContainer(orgPwdContainer);
      SecretKey secretKey = generatekey(key);
      pwdContainer.setName(decrypt(pwdContainer.getName(), secretKey));
      pwdContainer.setComment(decrypt(pwdContainer.getComment(), secretKey));
      pwdContainer.getPassword().forEach((pwd) -> {
        try {
          pwd.setName(StringUtils.isEmpty(pwd.getName()) ? "" : decrypt(pwd.getName(), secretKey));
          pwd.setUsr(StringUtils.isEmpty(pwd.getUsr()) ? "" : decrypt(pwd.getUsr(), secretKey));
          pwd.setPassword(StringUtils.isEmpty(pwd.getPassword()) ? "" : decrypt(pwd.getPassword(), secretKey));
          pwd.setComment(StringUtils.isEmpty(pwd.getComment()) ? "" : decrypt(pwd.getComment(), secretKey));
        } catch (NoSuchAlgorithmException | IllegalBlockSizeException e) {
          logger.error("Error while loading for each password", e);
        } catch (InvalidKeyException | NoSuchPaddingException | BadPaddingException e) {
          this.flagError.set(true);
        }
      });
      if (flagError.get()) {
        throw new DBExceptions.ControllerExcpetion();
      }
      return Optional.of(pwdContainer);
    } catch (DBExceptions.DAOObjectNotFoundException | UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeySpecException
        | IllegalBlockSizeException e) {
      logger.error("Error while loading", e);
    } catch (DBExceptions.ControllerExcpetion | InvalidKeyException | NoSuchPaddingException | BadPaddingException e) {
      throw new DBExceptions.ControllerInvalidKeyException("The key entered is invalid", e);
    } finally {
      this.flagError.set(false);
    }
    return Optional.empty();
  }

  @Override
  public boolean save(PwdContainer pwdContainer, String key) throws DBExceptions.ControllerCantSaveException {
    try {
      boolean result = false;
      SecretKey secretKey = generatekey(key);
      pwdContainer.setName(StringUtils.isEmpty(pwdContainer.getName()) ? "" : encrypt(pwdContainer.getName(), secretKey));
      pwdContainer.setComment(StringUtils.isEmpty(pwdContainer.getComment()) ? "" : encrypt(pwdContainer.getComment(), secretKey));
      pwdContainer.getPassword().stream().filter((pwd) -> !this.isEmpty(pwd)).forEach((pwd) -> {
        try {
          pwd.setName(StringUtils.isEmpty(pwd.getName()) ? "" : encrypt(pwd.getName(), secretKey));
          pwd.setUsr(StringUtils.isEmpty(pwd.getUsr()) ? "" : encrypt(pwd.getUsr(), secretKey));
          pwd.setPassword(StringUtils.isEmpty(pwd.getPassword()) ? "" : encrypt(pwd.getPassword(), secretKey));
          pwd.setComment(StringUtils.isEmpty(pwd.getComment()) ? "" : encrypt(pwd.getComment(), secretKey));
        } catch (NoSuchAlgorithmException | IllegalBlockSizeException e) {
          logger.error("Error while saving for each password", e);
        } catch (InvalidKeyException | NoSuchPaddingException | BadPaddingException e) {
          this.flagError.set(true);
        }
      });
      PwdContainer orgPwdContainer = passwordsDao.findAll();
      orgPwdContainer.setComment(pwdContainer.getComment());
      orgPwdContainer.setName(pwdContainer.getName());
      orgPwdContainer.getPassword().clear();
      pwdContainer.getPassword().forEach(pwd-> orgPwdContainer.getPassword().add(pwd));
      if (flagError.get()) {
        throw new DBExceptions.ControllerExcpetion();
      }
      result = passwordsDao.save(orgPwdContainer);
      return result;
    } catch (DBExceptions.DAOObjectNotFoundException |DBExceptions.DAOObjectNotSaved | UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeySpecException | IllegalBlockSizeException e) {
      logger.error("Error while saving", e);
    } catch (DBExceptions.ControllerExcpetion | InvalidKeyException | NoSuchPaddingException | BadPaddingException e) {
      throw new DBExceptions.ControllerCantSaveException("The key entered is invalid", e);
    } finally {
      this.flagError.set(false);
    }
    return false;
  }

  @Override
  public boolean removePassword(PwdContainer pwdContainer, Pwd password, String key) throws ControllerCantRemovePasswordException {
    Pwd pwd = pwdContainer.getPassword().stream().filter(pw -> pw.getName().equals(password.getName())).findFirst().orElse(null);
    pwdContainer.getPassword().remove(pwd);
    boolean result = false;
    try {
      this.save(pwdContainer, key);
      result = true;
    } catch (ControllerCantSaveException e) {
      logger.error("Error while performing deletion off a password", e);
    }
    return result;
  }

  private boolean isEmpty(Pwd pwd) {
    return StringUtils.isEmpty(pwd.getName()) && StringUtils.isEmpty(pwd.getUsr()) && StringUtils.isEmpty(pwd.getPassword())
        && StringUtils.isEmpty(pwd.getComment());
  }
}
