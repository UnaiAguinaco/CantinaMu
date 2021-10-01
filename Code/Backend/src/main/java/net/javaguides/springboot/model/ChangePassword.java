package net.javaguides.springboot.model;

public class ChangePassword {

  public ChangePassword() {

  }

  private String oldPassword;
  private String newPassword;
  private String repeatPassword;

  
  /** 
   * @return String
   */
  public String getOldPassword() {
    return oldPassword;
  }

  
  /** 
   * @param oldPassword
   */
  public void setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
  }

  
  /** 
   * @return String
   */
  public String getNewPassword() {
    return newPassword;
  }

  
  /** 
   * @param newPassword
   */
  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

  
  /** 
   * @return String
   */
  public String getRepeatPassword() {
    return repeatPassword;
  }

  
  /** 
   * @param repeatPassword
   */
  public void setRepeatPassword(String repeatPassword) {
    this.repeatPassword = repeatPassword;
  }

}
