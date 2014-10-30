package com.sree.swachhbharat;

import java.io.Serializable;

public class LineManData implements Serializable
{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public String getLINEMANID()
  {
    return LINEMANID;
  }

  public void setLINEMANID(String lINEMANID)
  {
    LINEMANID = lINEMANID;
  }

  public String getLINEMANNAME()
  {
    return LINEMANNAME;
  }

  public void setLINEMANNAME(String lINEMANNAME)
  {
    LINEMANNAME = lINEMANNAME;
  }

  public String getPHONENUMBER()
  {
    return PHONENUMBER;
  }

  public void setPHONENUMBER(String pHONENUMBER)
  {
    PHONENUMBER = pHONENUMBER;
  }

  public String getJOBTYPE()
  {
    return JOBTYPE;
  }

  public void setJOBTYPE(String jOBTYPE)
  {
    JOBTYPE = jOBTYPE;
  }

  private String LINEMANNAME, PHONENUMBER, JOBTYPE, LINEMANID;

}
