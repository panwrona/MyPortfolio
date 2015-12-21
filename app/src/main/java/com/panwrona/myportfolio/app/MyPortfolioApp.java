package com.panwrona.myportfolio.app;

import com.facebook.stetho.Stetho;
import com.panwrona.myportfolio.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MyPortfolioApp extends com.activeandroid.app.Application {

    @Override public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(
            new CalligraphyConfig.Builder().setDefaultFontPath("fonts/Roboto-Light.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        Stetho.initializeWithDefaults(this);
        //SQLiteUtils.rawQuery(Tool.class, "INSERT INTO Tools", new String[] {
        //    //"1,'Databases',4,'I am familiar with raw SQL queries and some android libraries. Most of all Ive been using ORMLite, but in this project I am testing ActiveAndroid library.'",
        //    "2,'EventBuses',5,'Ive been using event buses in  most of my projects, the commercial and private ones. I am familiar with Otto, GreenRobots EventBus and RxJava EventBus(implementing myself).'"
        //    });
        //SQLiteUtils.execSql(
        //    "INSERT INTO Tools VALUES(1,'Databases',4,'I am familiar with raw SQL queries and some android libraries. Most of all Ive been using ORMLite, but in this project I am testing ActiveAndroid library.')"
        //        + "INSERT INTO Tools VALUES(2,'EventBuses',5,'Ive been using event buses in  most of my projects, the commercial and private ones. I am familiar with Otto, GreenRobots EventBus and RxJava EventBus(implementing myself). ')"
        //        + "INSERT INTO Tools VALUES(3,'REST',4,'To the most of my projects Ive been using Squares Retrofit. Its enough for typical client-server connection, and combined with RxJava gets really sweet.')"
        //        + "INSERT INTO Tools VALUES(4,'Dependency Injection',3,'For dependencyałinjection Ive been using Dagger 1 and Dagger 2. ')"
        //        + "INSERT INTO Tools VALUES(5,'RxJava',3,'RxJava is the new thing in android programming and Ive been diving into it in few projects. Its really powerful tool')"
        //        + "INSERT INTO Tools VALUES(6,'Google Play Services',4,'I am familiar with those Google Play Services libraries: Google Maps, Google Cloud Messaging, Google+ and Google Places.')"
        //        + "INSERT INTO Tools VALUES(7,'Unit/UI Testing',2,'I started learning UI and Unit Testing on Android recently. Ive written some code using Roboelectric, Espresso and Mockito for UITests and JUnit for Unit Testing');");

    }
}
