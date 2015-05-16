package org.mqu.pwdbook.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialogs;
import org.mqu.pwdbook.controller.PwdBookController;
import org.mqu.pwdbook.exceptions.DBExceptions;
import org.mqu.pwdbook.exceptions.DBExceptions.ControllerCantRemovePasswordException;
import org.mqu.pwdbook.model.Pwd;
import org.mqu.pwdbook.model.PwdContainer;
import org.mqu.pwdbook.view.dto.PwdBookViewPasswordsDto;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PwdBookViewController implements Initializable {
  @FXML
  private TextField txtName;
  @FXML
  private TextField txtComments;
  @FXML
  private PasswordField pwdfldKey;
  @FXML
  private TableView<PwdBookViewPasswordsDto> tblPwds;
  @FXML
  private TableColumn<PwdBookViewPasswordsDto, String> clmnName;
  @FXML
  private TableColumn<PwdBookViewPasswordsDto, String> clmnUser;
  @FXML
  private TableColumn<PwdBookViewPasswordsDto, String> clmnPassword;
  @FXML
  private TableColumn<PwdBookViewPasswordsDto, String> clmnComments;

  private static final Logger logger = LogManager.getLogger(PwdBookViewController.class);
  private PwdContainer pwdContainer;
  private PwdBookController pwdBookController;
  private ObservableList<PwdBookViewPasswordsDto> passwords = FXCollections.observableArrayList();

  @FXML
  public void btnFindAllClick(ActionEvent event) {
    try {
      this.pwdContainer = this.pwdBookController.load(this.pwdfldKey.getText());
      this.txtName.setText(this.pwdContainer.getName());
      this.txtComments.setText(this.pwdContainer.getComment());
      this.passwords.removeAll(this.passwords);
      this.pwdContainer.getPassword().forEach(pwd -> {
        this.passwords.add(new PwdBookViewPasswordsDto(pwd.getName(), pwd.getComment(), pwd.getUsr(), pwd.getPassword()));
      });
    } catch (DBExceptions.ControllerInvalidKeyException e) {
      logger.error("Error while getting all passwords", e);
      Action response = Dialogs.create().owner(null).title("Error").message("Error: " + e.getMessage()).showError();
    }
  }

  @FXML
  public void btnSaveClick(ActionEvent event) {
    try {
      this.pwdContainer.setName(this.txtName.getText());
      this.pwdContainer.setComment(this.txtComments.getText());
      this.pwdContainer.getPassword().clear();
      this.passwords.forEach(pwd -> {
        this.pwdContainer.getPassword().add(new Pwd(pwd.getName().get(), pwd.getUser().get(), pwd.getPassword().get(), pwd.getComment().get()));
      });
      this.pwdBookController.save(pwdContainer, this.pwdfldKey.getText());
      this.btnFindAllClick(event);
    } catch (DBExceptions.ControllerCantSaveException e) {
      logger.error("Error while saving passwords", e);
      Action response = Dialogs.create().owner(null).title("Error").message("Error: " + e.getMessage()).showError();
    }
  }

  @FXML
  public void btnCancelCLick(ActionEvent event) {
    this.btnFindAllClick(event);
  }

  @FXML
  public void mnuAddPwdClick(ActionEvent event) {
    this.passwords.add(new PwdBookViewPasswordsDto("Change me", "Change me", "Change me", "Change me"));
  }

  @FXML
  public void mnuRemPwdClick(ActionEvent event) {
    PwdBookViewPasswordsDto pwdDto = this.tblPwds.getSelectionModel().getSelectedItem();
    try {
      this.pwdBookController.removePassword(pwdContainer, new Pwd(pwdDto.getName().get(), pwdDto.getUser().get(), pwdDto.getPassword().get(), pwdDto
          .getComment().get()), this.pwdfldKey.getText());
      this.btnFindAllClick(event);
    } catch (ControllerCantRemovePasswordException e) {
      logger.error(e);
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    try (AbstractApplicationContext contxt = new ClassPathXmlApplicationContext("META-INF/Beans-Config.xml");) {
      this.pwdBookController = (PwdBookController) contxt.getBean("pwdBookController");
    }
    this.clmnName.setCellFactory(TextFieldTableCell.forTableColumn());
    this.clmnName.setCellValueFactory(it -> it.getValue().getName());
    this.clmnUser.setCellFactory(TextFieldTableCell.forTableColumn());
    this.clmnUser.setCellValueFactory(it -> it.getValue().getUser());
    this.clmnPassword.setCellFactory(TextFieldTableCell.forTableColumn());
    this.clmnPassword.setCellValueFactory(it -> it.getValue().getPassword());
    this.clmnComments.setCellFactory(TextFieldTableCell.forTableColumn());
    this.clmnComments.setCellValueFactory(it -> it.getValue().getComment());

    this.tblPwds.setItems(passwords);
    this.tblPwds.setEditable(true);
  }

}
