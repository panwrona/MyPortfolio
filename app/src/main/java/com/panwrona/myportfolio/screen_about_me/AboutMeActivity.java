package com.panwrona.myportfolio.screen_about_me;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;

import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;

import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.customviews.support_layouts.MyNestedScrollView;
import com.panwrona.myportfolio.customviews.support_layouts.WrapContentHeightViewPager;
import com.panwrona.myportfolio.mvp.MvpActivity;
import com.panwrona.myportfolio.screen_contact.ContactActivity;
import com.panwrona.myportfolio.utils.GUIUtils;
import de.hdodenhof.circleimageview.CircleImageView;

public class AboutMeActivity extends MvpActivity<AboutMeView, AboutMePresenter>
	implements AboutMeView, ViewPager.OnPageChangeListener, AppBarLayout.OnOffsetChangedListener,
	ViewTreeObserver.OnGlobalLayoutListener {
	private static final String TAG = AboutMeActivity.class.getSimpleName();
	private static final float PERCENTAGE_TO_SCALE_TITLE_DETAILS = 0.21f;
	private static final float PERCENTAGE_TO_FADE_TITLE_DETAILS = 0.45f;
	private static final int COLLAPSING_ANIMATIONS_DURATION = 200;

	@Bind(R.id.activity_about_me_tl_container) TabLayout mTlContainer;
	@Bind(R.id.activity_about_me_vp_container) WrapContentHeightViewPager mVpContainer;
	@Bind(R.id.activity_about_me_nsv_container) MyNestedScrollView mNsvContainer;
	@Bind(R.id.activity_about_me_cv_container) CardView mCvContainer;
	@Bind(R.id.activity_about_me_appbar) AppBarLayout mAppBar;
	@Bind(R.id.activity_about_me_fab) FloatingActionButton mFab;
	@Bind(R.id.activity_about_me_toolbar) Toolbar mToolbar;
	@Bind(R.id.activity_about_me_civ) CircleImageView mCivMe;
	@Bind(R.id.activity_about_me_cl_main_layout) CoordinatorLayout mClMainLayout;
	@Bind(R.id.activity_about_me_tv_info) TextView mTvInfo;

	private boolean mIsTheTitlePhotoVisible = true;
	private boolean mIsTheTitleTextsVisible = true;

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
		initViews();
		AboutMePagerAdapter pagerAdapter =
			new AboutMePagerAdapter(getSupportFragmentManager(), AboutMeActivity.this);
		mVpContainer.setAdapter(pagerAdapter);
		mVpContainer.addOnPageChangeListener(this);
		mTlContainer.setupWithViewPager(mVpContainer);
		mClMainLayout.getViewTreeObserver().addOnGlobalLayoutListener(this);
	}

	private void setupEnterTransition() {
		GUIUtils.startEnterTransitionSlideUp(this, mCvContainer);
		GUIUtils.startEnterTransitionSlideDown(this, mAppBar, mToolbar);
		mClMainLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
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
		GUIUtils.startReturnTransitionSlideDown(this, AboutMeActivity.this::onBackPress,
			mCvContainer);
	}

	private void onBackPress() {
		super.onBackPressed();
	}

	@SuppressWarnings("ConstantConditions")
	private void initViews() {
		setSupportActionBar(mToolbar);
		getSupportActionBar().setTitle("");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
		mToolbar.setNavigationOnClickListener(v -> onBackPressed());
		mAppBar.addOnOffsetChangedListener(this);
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
				if (!mFab.isShown()) mFab.show();
				break;
			case ViewPager.SCROLL_STATE_DRAGGING:
				if (mFab.isShown()) mFab.hide();
				break;
			case ViewPager.SCROLL_STATE_SETTLING:
				if (!mFab.isShown()) mFab.show();
				break;
		}
	}

	@Override
	public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
		int maxScroll = mAppBar.getTotalScrollRange();
		float percentage = (float) Math.abs(i) / (float)maxScroll;
		handleCircularImageViewScale(percentage);
		handleTextViewFade(percentage);
	}

	private void handleCircularImageViewScale(float percentage) {
		if (percentage >= PERCENTAGE_TO_SCALE_TITLE_DETAILS) {
			if (mIsTheTitlePhotoVisible) {
				startScaleAnimationDown(mCivMe);
				mIsTheTitlePhotoVisible = false;
			}
		} else {
			if (!mIsTheTitlePhotoVisible) {
				startScaleAnimationUp(mCivMe);
				mIsTheTitlePhotoVisible = true;
			}
		}
	}

	private void handleTextViewFade(float percentage) {
		if (percentage >= PERCENTAGE_TO_FADE_TITLE_DETAILS) {
			if (mIsTheTitleTextsVisible) {
				startFadeOutAnimation(mTvInfo);
				mIsTheTitleTextsVisible = false;
			}
		} else {
			if (!mIsTheTitleTextsVisible) {
				startFadeInAnimation(mTvInfo);
				mIsTheTitleTextsVisible = true;
			}
		}
	}

	private void startFadeInAnimation(TextView textView) {
		Animation animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
		animation.setDuration(COLLAPSING_ANIMATIONS_DURATION);
		textView.startAnimation(animation);
		textView.setVisibility(View.VISIBLE);
	}

	private void startFadeOutAnimation(TextView textView) {
		Animation animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
		animation.setDuration(COLLAPSING_ANIMATIONS_DURATION);
		textView.startAnimation(animation);
		textView.setVisibility(View.INVISIBLE);
	}

	private void startScaleAnimationDown(CircleImageView civ) {
		ScaleAnimation scaleAnimation =
			new ScaleAnimation(1f, 0f, 1f, 0f, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		scaleAnimation.setDuration(COLLAPSING_ANIMATIONS_DURATION);
		civ.startAnimation(scaleAnimation);
		civ.setVisibility(View.INVISIBLE);
	}

	private void startScaleAnimationUp(CircleImageView civ) {
		ScaleAnimation scaleAnimation =
			new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		scaleAnimation.setDuration(COLLAPSING_ANIMATIONS_DURATION);
		civ.setVisibility(View.VISIBLE);
		civ.startAnimation(scaleAnimation);
	}

	@Override
	public void onGlobalLayout() {
		new Handler(Looper.getMainLooper()).post(() -> setupEnterTransition());
	}
}
