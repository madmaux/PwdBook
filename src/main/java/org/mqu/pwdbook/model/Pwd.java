package org.mqu.pwdbook.model;

import java.io.Serializable;

public class Pwd implements Serializable {

  private static final long serialVersionUID = 1L;

  private String Name;
  private String Usr;
  private String Pwd;
  private String Comment;

  public Pwd() {
    super();
  }

  public Pwd(String name, String usr, String password, String comment) {
    super();
    Name = name;
    Usr = usr;
    Pwd = password;
    Comment = comment;
  }

  public String getName() {
    return Name;
  }

  public void setName(String name) {
    Name = name;
  }

  public String getUsr() {
    return Usr;
  }

  public void setUsr(String usr) {
    Usr = usr;
  }

  public String getPassword() {
    return Pwd;
  }

  public void setPassword(String password) {
    Pwd = password;
  }

  public String getComment() {
    return Comment;
  }

  public void setComment(String comment) {
    Comment = comment;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((Comment == null) ? 0 : Comment.hashCode());
    result = prime * result + ((Name == null) ? 0 : Name.hashCode());
    result = prime * result + ((Pwd == null) ? 0 : Pwd.hashCode());
    result = prime * result + ((Usr == null) ? 0 : Usr.hashCode());
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
    Pwd other = (Pwd) obj;
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
    if (Pwd == null) {
      if (other.Pwd != null)
        return false;
    } else if (!Pwd.equals(other.Pwd))
      return false;
    if (Usr == null) {
      if (other.Usr != null)
        return false;
    } else if (!Usr.equals(other.Usr))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Pwd [Name=" + Name + ", Usr=" + Usr + ", Pwd=" + Pwd + ", Comment=" + Comment + "]";
  }
}
