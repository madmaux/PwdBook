package org.mqu.pwdbook.view;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
public class PwdBookView extends Application{
  private static final SpringFxmlLoader loader = new SpringFxmlLoader();

  public static void main(String[] args) {
    SpringApplication.run(PwdBookView.class, args);
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    Parent root = (Parent) loader.load("windows/PwdBookView.fxml");
    Scene scene = new Scene(root);
    // scene.getStylesheets().add("windows/css/application.css");
    stage.setScene(scene);
    stage.setTitle("Password Book");
    stage.setResizable(false);
    stage.show();
  }
}
