package com.sree.swachhbharat;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

public class MapActivity extends Activity implements OnMapClickListener, OnMapLongClickListener, OnMarkerClickListener
{

  final int RQS_GooglePlayServices = 1;
  private GoogleMap myMap;

  LatLng myLocation;
  TextView tvLocInfo;

  boolean markerClicked;
  PolygonOptions polygonOptions;
  Polygon polygon;
  private Location location;
  private GPSTracker gpsTracker;
  private Button saveButton;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.root_map);
    saveButton = (Button) findViewById(R.id.save);
    saveButton.setEnabled(false);

    tvLocInfo = (TextView) findViewById(R.id.locinfo);

    FragmentManager myFragmentManager = getFragmentManager();
    MapFragment myMapFragment = (MapFragment) myFragmentManager.findFragmentById(R.id.map);
    myMap = myMapFragment.getMap();

    myMap.setMyLocationEnabled(true);

    myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

    myMap.setOnMapClickListener(this);
    myMap.setOnMapLongClickListener(this);
    myMap.setOnMarkerClickListener(this);

    markerClicked = false;

    location = myMap.getMyLocation();
    gpsTracker = new GPSTracker(this);
    location = gpsTracker.getLocation();
    if (location != null)
    {
      myLocation = new LatLng(location.getLatitude(), location.getLongitude());
      myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 17.0f));
    }

    drawPolygons();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    switch (item.getItemId())
    {
      case R.id.action_settings:
        String LicenseInfo = GooglePlayServicesUtil.getOpenSourceSoftwareLicenseInfo(getApplicationContext());
        AlertDialog.Builder LicenseDialog = new AlertDialog.Builder(MapActivity.this);
        LicenseDialog.setTitle("Legal Notices");
        LicenseDialog.setMessage(LicenseInfo);
        LicenseDialog.show();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onResume()
  {

    super.onResume();

    int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());

    if (resultCode == ConnectionResult.SUCCESS)
    {
      // Toast.makeText(getApplicationContext(),
      // "isGooglePlayServicesAvailable SUCCESS", Toast.LENGTH_LONG).show();
    }
    else
    {
      GooglePlayServicesUtil.getErrorDialog(resultCode, this, RQS_GooglePlayServices);
    }

  }

  @Override
  public void onMapClick(LatLng point)
  {
    tvLocInfo.setText(point.toString());
    myMap.animateCamera(CameraUpdateFactory.newLatLng(point));

    markerClicked = false;
  }

  @Override
  public void onMapLongClick(LatLng point)
  {
    tvLocInfo.setText("New marker added@" + point.toString());
    MarkerOptions marker = new MarkerOptions().position(point).title(point.toString());
    Marker myMarker = myMap.addMarker(marker);
    myMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.broom));
    markerClicked = false;
  }

  @Override
  public boolean onMarkerClick(Marker marker)
  {
    if (markerClicked)
    {
      if (polygon != null)
      {
        polygon.remove();
        polygon = null;
      }
      polygonOptions.add(marker.getPosition());
      polygonOptions.strokeColor(CommonUtils.OPEN_COLOR_DARK);
      polygonOptions.strokeWidth(5f);
      polygonOptions.fillColor(CommonUtils.OPEN_COLOR_A);
      polygon = myMap.addPolygon(polygonOptions);
      if (polygonOptions.getPoints().size() > 2)
      {
        saveButton.setEnabled(true);
      }
    }
    else
    {
      if (polygon != null)
      {
        polygon.remove();
        polygon = null;
      }

      polygonOptions = new PolygonOptions().add(marker.getPosition());
      markerClicked = true;
    }

    return true;
  }

  public void onSaveArea(View v)
  {
    if (polygonOptions != null)
    {
      MyArea myArea = new MyArea();
      myArea.setAreaName("Some Area Name");
      // myArea.setPolygonOptions(polygonOptions);
      myArea.setPosition(polygonOptions.getPoints());
      Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
      List<Address> addresses;
      try
      {
        LatLng latLng = polygonOptions.getPoints().get(0);
        addresses = gcd.getFromLocation(latLng.latitude, latLng.longitude, 1);
        Address address = addresses.get(0);
        myArea.setCityName(address.getLocality());
        myArea.setSubLocality(address.getSubLocality());
        myArea.setSubAdminArea(address.getSubAdminArea());
        myArea.setThoroughfare(address.getThoroughfare());
        myArea.setSubThoroughfare(address.getSubThoroughfare());
      }
      catch (Exception e)
      {

      }
      if (MainActivity.myLocations == null)
      {
        MainActivity.myLocations = new ArrayList<MyArea>();
      }
      MainActivity.myLocations.add(myArea);

      myMap.clear();
      polygonOptions = null;
      drawPolygons();
      saveButton.setEnabled(false);
    }
  }

  private void drawPolygons()
  {
    if (MainActivity.myLocations != null)
    {
      for (int i = 0; i < MainActivity.myLocations.size(); ++i)
      {
        PolygonOptions po = new PolygonOptions();
        // @Todo
        myMap.addPolygon(po);
      }
    }
  }
}
