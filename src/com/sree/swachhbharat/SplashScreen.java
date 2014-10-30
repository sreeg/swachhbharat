package com.sree.swachhbharat;

import com.sree.swachhbharat.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class SplashScreen extends Activity
{
  private static int SPLASH_TIME_OUT = 3000;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    // this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.splash);
    ImageView imgView = (ImageView) findViewById(R.id.imgLogo);
    imgView.setScaleType(ScaleType.FIT_CENTER);
    // getActionBar().hide();
    new Handler().postDelayed(new Runnable()
    {
      @Override
      public void run()
      {
        //Intent i = new Intent(SplashScreen.this, GetCurrentLocation.class);
        Intent i = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(i);

        finish();
      }
    }, SPLASH_TIME_OUT);
  }

}