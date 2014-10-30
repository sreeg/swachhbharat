package com.sree.swachhbharat;

import java.io.Serializable;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;

public class MyArea implements Serializable
{
  /**
   * 
   */
  private static final long serialVersionUID = -8952966409388669129L;
  private String areaName, thoroughfare, subThoroughfare;
  private String cityName, subLocality, subAdminArea;
  private List<LatLng> positions;

  public LatLng getAreaPosition()
  {
    return positions.get(0);
  }

  public String getAreaName()
  {
    return areaName;
  }

  public void setAreaName(String areaName)
  {
    this.areaName = areaName;
  }

  public String getCityName()
  {
    return cityName;
  }

  public void setCityName(String cityName)
  {
    this.cityName = cityName;
  }

  public String getSubLocality()
  {
    return subLocality;
  }

  public void setSubLocality(String subLocality)
  {
    this.subLocality = subLocality;
  }

  public String getSubAdminArea()
  {
    return subAdminArea;
  }

  public void setSubAdminArea(String subAdminArea)
  {
    this.subAdminArea = subAdminArea;
  }

  public String getThoroughfare()
  {
    return thoroughfare;
  }

  public void setThoroughfare(String throughfare)
  {
    this.thoroughfare = throughfare;
  }

  public String getSubThoroughfare()
  {
    return subThoroughfare;
  }

  public void setSubThoroughfare(String subThoroughfare)
  {
    this.subThoroughfare = subThoroughfare;
  }

  public List<LatLng> getPosition()
  {
    return positions;
  }

  public void setPosition(List<LatLng> position)
  {
    this.positions = position;
  }
}
