package com.panwrona.myportfolio.screen_about_me;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import butterknife.Bind;
import butterknife.OnClick;

import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.customviews.support_layouts.MyNestedScrollView;
import com.panwrona.myportfolio.customviews.support_layouts.WrapContentHeightViewPager;
import com.panwrona.myportfolio.mvp.MvpActivity;
import com.panwrona.myportfolio.screen_contact.ContactActivity;
import com.panwrona.myportfolio.utils.GUIUtils;

public class AboutMeActivity extends MvpActivity<AboutMeView, AboutMePresenter>
        implements AboutMeView, ViewPager.OnPageChangeListener {
    private static final String TAG = AboutMeActivity.class.getSimpleName();

    @Bind(R.id.activity_about_me_tl_container)
    TabLayout mTlContainer;
    @Bind(R.id.activity_about_me_vp_container)
    WrapContentHeightViewPager mVpContainer;
    @Bind(R.id.activity_about_me_nsv_container)
    MyNestedScrollView mNsvContainer;
    @Bind(R.id.activity_about_me_cv_container)
    CardView mCvContainer;
    @Bind(R.id.activity_about_me_appbar)
    AppBarLayout mAppBar;
    @Bind(R.id.activity_about_me_fab)
    FloatingActionButton mFab;
    @Bind(R.id.activity_abouy_me_toolbar)
    Toolbar mToolbar;

    @Override
    protected AboutMePresenter createPresenter() {
        return new AboutMePresenterImp();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_about_me;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initEnterTransition();
        initToolbar();
        AboutMePagerAdapter pagerAdapter =
                new AboutMePagerAdapter(getSupportFragmentManager(), AboutMeActivity.this);
        mVpContainer.setAdapter(pagerAdapter);
        mVpContainer.addOnPageChangeListener(this);
        mTlContainer.setupWithViewPager(mVpContainer);
    }

    private void initEnterTransition() {
        Fade fade = new Fade(Fade.IN);
        fade.setDuration(150);
        fade.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                setupEnterTransition();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
        getWindow().setEnterTransition(fade);

        Fade fadeOut = new Fade(Fade.OUT);
        fade.setDuration(150);
        getWindow().setReturnTransition(fadeOut);
    }

    private void setupEnterTransition() {
        GUIUtils.startEnterTransitionSlideUp(this, mCvContainer);
        GUIUtils.startEnterTransitionSlideDown(this, mAppBar, mToolbar);
    }

    @Override
    public void onBackPressed() {
        setupReturnTransition();
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        super.finish();
    }

    private void setupReturnTransition() {
        GUIUtils.startReturnTransitionSlideUp(this, null, mToolbar, mAppBar);
        GUIUtils.startReturnTransitionSlideDown(this, AboutMeActivity.this::onBackPress, mCvContainer);
    }

    private void onBackPress() {
        super.onBackPressed();
    }

    @SuppressWarnings("ConstantConditions")
    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @OnClick(R.id.activity_about_me_fab)
    public void onFabClicked() {
        ActivityOptions options =
                ActivityOptions.makeSceneTransitionAnimation(this, mFab, mFab.getTransitionName());
        startActivity(new Intent(this, ContactActivity.class), options.toBundle());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_IDLE:
                if (!mFab.isShown())
                    mFab.show();
                break;
            case ViewPager.SCROLL_STATE_DRAGGING:
                if (mFab.isShown())
                    mFab.hide();
                break;
            case ViewPager.SCROLL_STATE_SETTLING:
                if (!mFab.isShown())
                    mFab.show();
                break;
        }
    }
}
