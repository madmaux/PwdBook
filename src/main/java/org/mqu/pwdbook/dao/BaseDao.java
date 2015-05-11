package org.mqu.pwdbook.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class BaseDao {
  private static final Logger logger = LogManager.getLogger(BaseDao.class);
  protected static String FILE_PATH;
  protected static String FILE_NAME;
  protected static final String FILE_TEMPLATE = "{\"Name\":\"\",\"Comment\":\"\",\"Passwords\":[{\"Name\":\"\",\"Usr\":\"\",\"Pwd\":\"\",\"Comment\":\"\"}]}";

  static {
    try (InputStream in = BaseDao.class.getClassLoader().getResourceAsStream("Setup.properties");) {
      logger.info("Loading config file");
      Properties prop = new Properties();
      prop.load(in);
      FILE_PATH = prop.getProperty("File.Path");
      FILE_NAME = prop.getProperty("File.Name");
      logger.info("Loaded path: {}, loaded file name: {}", FILE_PATH, FILE_NAME);
    } catch (IOException e) {
      logger.error("Can't open config file", e);
    }
  }

  public BaseDao() {
    super();
  }

  protected BufferedReader open() throws IOException {
    this.checkFile();
    logger.info("Open file: {}", FILE_PATH + "/" + FILE_NAME);
    BufferedReader br = new BufferedReader(new FileReader(FILE_PATH + "/" + FILE_NAME));
    return br;
  }

  protected void checkFile() throws IOException {
    logger.info("Check file exists, if not then create");
    if (Files.exists(Paths.get(FILE_PATH + "/" + FILE_NAME)))
      return;
    logger.info("File not found, creating start file");
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH + "/" + FILE_NAME));) {
      bw.write(FILE_TEMPLATE);
      bw.flush();
    }
  }
}
