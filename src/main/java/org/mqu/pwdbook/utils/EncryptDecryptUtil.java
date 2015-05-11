package org.mqu.pwdbook.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;

public final class EncryptDecryptUtil {

  public static SecretKey generatekey(String key) throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
    String myEncryptionKey = key;
    String myEncryptionScheme = "DESede";
    byte[] arrayBytes = myEncryptionKey.getBytes("utf-8");
    KeySpec ks = new DESedeKeySpec(arrayBytes);
    SecretKeyFactory skf = SecretKeyFactory.getInstance(myEncryptionScheme);
    return skf.generateSecret(ks);
  }

  public static String encrypt(String message, SecretKey key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
      IllegalBlockSizeException, BadPaddingException {
    Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
    cipher.init(Cipher.ENCRYPT_MODE, key);
    String encryptedString = Base64.encodeBase64String(cipher.doFinal(message.getBytes()));
    return encryptedString;
  }

  public static String decrypt(String message, SecretKey key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
      IllegalBlockSizeException, BadPaddingException {
    Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
    cipher.init(Cipher.DECRYPT_MODE, key);
    String decryptedString = new String(cipher.doFinal(Base64.decodeBase64(message)));
    return decryptedString;
  }
}