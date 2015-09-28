package com.panwrona.myportfolio.screen_about_me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Gravity;
import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.mvp.MvpActivity;
import com.panwrona.myportfolio.screen_main.MainActivity;

public class AboutMeActivity extends MvpActivity<AboutMeView, AboutMePresenter> implements AboutMeView {

	@Override
	protected AboutMePresenter createPresenter() {
		return new AboutMePresenterImp();
	}

	@Override
	protected Transition getEnterTransition() {
		Slide slideTransition = new Slide();
		slideTransition.setSlideEdge(Gravity.START);
		slideTransition.setDuration(500);
		return slideTransition;	}

	@Override
	protected Transition getExitTransition() {
		return null;
	}

	@Override
	protected Transition getReturnTransition() {
		return null;
	}

	@Override
	protected Transition getReenterTransition() {
		return null;
	}

	@Override
	protected int getLayout() {
		return R.layout.activity_about_me;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportFragmentManager()
			.beginTransaction()
			.replace(R.id.activity_about_me_fl_container,AboutMeFragment.newInstance(), AboutMeFragment.TAG)
			.commit();
	}

	public static void startActivity(Context context) {
		Intent intent = new Intent(context.getApplicationContext(), AboutMeActivity.class);
		context.startActivity(intent);
	}
}
