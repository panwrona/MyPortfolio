package com.panwrona.myportfolio.screen_main;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
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
import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.mvp.MvpActivity;
import com.panwrona.myportfolio.screen_about_me.AboutMeActivity;
import com.panwrona.myportfolio.screen_coding.CodingActivity;
import com.panwrona.myportfolio.screen_contact.ContactActivity;
import com.panwrona.myportfolio.screen_passion.PassionActivity;
import com.panwrona.myportfolio.utils.GUIUtils;
import com.panwrona.myportfolio.utils.RequestCodes;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends MvpActivity<MainActivityView, MainActivityPresenter>
	implements MainActivityView, ViewTreeObserver.OnGlobalLayoutListener {
	private static final String TAG = MainActivity.class.getSimpleName();

	@Bind(R.id.activity_main_rl_placeholder) RelativeLayout mRlPlaceholder;
	@Bind(R.id.activity_main_fab) FloatingActionButton mFab;
	@Bind(R.id.activity_main_iv_logo) CircleImageView mIvLogo;
	@Bind(R.id.activity_main_cl_container) CoordinatorLayout mClContainer;
	@Bind(R.id.activity_main_ll_container) LinearLayout mLlContainer;

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
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == RequestCodes.NEW_ACTIVITY) {
				startEnterTransitions();
			}
		}
	}

	private void startEnterTransitions() {
		GUIUtils.startEnterTransitionSlideDown(this, mRlPlaceholder);
		GUIUtils.startEnterTransitionSlideUp(this, mLlContainer);
		GUIUtils.startScaleUpAnimation(this, mIvLogo);
		mFab.show();
		mClContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
	}

	@OnClick(R.id.activity_main_tv_about_me)
	public void onAboutMeClicked() {
		GUIUtils.startReturnTransitionSlideUp(this, null, mRlPlaceholder);
		GUIUtils.startReturnTransitionSlideDown(this, MainActivity.this::startAboutMeActivity,
			mLlContainer);
		GUIUtils.startScaleDownAnimation(this, mIvLogo);
		mFab.hide();
	}

	private void startAboutMeActivity() {
		//noinspection unchecked
		ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
		startActivityForResult(new Intent(this, AboutMeActivity.class), RequestCodes.NEW_ACTIVITY,
			options.toBundle());
	}

	@OnClick(R.id.activity_main_tv_coding)
	public void onCodingClicked() {
		GUIUtils.startReturnTransitionSlideUp(this, null, mRlPlaceholder);
		GUIUtils.startReturnTransitionSlideDown(this, MainActivity.this::startCodingActivity,
			mLlContainer);
		GUIUtils.startScaleDownAnimation(this, mIvLogo);
		mFab.hide();
	}

	private void startCodingActivity() {
		//noinspection unchecked
		ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
		startActivityForResult(new Intent(this, CodingActivity.class), RequestCodes.NEW_ACTIVITY,
			options.toBundle());
	}

	@OnClick(R.id.activity_main_tv_passion)
	public void onPassionClicked() {
		GUIUtils.startReturnTransitionSlideUp(this, null, mRlPlaceholder);
		GUIUtils.startReturnTransitionSlideDown(this, MainActivity.this::startPassionActivity,
			mLlContainer);
		GUIUtils.startScaleDownAnimation(this, mIvLogo);
		mFab.hide();
	}

	private void startPassionActivity() {
		//noinspection unchecked
		startActivityForResult(new Intent(this, PassionActivity.class), RequestCodes.NEW_ACTIVITY);
	}

	@OnClick(R.id.activity_main_fab)
	public void onFabClicked() {
		ActivityOptions options =
			ActivityOptions.makeSceneTransitionAnimation(this, mFab, mFab.getTransitionName());
		startActivity(new Intent(this, ContactActivity.class), options.toBundle());
	}

	@Override
	public void onGlobalLayout() {
		new Handler(Looper.getMainLooper()).post(this::fadeInEnterTransitions);
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
