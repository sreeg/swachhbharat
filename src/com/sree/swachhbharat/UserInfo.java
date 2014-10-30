package com.sree.swachhbharat;

public class UserInfo
{
  private String sUserName;
  private String sEmail;
  private boolean isFB;
  private boolean isGPlus;

  public String getsUserName()
  {
    return sUserName;
  }

  public void setsUserName(String sUserName)
  {
    this.sUserName = sUserName;
  }

  public String getsEmail()
  {
    return sEmail;
  }

  public void setsEmail(String sEmail)
  {
    this.sEmail = sEmail;
  }

  public boolean isFB()
  {
    return isFB;
  }

  public void setFB(boolean isFB)
  {
    this.isFB = isFB;
  }

  public boolean isGPlus()
  {
    return isGPlus;
  }

  public void setGPlus(boolean isGPlus)
  {
    this.isGPlus = isGPlus;
  }

}
