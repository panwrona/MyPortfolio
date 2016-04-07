package com.panwrona.myportfolio.screen_main;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import butterknife.Bind;
import butterknife.OnClick;

import com.panwrona.myportfolio.BuildConfig;
import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.app.MyPortfolioApp;
import com.panwrona.myportfolio.data.database.entities.Tool;
import com.panwrona.myportfolio.mvp.MvpActivity;
import com.panwrona.myportfolio.screen_about_me.AboutMeActivity;
import com.panwrona.myportfolio.screen_coding.CodingActivity;
import com.panwrona.myportfolio.screen_contact.ContactActivity;
import com.panwrona.myportfolio.screen_news.TwitterNewsActivity;
import com.panwrona.myportfolio.screen_passion.PassionActivity;
import com.panwrona.myportfolio.utils.GUIUtils;
import com.panwrona.myportfolio.utils.IntentUtils;
import com.panwrona.myportfolio.utils.RequestCodes;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends MvpActivity<MainActivityView, MainActivityPresenter>
        implements MainActivityView, ViewTreeObserver.OnGlobalLayoutListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Bind(R.id.activity_main_rl_placeholder)
    RelativeLayout mRlPlaceholder;
    @Bind(R.id.activity_main_fab)
    FloatingActionButton mFab;
    @Bind(R.id.activity_main_iv_logo)
    CircleImageView mIvLogo;
    @Bind(R.id.activity_main_cl_container)
    CoordinatorLayout mClContainer;
    @Bind(R.id.activity_main_ll_container)
    LinearLayout mLlContainer;

    private String[] toolNames = {"Databases", "EventBuses", "REST", "Dependency Injection", "RxJava", "Google Play Services", "Unit/UI Testing"};
    private String[] toolDescriptions = {"I am familiar with raw SQL queries and some android libraries. Most of all Ive been using ORMLite, but in this project I am testing ActiveAndroid library.'",
            "I've been using event buses in  most of my projects, the commercial and private ones. I am familiar with Otto, GreenRobots EventBus and RxJava EventBus(implementing myself).",
            "To the most of my projects Ive been using Squares Retrofit. Its enough for typical client-server connection, and combined with RxJava gets really sweet.",
            "For dependency injection Ive been using Dagger 1 and Dagger 2.",
            "RxJava is the new thing in android programming and Ive been diving into it in few projects. Its really powerful tool.",
            "I am familiar with those Google Play Services libraries: Google Maps, Google Cloud Messaging, Google+ and Google Places.",
            "I started learning UI and Unit Testing on Android recently. Ive written some code using Roboelectric, Espresso and Mockito for UITests and JUnit for Unit Testing."
    };

    private int[] toolSkills = {4,5,4,3,3,4,2};
    @Override
    protected MainActivityPresenter createPresenter() {
        return new MainActivityPresenterImpl();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClContainer.getViewTreeObserver().addOnGlobalLayoutListener(this);
        if (MyPortfolioApp.getInstance().getUserPreferences() != null) {
            if (!MyPortfolioApp.getInstance().getUserPreferences().getmIsDbPrepared()) {
                initDb();
                MyPortfolioApp.getInstance().getUserPreferences().setmIsDbPrepared(true);
            }
        }
    }

    private void initDb() {
        for(int i = 0; i < toolDescriptions.length; i++) {
            Tool tool = new Tool();
            tool.name = toolNames[i];
            tool.description = toolDescriptions[i];
            tool.skillLevel = toolSkills[i];
            tool.save();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RequestCodes.NEW_LOLLIPOP_ACTIVITY) {
                startEnterTransitions();
            }
        }
    }

    private void startEnterTransitions() {
        GUIUtils.startEnterTransitionSlideDown(this, mRlPlaceholder);
        GUIUtils.startEnterTransitionSlideUp(this, mLlContainer);
        GUIUtils.startScaleUpAnimation(this, mIvLogo);
        mFab.show();
//        mClContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    @OnClick(R.id.activity_main_tv_about_me)
    public void onAboutMeClicked() {
        chooseActivityAndStart(AboutMeActivity.class);
    }

    @OnClick(R.id.activity_main_tv_coding)
    public void onCodingClicked() {
        chooseActivityAndStart(CodingActivity.class);
    }

    @OnClick(R.id.activity_main_tv_passion)
    public void onPassionClicked() {
        chooseActivityAndStart(PassionActivity.class);
    }

    @OnClick(R.id.activity_main_tv_news)
    public void onNewsClicked() {
        chooseActivityAndStart(TwitterNewsActivity.class);
    }

    @OnClick(R.id.activity_main_fab)
    public void onFabClicked() {
        startContactActivity();
    }

    private void chooseActivityAndStart(Class<?> desiredClass) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            GUIUtils.startReturnTransitionSlideUp(this, null, mRlPlaceholder);
            GUIUtils.startReturnTransitionSlideDown(this, () -> IntentUtils.startActivityForResult(this, desiredClass)
                    , mLlContainer);
            GUIUtils.startScaleDownAnimation(this, mIvLogo);
        } else {
            startActivityForResult(new Intent(this, desiredClass), RequestCodes.NEW_ACTIVITY);
        }
        mFab.hide();
    }

    private void startContactActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options =
                    ActivityOptions.makeSceneTransitionAnimation(this, mFab, mFab.getTransitionName());
            startActivity(new Intent(this, ContactActivity.class), options.toBundle());
        } else {
            startActivity(new Intent(this, ContactActivity.class));
        }
    }

    @Override
    public void onGlobalLayout() {
//        new Handler(Looper.getMainLooper()).post(this::fadeInEnterTransitions);
    }

    private void fadeInEnterTransitions() {
        Animation animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        animation.setDuration(500);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mClContainer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mClContainer.startAnimation(animation);
    }
}
