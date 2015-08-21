package com.panwrona.myportfolio.app;

import android.app.Application;
import com.panwrona.myportfolio.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MyPortfolioApp extends Application {

    private MyPortfolioAppComponent component;

    @Override public void onCreate() {
        super.onCreate();
        component = MyPortfolioAppComponent.Initializer.init(this);
        CalligraphyConfig.initDefault(
            new CalligraphyConfig.Builder().setDefaultFontPath("fonts/Roboto-Light.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }

    public MyPortfolioAppComponent component() {
        return component;
    }
}
