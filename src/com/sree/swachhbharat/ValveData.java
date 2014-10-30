package com.sree.swachhbharat;

import java.io.Serializable;

public class ValveData implements Serializable
{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public int getVALVID_INT()
  {
    return (int) Double.parseDouble(VALVID_INT);
  }

  public void setVALVID_INT(String vALVID_INT)
  {
    VALVID_INT = vALVID_INT;
  }

  public int getSUPPLYPATTERN()
  {
    return Integer.parseInt(SUPPLYPATTERN);
  }

  public void setSUPPLYPATTERN(String sUPPLYPATTERN)
  {
    SUPPLYPATTERN = sUPPLYPATTERN;
  }

  public String getLINEMANID()
  {
    return LINEMANID;
  }

  public void setLINEMANID(String lINEMANID)
  {
    LINEMANID = lINEMANID;
  }

  public int getVALVESIZE()
  {
    return Integer.parseInt(VALVESIZE);
  }

  public void setVALVESIZE(String vALVESIZE)
  {
    VALVESIZE = vALVESIZE;
  }

  public String getOPERATIONPOSITION()
  {
    return OPERATIONPOSITION;
  }

  public void setOPERATIONPOSITION(String oPERATIONPOSITION)
  {
    OPERATIONPOSITION = oPERATIONPOSITION;
  }

  public String getOPENINGTIME()
  {
    return OPENINGTIME;
  }

  public void setOPENINGTIME(String oPENINGTIME)
  {
    OPENINGTIME = oPENINGTIME;
  }

  public String getCLOSINGTIME()
  {
    return CLOSINGTIME;
  }

  public void setCLOSINGTIME(String cLOSINGTIME)
  {
    CLOSINGTIME = cLOSINGTIME;
  }

  public String getAREATYPE()
  {
    return AREATYPE;
  }

  public void setAREATYPE(String aREATYPE)
  {
    AREATYPE = aREATYPE;
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

  public String getVALVEID()
  {
    return VALVEID;
  }

  public void setVALVEID(String vALVEID)
  {
    VALVEID = vALVEID;
  }

  public String getVALVETYPE()
  {
    return VALVETYPE;
  }

  public void setVALVETYPE(String vALVETYPE)
  {
    VALVETYPE = vALVETYPE;
  }

  public String getVALVELANDMARK()
  {
    return VALVELANDMARK;
  }

  public void setVALVELANDMARK(String vALVELANDMARK)
  {
    VALVELANDMARK = vALVELANDMARK;
  }

  public String getLOCATIONOFVALVE()
  {
    return LOCATIONOFVALVE;
  }

  public void setLOCATIONOFVALVE(String lOCATIONOFVALVE)
  {
    LOCATIONOFVALVE = lOCATIONOFVALVE;
  }

  public String getSECTIONNAME()
  {
    return SECTIONNAME;
  }

  public void setSECTIONNAME(String sECTIONNAME)
  {
    SECTIONNAME = sECTIONNAME;
  }

  public String getDIVNAME()
  {
    return DIVNAME;
  }

  public void setDIVNAME(String dIVNAME)
  {
    DIVNAME = dIVNAME;
  }

  public String getRESERVOIRNAME()
  {
    return RESERVOIRNAME;
  }

  public void setRESERVOIRNAME(String rESERVOIRNAME)
  {
    RESERVOIRNAME = rESERVOIRNAME;
  }

  public String getAREADCODE()
  {
    return AREADCODE;
  }

  public void setAREADCODE(String aREADCODE)
  {
    AREADCODE = aREADCODE;
  }

  public String getORGDESCRIPTION()
  {
    return ORGDESCRIPTION;
  }

  public void setORGDESCRIPTION(String oRGDESCRIPTION)
  {
    ORGDESCRIPTION = oRGDESCRIPTION;
  }

  public double getLATITUDE()
  {
    return Double.parseDouble(LATITUDE);
  }

  public void setLATITUDE(String lATITUDE)
  {
    LATITUDE = lATITUDE;
  }

  public double getLONGITUDE()
  {
    return Double.parseDouble(LONGITUDE);
  }

  public void setLONGITUDE(String lONGITUDE)
  {
    LONGITUDE = lONGITUDE;
  }

  public String getPARTIALROUNDS()
  {
    return PARTIALROUNDS;
  }

  public void setPARTIALROUNDS(String pARTIALROUNDS)
  {
    PARTIALROUNDS = pARTIALROUNDS;
  }

  private String OPERATIONPOSITION, OPENINGTIME, CLOSINGTIME, AREATYPE, LINEMANNAME, PHONENUMBER, JOBTYPE, VALVEID, VALVETYPE, VALVELANDMARK, LOCATIONOFVALVE, SECTIONNAME,
      DIVNAME, RESERVOIRNAME, AREADCODE, ORGDESCRIPTION, PARTIALROUNDS, VALVID_INT, SUPPLYPATTERN, LINEMANID, VALVESIZE, LATITUDE, LONGITUDE, STATUS;

  public String getSTATUS()
  {
    return STATUS;
  }

  public void setSTATUS(String sTATUS)
  {
    STATUS = sTATUS;
  }

  public enum ValveStatus
  {
    OPEN("O"), CLOSED("C"), NOSUPPLY("N");

    private final String statusCode;

    private ValveStatus(String s)
    {
      statusCode = s;
    }

    public String getStatusCode()
    {
      return statusCode;
    }

  }

}
