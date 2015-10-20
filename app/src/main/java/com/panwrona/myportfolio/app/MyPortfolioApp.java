package com.panwrona.myportfolio.app;

import android.app.Application;
import com.panwrona.myportfolio.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MyPortfolioApp extends Application {

    @Override public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(
            new CalligraphyConfig.Builder().setDefaultFontPath("fonts/Roboto-Light.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }
}
