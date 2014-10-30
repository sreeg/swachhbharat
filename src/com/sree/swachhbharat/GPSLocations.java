package com.sree.swachhbharat;

import com.sree.swachhbharat.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class GPSLocations extends Activity
{

  TextView textview;
  GPSTracker gpsTracker;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.geolocationexample);

    // check if GPS enabled
    gpsTracker = new GPSTracker(this);

    if (gpsTracker.canGetLocation())
    {
      String stringLatitude = String.valueOf(gpsTracker.latitude);
      textview = (TextView) findViewById(R.id.fieldLatitude);
      textview.setText(stringLatitude);

      String stringLongitude = String.valueOf(gpsTracker.longitude);
      textview = (TextView) findViewById(R.id.fieldLongitude);
      textview.setText(stringLongitude);

      String country = gpsTracker.getCountryName(this);
      textview = (TextView) findViewById(R.id.fieldCountry);
      textview.setText(country);

      String city = gpsTracker.getLocality(this);
      textview = (TextView) findViewById(R.id.fieldCity);
      textview.setText(city);

      String postalCode = gpsTracker.getPostalCode(this);
      textview = (TextView) findViewById(R.id.fieldPostalCode);
      textview.setText(postalCode);

      String addressLine = gpsTracker.getAddressLine(this);
      textview = (TextView) findViewById(R.id.fieldAddressLine);
      textview.setText(addressLine);
    }
    else
    {
      // can't get location
      // GPS or Network is not enabled
      // Ask user to enable GPS/network in settings
      gpsTracker.showSettingsAlert();
    }
  }
}