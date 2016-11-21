package com.loftschool.moneytracker.ui.fragments;



import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loftschool.moneytracker.R;

import org.androidannotations.annotations.EFragment;

@EFragment(R.layout.settings_fragment)
public class SettingsFragment extends PreferenceFragmentCompat
        implements Preference.OnPreferenceChangeListener,
        SharedPreferences.OnSharedPreferenceChangeListener {

    private static final boolean DEFAULT_VALUE = true;
    private static final String LOG_TAG = SettingsFragment.class.getSimpleName();

    private SharedPreferences mSharedPreferences;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.notifications_preferences);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_enable_notifications_key)));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_enable_notifications_key)));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        mSharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mSharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.d(LOG_TAG, "Value with key '" + key + "' was changed to " +
                sharedPreferences.getBoolean(key, DEFAULT_VALUE));
    }

    private void bindPreferenceSummaryToValue(Preference preference) {
        onPreferenceChange(preference, mSharedPreferences.getBoolean(preference.getKey(), true));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {

        String value = newValue.toString();

        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int preferenceIndex = listPreference.findIndexOfValue(value);
            if (preferenceIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[preferenceIndex]);
                return true;
            }
        } else {
            preference.setSummary(value);
            return true;
        }
        return false;
    }
}