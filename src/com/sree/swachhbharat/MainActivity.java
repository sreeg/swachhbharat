package com.sree.swachhbharat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AppEventsLogger;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

public class MainActivity extends Activity implements ConnectionCallbacks, OnConnectionFailedListener
{

  private static final int RC_SIGN_IN = 0;
  // Google client to interact with Google API
  private GoogleApiClient mGoogleApiClient;
  public static List<MyArea> myLocations;
  @SuppressLint("SdCardPath")
  public static String FILE_PATH = "/sdcard/SB_MY_Areas.bin";
  /**
   * A flag indicating that a PendingIntent is in progress and prevents us from
   * starting further intents.
   */
  private boolean mIntentInProgress;
  private boolean mSignInClicked;
  private ConnectionResult mConnectionResult;
  private UiLifecycleHelper uiHelper;
  private TextView loginText;
  private static boolean isConnectedToFB = false;
  private static boolean isConnectedToGPlus = false;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.home);

    mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(Plus.API).addScope(Plus.SCOPE_PLUS_LOGIN).build();
    uiHelper = new UiLifecycleHelper(this, statusCallback);
    uiHelper.onCreate(savedInstanceState);
    isConnectedToGPlus = mGoogleApiClient.isConnected();
    loginText = (TextView) findViewById(R.id.loginMessage);
    mGoogleApiClient.connect();
    if (isConnectedToGPlus)
    {
      if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null)
      {
        Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
        String personName = currentPerson.getDisplayName();

        loginText.setText("Welcome " + personName);
      }
    }
    readFromFile();
    createAndAppendAreaListLayout();
  }

  private Session.StatusCallback statusCallback = new Session.StatusCallback()
  {
    @Override
    public void call(Session session, SessionState state, Exception exception)
    {
      if (state.isOpened())
      {
        Log.d("FacebookSampleActivity", "Facebook session opened");
        isConnectedToFB = true;
      }
      else if (state.isClosed())
      {
        Log.d("FacebookSampleActivity", "Facebook session closed");
        isConnectedToFB = false;
      }
    }
  };

  protected void onStart()
  {
    super.onStart();
    mGoogleApiClient.connect();
  }

  protected void onStop()
  {
    super.onStop();
    if (mGoogleApiClient.isConnected())
    {
      mGoogleApiClient.disconnect();
    }
    saveObject(myLocations);
  }

  /**
   * Method to resolve any sign in errors
   * */
  private void resolveSignInError()
  {
    if (mConnectionResult.hasResolution())
    {
      try
      {
        mIntentInProgress = true;
        mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
      }
      catch (SendIntentException e)
      {
        mIntentInProgress = false;
        mGoogleApiClient.connect();
      }
    }
  }

  @SuppressLint("SdCardPath")
  @SuppressWarnings("unchecked")
  public static void readFromFile()
  {
    try
    {
      InputStream is = new FileInputStream(FILE_PATH);

      ObjectInputStream ois = new ObjectInputStream(is);
      ArrayList<Object> objs = (ArrayList<Object>) ois.readObject();
      myLocations = (List<MyArea>) objs.get(0);

      ois.close();
    }
    catch (Exception ex)
    {
      Log.v("Serialization Error >> ", ex.getMessage());
      ex.printStackTrace();
    }
  }

  @Override
  public void onConnectionFailed(ConnectionResult result)
  {
    if (!result.hasResolution())
    {
      GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this, 0).show();
      return;
    }

    if (!mIntentInProgress)
    {
      mConnectionResult = result;

      if (mSignInClicked)
      {
        resolveSignInError();
      }
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int responseCode, Intent intent)
  {
    if (requestCode == RC_SIGN_IN)
    {
      if (responseCode != RESULT_OK)
      {
        mSignInClicked = false;
      }

      mIntentInProgress = false;

      if (!mGoogleApiClient.isConnecting())
      {
        mGoogleApiClient.connect();
      }
    }
    uiHelper.onActivityResult(requestCode, responseCode, intent);
  }

  @Override
  protected void onPause()
  {
    super.onPause();
    AppEventsLogger.deactivateApp(this);
    uiHelper.onPause();
  }

  @Override
  public void onConnected(Bundle arg0)
  {
    mSignInClicked = false;
  }

  @Override
  public void onDestroy()
  {
    super.onDestroy();
    uiHelper.onDestroy();
  }

  @Override
  protected void onResume()
  {
    super.onResume();
    AppEventsLogger.activateApp(this);
    uiHelper.onResume();
    createAndAppendAreaListLayout();
    saveObject(myLocations);
  }

  public void addLocation(View v)
  {
    Intent i = new Intent(this, MapActivity.class);
    startActivityForResult(i, 1);
  }

  @Override
  public void onConnectionSuspended(int arg0)
  {
    mGoogleApiClient.connect();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    getMenuInflater().inflate(R.menu.menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    int id = item.getItemId();
    if (id == R.id.action_settings)
    {
      return true;
    }
    else if (id == R.id.connectwithFB)
    {
      Intent i = new Intent(this, FBActivity.class);
      startActivityForResult(i, 1);
    }
    else if (id == R.id.connectwithGPlus)
    {
      Intent i = new Intent(this, GPlusActivity.class);
      startActivityForResult(i, 1);
    }
    return super.onOptionsItemSelected(item);
  }

  public static void saveObject(List<MyArea> v)
  {

    try
    {
      ArrayList<Object> objs = new ArrayList<Object>();
      objs.add(v);
      objs.add(myLocations);

      ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(FILE_PATH)));
      oos.writeObject(objs); // write the class as an 'object'
      oos.flush(); // flush the stream to insure all of the information was
                   // written to 'save_object.bin'
      oos.close();// close the stream
    }
    catch (Exception ex)
    {
      Log.v("Serialization Save Error : ", ex.getMessage());
      ex.printStackTrace();
    }
  }

  @Override
  public void onBackPressed()
  {
    backButtonHandler();
  }

  private void backButtonHandler()
  {
    AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
    alertDialog.setTitle(R.string.leave_application_);
    alertDialog.setMessage(R.string.are_you_sure_you_want_to_leave_gis_);
    alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
    {
      @Override
      public void onClick(DialogInterface dialog, int which)
      {
        finish();
      }
    });
    alertDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener()
    {
      @Override
      public void onClick(DialogInterface dialog, int which)
      {
        dialog.cancel();
      }
    });
    alertDialog.show();
  }

  private void createAndAppendAreaListLayout()
  {
    LinearLayout mainLayout = (LinearLayout) findViewById(R.id.areaListContainerInner);
    if (mainLayout.getChildCount() > 0)
    {
      ((ViewGroup) mainLayout).removeAllViews();
    }
    List<MyArea> mainList = myLocations; // populate it...
    if (mainList != null && mainList.size() != 0)
    {
      loginText.setText("Welcome, thanks for participating in Swachh Bharat Abhiyan.");
      LayoutInflater li = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      int size = mainList.size();
      for (int i = 0; i < size; i++)
      {
        final MyArea myArea = mainList.get(i);

        View tempView = li.inflate(R.layout.arealist, null);

        final String areaName = myArea.getThoroughfare() + ", " + myArea.getSubLocality();
        final LinearLayout repeatingLayout = (LinearLayout) tempView.findViewById(R.id.areaContainer);
        final LinearLayout layoutBottom = (LinearLayout) tempView.findViewById(R.id.areaContainer2);

        repeatingLayout.setOnTouchListener(new OnSwipeListener(this)
        {
          @Override
          public void onSwipeTop()
          {
            Toast.makeText(MainActivity.this, "top", Toast.LENGTH_SHORT).show();
          }

          @Override
          public void onSwipeRight()
          {
            // ;
          }

          @Override
          public void onSwipeLeft()
          {
            //
          }

          @Override
          public void onSwipeBottom()
          {
            Toast.makeText(MainActivity.this, "bottom", Toast.LENGTH_SHORT).show();
          }

          @Override
          public void onSingleTouch()
          {
            Intent i = new Intent(MainActivity.this, MapActivity.class);
            startActivityForResult(i, 1);
          }

          @Override
          public void onDoubleTouch()
          {
            Intent i = new Intent(MainActivity.this, MapActivity.class);
            startActivityForResult(i, 1);
          }

          @Override
          public boolean onTouch(View v, MotionEvent event)
          {
            // onClickLayout(v, valveData);
            return getGestureDetector().onTouchEvent(event);
          }
        });

        repeatingLayout.setBackgroundColor(CommonUtils.COLOR_WHITE);
        // im.setBackgroundColor(CommonUtils.COLOR_WHITE);
        // im.setTextColor(CommonUtils.COLOR_BLACK);
        // im.setPadding(6, 3, 6, 3);
        layoutBottom.setBackgroundColor(CommonUtils.COLOR_DARK_WHITE);

        repeatingLayout.setOnClickListener(new View.OnClickListener()
        {

          @Override
          public void onClick(View v)
          {
            Intent i = new Intent(MainActivity.this, MapActivity.class);
            startActivityForResult(i, 1);
          }
        });

        TextView areaLabel = (TextView) tempView.findViewById(R.id.areaNameLabel);
        areaLabel.setTextColor(CommonUtils.COLOR_BLACK);

        TextView areaName2 = (TextView) tempView.findViewById(R.id.areaName);
        areaName2.setText(areaName);
        areaName2.setTextColor(CommonUtils.COLOR_LIGHT_BLACK);
        // areaName2.setTypeface(null, Typeface.BOLD);

        TextView areaLandmark = (TextView) tempView.findViewById(R.id.areaLandmark);
        areaLandmark.setText(CommonUtils.capitalizeString(myArea.getCityName()) + ", ");
        areaLandmark.setTextColor(CommonUtils.COLOR_LIGHT_BLACK);

        TextView areaLocation = (TextView) tempView.findViewById(R.id.areaLocation);
        areaLocation.setText(CommonUtils.capitalizeString(myArea.getSubAdminArea()));
        areaLocation.setTextColor(CommonUtils.COLOR_LIGHT_BLACK);

        // TextView valvedistance = (TextView)
        // tempView.findViewById(R.id.areasArea);
        // valvedistance.setText("-");
        // valvedistance.setTextColor(CommonUtils.COLOR_LIGHT_BLACK);

        ImageView mapIcon = (ImageView) tempView.findViewById(R.id.imageMap);
        mapIcon.setOnClickListener(new OnClickListener()
        {

          @Override
          public void onClick(View v)
          {
            Intent i = new Intent(MainActivity.this, MapActivity.class);
            startActivityForResult(i, 1);
          }
        });

        mainLayout.addView(tempView);
      }
    }
  }
}
