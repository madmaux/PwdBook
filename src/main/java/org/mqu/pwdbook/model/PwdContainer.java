package org.mqu.pwdbook.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class PwdContainer implements Serializable {

  private static final long serialVersionUID = 1L;

  private String Name;
  private String Comment;
  private List<Pwd> Passwords;

  public PwdContainer() {
    super();
  }

  public PwdContainer(String name, String comment, List<Pwd> passwords) {
    super();
    Name = name;
    Comment = comment;
    Passwords = passwords.stream().collect(Collectors.toList());
  }

  public PwdContainer(PwdContainer aPwdContainer) {
    this(aPwdContainer.getName(), aPwdContainer.getComment(), aPwdContainer.getPassword());
  }

  public String getName() {
    return Name;
  }

  public void setName(String name) {
    Name = name;
  }

  public String getComment() {
    return Comment;
  }

  public void setComment(String comment) {
    Comment = comment;
  }

  public List<Pwd> getPassword() {
    return Passwords;
  }

  public void setPassword(List<Pwd> passwords) {
    Passwords = passwords;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((Comment == null) ? 0 : Comment.hashCode());
    result = prime * result + ((Name == null) ? 0 : Name.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PwdContainer other = (PwdContainer) obj;
    if (Comment == null) {
      if (other.Comment != null)
        return false;
    } else if (!Comment.equals(other.Comment))
      return false;
    if (Name == null) {
      if (other.Name != null)
        return false;
    } else if (!Name.equals(other.Name))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "PwdContainer [Name=" + Name + ", Comment=" + Comment + ", Passwords=" + Passwords + "]";
  }
}
