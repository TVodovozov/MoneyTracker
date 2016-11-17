package com.loftschool.moneytracker.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Binder;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.loftschool.moneytracker.R;
import com.loftschool.moneytracker.ui.fragments.SettingsFragment_;


public class ServiceSample extends Service implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final long[] VIBRATE_PATTERN = new long[]{1000, 1000, 1000};
    private static final int LED_LIGHTS_TIME_ON = 200;
    private static final int LED_LIGHTS_TIME_OFF = 1500;
    private static final int NOTIFICATION_ID = 4004;
    private static final boolean DEFAULT_VALUE = true;

    private SharedPreferences mSharedPreferences;
    private NotificationManager mNotificationManager;
    private SampleBinder mSampleBinder;

    private String globalNotificationsKey;
    private String vibrateNotificationsKey;
    private String soundNotificationsKey;
    private String ledNotificationsKey;
    private String foregroundNotificationsKey;

    private boolean isNotificationsEnabled;
    private boolean isVibrateEnabled;
    private boolean isSoundEnabled;
    private boolean isLedEnabled;
    private boolean isForegroundEnabled;

    @Override
    public void onCreate() {
        super.onCreate();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mSharedPreferences.registerOnSharedPreferenceChangeListener(this);
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        init();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        boolean value = sharedPreferences.getBoolean(key, DEFAULT_VALUE);
        if (TextUtils.equals(key, globalNotificationsKey)) {
            isNotificationsEnabled = value;
        } else if (TextUtils.equals(key, vibrateNotificationsKey)) {
            isVibrateEnabled = value;
        } else if (TextUtils.equals(key, soundNotificationsKey)) {
            isSoundEnabled = value;
        } else if (TextUtils.equals(key, ledNotificationsKey)) {
            isLedEnabled = value;
        } else if (TextUtils.equals(key, foregroundNotificationsKey)) {
            isForegroundEnabled = value;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if (mSampleBinder == null) {
            mSampleBinder = new SampleBinder();
        }
        return mSampleBinder;
    }

    @Override
    public void onDestroy() {
        mSharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
        mSharedPreferences = null;
        mNotificationManager = null;
        mSampleBinder = null;
        super.onDestroy();
    }

    public void sendNotification() {
        if (!isNotificationsEnabled) {
            return;
        }
        Intent runActivityIntent = new Intent(ServiceSample.this, SettingsFragment_.class);
        runActivityIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                runActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.notification_message))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        if (isLedEnabled) {
            builder.setLights(Color.CYAN, LED_LIGHTS_TIME_ON, LED_LIGHTS_TIME_OFF);
        }

        if (isSoundEnabled) {
            builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        }

        if (isVibrateEnabled) {
            builder.setVibrate(VIBRATE_PATTERN);
        }

        if (isForegroundEnabled) {
            startForeground(NOTIFICATION_ID, builder.build());
        } else {
            stopForeground(true);
            mNotificationManager.notify(NOTIFICATION_ID, builder.build());
        }
    }

    public class SampleBinder extends Binder {

        public ServiceSample getService() {
            return ServiceSample.this;
        }

    }

    private void init() {
        if (mSharedPreferences == null) {
            throw new IllegalStateException("Shared preferences can't be null.");
        }
        globalNotificationsKey = getString(R.string.pref_enable_notifications_key);
        vibrateNotificationsKey = getString(R.string.pref_enable_vibrate_notifications_key);
        soundNotificationsKey = getString(R.string.pref_enable_sound_notifications_key);
        ledNotificationsKey = getString(R.string.pref_enable_led_notifications_key);
        foregroundNotificationsKey = getString(R.string.pref_enable_foreground_notifications_key);

        isNotificationsEnabled = mSharedPreferences.getBoolean(globalNotificationsKey, DEFAULT_VALUE);
        isVibrateEnabled = mSharedPreferences.getBoolean(vibrateNotificationsKey, DEFAULT_VALUE);
        isSoundEnabled = mSharedPreferences.getBoolean(soundNotificationsKey, DEFAULT_VALUE);
        isLedEnabled = mSharedPreferences.getBoolean(ledNotificationsKey, DEFAULT_VALUE);
        isForegroundEnabled = mSharedPreferences.getBoolean(foregroundNotificationsKey, DEFAULT_VALUE);
    }
}
