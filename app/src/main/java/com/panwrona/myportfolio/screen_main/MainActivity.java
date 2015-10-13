package com.panwrona.myportfolio.screen_main;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.transition.Fade;
import android.transition.Transition;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import butterknife.Bind;
import butterknife.OnClick;
import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.buses.DataBus;
import com.panwrona.myportfolio.mvp.MvpActivity;
import com.panwrona.myportfolio.screen_about_me.AboutMeActivity;
import com.panwrona.myportfolio.screen_main.di.MainActivityComponent;
import com.panwrona.myportfolio.screen_contact.ContactActivity;
import com.panwrona.myportfolio.utils.GUIUtils;
import com.panwrona.myportfolio.utils.RequestCodes;
import de.hdodenhof.circleimageview.CircleImageView;
import javax.inject.Inject;

public class MainActivity extends MvpActivity<MainActivityView, MainActivityPresenter>
	implements MainActivityView {
	private static final String TAG = MainActivity.class.getSimpleName();

	@Inject DataBus dataBus;

	@Bind(R.id.activity_main_rl_placeholder) RelativeLayout mRlPlaceholder;
	@Bind(R.id.toolbar_rl) RelativeLayout mRlToolbarMainLayout;
	@Bind(R.id.activity_main_fab) FloatingActionButton mFab;
	@Bind(R.id.activity_main_iv_logo) CircleImageView mIvLogo;
	@Bind(R.id.activity_main_cl_container) CoordinatorLayout mClContainer;
	@Bind(R.id.activity_main_ll_container) LinearLayout mLlContainer;

	private MainActivityComponent component;

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
		component = MainActivityComponent.Initializer.init(this);
		component.inject(this);
		super.onCreate(savedInstanceState);
		dataBus.register(this);
		setEnterTransition();
	}

	private void setEnterTransition() {
		Fade fade = new Fade(Fade.IN);
		fade.setDuration(300);
		fade.addListener(new Transition.TransitionListener() {
			@Override
			public void onTransitionStart(Transition transition) {

			}

			@Override
			public void onTransitionEnd(Transition transition) {
				startEnterTransitions();
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
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == Activity.RESULT_OK) {
			if(requestCode == RequestCodes.ABOUT_ME) {
				startEnterTransitions();
			}
		}
	}

	private void startEnterTransitions() {
		GUIUtils.startEnterTransitionSlideDown(this, mRlPlaceholder);
		GUIUtils.startEnterTransitionSlideUp(this, mLlContainer);
		GUIUtils.startScaleUpAnimation(this, mIvLogo);
		mFab.show();
	}

	@Override
	protected void onDestroy() {
		dataBus.unregister(this);
		super.onDestroy();
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
		ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
		startActivityForResult(new Intent(this, AboutMeActivity.class), RequestCodes.ABOUT_ME,
			options.toBundle());
	}

	@OnClick(R.id.activity_main_fab)
	public void onFabClicked() {
		ActivityOptions options =
			ActivityOptions.makeSceneTransitionAnimation(this, mFab, mFab.getTransitionName());
		startActivity(new Intent(this, ContactActivity.class), options.toBundle());
	}

	public MainActivityComponent getComponent() {
		return component;
	}
}
