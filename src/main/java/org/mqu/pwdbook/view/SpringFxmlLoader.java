package org.mqu.pwdbook.view;

import javafx.fxml.FXMLLoader;
import javafx.util.Callback;
import org.mqu.pwdbook.controller.PwdBookController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by mauro on 06/06/15.
 */
public class SpringFxmlLoader {
  private static final AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext("META-INF/Beans-Config.xml");

  public Object load(String url) {
    try (InputStream fxmlStream = SpringFxmlLoader.class.getClassLoader().getResourceAsStream(url)) {
      System.err.println(SpringFxmlLoader.class.getClassLoader().getResourceAsStream(url));
      FXMLLoader loader = new FXMLLoader();
      loader.setControllerFactory(new Callback<Class<?>, Object>() {
        @Override
        public Object call(Class<?> clazz) {
          return applicationContext.getBean(clazz);
        }
      });
      return loader.load(fxmlStream);
    } catch (IOException ioException) {
      throw new RuntimeException(ioException);
    }
  }
}
