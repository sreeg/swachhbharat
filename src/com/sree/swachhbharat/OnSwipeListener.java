package com.sree.swachhbharat;

import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class OnSwipeListener implements OnTouchListener
{

  private final GestureDetector gestureDetector;

  public OnSwipeListener(Context ctx)
  {
    gestureDetector = new GestureDetector(ctx, new GestureListener());
  }

  private final class GestureListener extends SimpleOnGestureListener
  {

    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;
    private boolean tapIndicator;

    @Override
    public boolean onDown(MotionEvent e)
    {
      return true;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e)
    {
      this.tapIndicator = true;
      return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent arg0)
    {
      onDoubleTouch();
      return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent arg0)
    {
      return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent arg0)
    {
      onSingleTouch();
      return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
    {
      boolean result = false;
      try
      {
        float diffY = e2.getY() - e1.getY();
        float diffX = e2.getX() - e1.getX();
        if (Math.abs(diffX) > Math.abs(diffY))
        {
          if ((Math.abs(diffX) > SWIPE_THRESHOLD) && (Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD))
          {
            if (diffX > 0)
            {
              onSwipeRight();
            }
            else
            {
              onSwipeLeft();
            }
          }
          result = true;
        }
        else if ((Math.abs(diffY) > SWIPE_THRESHOLD) && (Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD))
        {
          if (diffY > 0)
          {
            onSwipeBottom();
          }
          else
          {
            onSwipeTop();
          }
        }
        result = true;

      }
      catch (Exception exception)
      {
        exception.printStackTrace();
      }
      return result;
    }
  }

  public void onSwipeRight()
  {
  }

  public void onSwipeLeft()
  {
  }

  public void onSwipeTop()
  {
  }

  public void onSwipeBottom()
  {
  }

  public void onSingleTouch()
  {
  }

  public void onDoubleTouch()
  {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean onTouch(View v, MotionEvent event)
  {
    return false;
  }

  public GestureDetector getGestureDetector()
  {
    return gestureDetector;
  }
}