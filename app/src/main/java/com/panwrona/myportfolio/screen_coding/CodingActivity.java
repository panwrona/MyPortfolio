package com.panwrona.myportfolio.screen_coding;

import android.transition.Transition;
import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.mvp.MvpActivity;

public class CodingActivity extends MvpActivity<CodingView, CodingPresenter> implements CodingView {

	@Override protected CodingPresenter createPresenter() {
		return new CodingPresenterImp();
	}

	@Override protected Transition getEnterTransition() {
		return null;
	}

	@Override protected Transition getExitTransition() {
		return null;
	}

	@Override protected Transition getReturnTransition() {
		return null;
	}

	@Override protected Transition getReenterTransition() {
		return null;
	}

	@Override protected int getLayout() {
		return R.layout.activity_coding;
	}
}
