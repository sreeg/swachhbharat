package com.sree.swachhbharat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class UserSettingActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener
{
  public static boolean prefsUpdated = false;

  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    addPreferencesFromResource(R.xml.preferences);
    PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);

    ListPreference listPreferenceCategory = (ListPreference) findPreference("prefLineManId");
    if (listPreferenceCategory != null)
    {

    }

    Preference button = (Preference) findPreference("prefClearAll");
    button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
    {
      @Override
      public boolean onPreferenceClick(Preference arg0)
      {
        return true;
      }
    });
  }

  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
  {
    prefsUpdated = true;
  }
}