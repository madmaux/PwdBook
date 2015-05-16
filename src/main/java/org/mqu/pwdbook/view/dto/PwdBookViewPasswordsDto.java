package org.mqu.pwdbook.view.dto;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PwdBookViewPasswordsDto {
  private final StringProperty Name;
  private final StringProperty Comment;
  private final StringProperty User;
  private final StringProperty Password;

  public PwdBookViewPasswordsDto() {
    this(null, null, null, null);
  }

  public PwdBookViewPasswordsDto(String name, String comment, String user, String password) {
    super();
    this.Name = new SimpleStringProperty(name);
    this.Comment = new SimpleStringProperty(comment);
    this.User = new SimpleStringProperty(user);
    this.Password = new SimpleStringProperty(password);
  }

  public void setName(String name) {
    this.Name.set(name);
  }

  public StringProperty getName() {
    return Name;
  }

  public void setComment(String comment) {
    this.Comment.set(comment);
  }

  public StringProperty getComment() {
    return Comment;
  }

  public void setUser(String user) {
    this.User.set(user);
  }

  public StringProperty getUser() {
    return User;
  }

  public void setPassword(String password) {
    this.Password.set(password);
  }

  public StringProperty getPassword() {
    return Password;
  }
}
