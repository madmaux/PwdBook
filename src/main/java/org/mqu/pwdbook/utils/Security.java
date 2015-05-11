package org.mqu.pwdbook.utils;

import javax.crypto.SecretKey;

public interface Security {
  SecretKey generatekey(String key);

  String encrypt(String message, SecretKey key);

  String decrypt(String message, SecretKey key);
}