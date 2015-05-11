package org.mqu.pwdbook.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PwdBookView extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    Parent root = FXMLLoader.load(PwdBookView.class.getClassLoader().getResource("windows/PwdBookView.fxml"));
    Scene scene = new Scene(root);
    // scene.getStylesheets().add("windows/css/application.css");
    stage.setScene(scene);
    stage.setTitle("Password Book");
    stage.setResizable(false);
    stage.show();
  }

}
