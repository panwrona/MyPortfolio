package com.panwrona.myportfolio.utils;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import com.panwrona.myportfolio.BuildConfig;
import com.panwrona.myportfolio.screen_about_me.AboutMeActivity;

public class IntentUtils {

    public static void startBrowser(Context ctx, String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        ctx.startActivity(i);
    }

    public static void startActivityForResult(Activity ctx, Class<?> cls) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(ctx);
            ctx.startActivityForResult(new Intent(ctx, cls), RequestCodes.NEW_LOLLIPOP_ACTIVITY, options.toBundle());
        } else {
            ctx.startActivityForResult(new Intent(ctx, cls), RequestCodes.NEW_ACTIVITY);
        }
    }

}
