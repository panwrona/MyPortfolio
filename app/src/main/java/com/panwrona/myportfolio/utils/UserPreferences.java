package com.panwrona.myportfolio.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.inkapplications.preferences.BooleanPreference;
import com.inkapplications.preferences.IntPreference;

public class UserPreferences {
    private static final String PREFS = "UserPreferences";

    interface Preference {
        String PREF_LAST_APP_VERSION = "_preference_last_app_version";
        String IS_DB_PREPARED = "_preference_is_db_prepared";
    }

    private IntPreference mLastAppVersion;
    private BooleanPreference mIsDbPrepared;

    public UserPreferences(Context context) {
        SharedPreferences mSharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        mLastAppVersion = new IntPreference(mSharedPreferences, PREFS + Preference.PREF_LAST_APP_VERSION);
        mIsDbPrepared = new BooleanPreference(mSharedPreferences, PREFS + Preference.IS_DB_PREPARED, false);
    }

    public int getmLastAppVersion() {
        return mLastAppVersion.get();
    }

    public void setmLastAppVersion(int version) {
        mLastAppVersion.set(version);
    }

    public boolean getmIsDbPrepared() {
        return mIsDbPrepared.get();
    }

    public void setmIsDbPrepared(boolean isDbPrepared) {
        mIsDbPrepared.set(isDbPrepared);
    }
}
