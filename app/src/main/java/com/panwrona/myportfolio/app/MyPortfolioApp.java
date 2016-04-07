package com.panwrona.myportfolio.app;

import com.facebook.stetho.Stetho;
import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.utils.UserPreferences;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.internal.TwitterApi;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MyPortfolioApp extends com.activeandroid.app.Application {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "8yfC8xWRG0VJJVpqI8taKRQvu";
    private static final String TWITTER_SECRET = "H8dZTOKjhsdbFLyMsqgRGq0EUV2prZpQawvCR50O6Vq9MTn1Tk";


    private static MyPortfolioApp sInstance = null;
    private UserPreferences mUserPreferences;

    @Override public void onCreate() {
        super.onCreate();
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        CalligraphyConfig.initDefault(
                new CalligraphyConfig.Builder().setDefaultFontPath("fonts/Roboto-Light.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build());
        Stetho.initializeWithDefaults(this);
        sInstance = this;
        mUserPreferences = new UserPreferences(this);
    }

    public static MyPortfolioApp getInstance() {
        return sInstance;
    }

    public UserPreferences getUserPreferences() {
        return mUserPreferences;
    }
}
