package com.sree.swachhbharat;

import java.util.List;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;

import android.graphics.Color;
import android.util.FloatMath;

public class CommonUtils
{
  public static int COLOR_WHITE = Color.parseColor("#FFFFFF");
  public static int COLOR_DARK_WHITE = Color.parseColor("#f7f7f7");
  public static int COLOR_BLACK = Color.parseColor("#000000");
  public static int COLOR_LIGHT_BLACK = Color.parseColor("#333333");
  /*
   * public static int CLOSE_COLOR = Color.parseColor("#d3e9d3");//#D84A38
   * public static int NO_SUPPLY_COLOR = Color.parseColor("#e9e2ea"); public
   * static int OPEN_COLOR = Color.parseColor("#f9d8dc");//#28B779
   */
  /*
   * public static int OPEN_COLOR = Color.parseColor("#28B779");//#61C227 public
   * static int OPEN_COLOR_DARK = Color.parseColor("#17A668");//#53a621 public
   * static int CLOSE_COLOR = Color.parseColor("#D84A38");//#F9472A public
   * static int CLOSE_COLOR_DARK = Color.parseColor("#b93524");//#f93919 public
   * static int NO_SUPPLY_COLOR = Color.parseColor("#852B99");//#202A33 public
   * static int NO_SUPPLY_COLOR_DARK = Color.parseColor("#731C87");//#192129
   */
  public static int OPEN_COLOR = Color.parseColor("#61C227");// #61C227
  public static int OPEN_COLOR_DARK = Color.parseColor("#53a621");// #53a621
  public static int OPEN_COLOR_A = Color.argb(60, 97, 194, 39);
  public static int OPEN_COLOR_DARK_A = Color.argb(60, 83, 166, 33);
  public static int CLOSE_COLOR = Color.parseColor("#F9472A");// #F9472A
  public static int CLOSE_COLOR_DARK = Color.parseColor("#e92607");// #f93919
  public static int NO_SUPPLY_COLOR = Color.parseColor("#FFBA15");// #FFBA15
  public static int NO_SUPPLY_COLOR_DARK = Color.parseColor("#f2ab00");// #f2ab00

  /*
   * public static int TEXT_COLOR_SECONDARY = Color.parseColor("#333333");
   * public static int TEXT_COLOR_TERITIARY = Color.parseColor("#666666");
   * public static int TEXT_COLOR_PRIMARY = Color.parseColor("#000000");
   */
  public static int TEXT_COLOR_PRIMARY = Color.parseColor("#FFFFFF");
  public static int TEXT_COLOR_SECONDARY = Color.parseColor("#f7f7f7");
  public static int TEXT_COLOR_TERITIARY = Color.parseColor("#EEEEEE");

  public static String capitalizeString(String string)
  {
    if (string != null)
    {
      char[] chars = string.toLowerCase().toCharArray();
      boolean found = false;
      for (int i = 0; i < chars.length; i++)
      {
        if (!found && Character.isLetter(chars[i]))
        {
          chars[i] = Character.toUpperCase(chars[i]);
          found = true;
        }
        else if (Character.isWhitespace(chars[i]) || (chars[i] == '.') || (chars[i] == '\''))
        { // You can add other chars here
          found = false;
        }
      }
      return String.valueOf(chars);
    }
    else
    {
      return "";
    }
  }

  public static double gps2m(float lat_a, float lng_a, float lat_b, float lng_b)
  {
    float pk = (float) (180 / 3.14169);

    float a1 = lat_a / pk;
    float a2 = lng_a / pk;
    float b1 = lat_b / pk;
    float b2 = lng_b / pk;

    float t1 = FloatMath.cos(a1) * FloatMath.cos(a2) * FloatMath.cos(b1) * FloatMath.cos(b2);
    float t2 = FloatMath.cos(a1) * FloatMath.sin(a2) * FloatMath.cos(b1) * FloatMath.sin(b2);
    float t3 = FloatMath.sin(a1) * FloatMath.sin(b1);
    double tt = Math.acos(t1 + t2 + t3);

    return 6366000 * tt;
  }

  public static double gps2m(double lat_a, double lng_a, double lat_b, double lng_b)
  {
    double pk = 180 / 3.14169;

    float a1 = (float) (lat_a / pk);
    float a2 = (float) (lng_a / pk);
    float b1 = (float) (lat_b / pk);
    float b2 = (float) (lng_b / pk);

    double t1 = FloatMath.cos(a1) * FloatMath.cos(a2) * FloatMath.cos(b1) * FloatMath.cos(b2);
    double t2 = FloatMath.cos(a1) * FloatMath.sin(a2) * FloatMath.cos(b1) * FloatMath.sin(b2);
    double t3 = FloatMath.sin(a1) * FloatMath.sin(b1);
    double tt = Math.acos(t1 + t2 + t3);

    return roundTo4Decimals(6366000 * tt);
  }

  public static double roundTo4Decimals(double d)
  {
    return (double) Math.round(d * 10000) / 10000;
  }

  public static LatLng centroid(PolygonOptions po)
  {
    double[] centroid = { 0.0, 0.0 };
    List<LatLng> points = po.getPoints();
    for (int i = 0; i < points.size(); i++)
    {
      centroid[0] += points.get(i).latitude;
      centroid[1] += points.get(i).longitude;
    }

    int totalPoints = points.size();
    centroid[0] = centroid[0] / totalPoints;
    centroid[1] = centroid[1] / totalPoints;

    return new LatLng(centroid[0], centroid[1]);
  }
}
