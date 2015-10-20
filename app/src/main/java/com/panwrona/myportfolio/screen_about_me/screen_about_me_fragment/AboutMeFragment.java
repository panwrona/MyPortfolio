package com.panwrona.myportfolio.screen_about_me.screen_about_me_fragment;

import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.mvp.MvpFragment;

public class AboutMeFragment extends MvpFragment<AboutMeFragmentView, AboutMeFragmentPresenter> implements AboutMeFragmentView {
	public static final String TAG = AboutMeFragment.class.getSimpleName();

	public static AboutMeFragment newInstance() {
		return new AboutMeFragment();
	}

	@Override
	protected AboutMeFragmentPresenter createPresenter() {
		return new AboutMeFragmentPresenterImp();
	}

	@Override
	public int getLayout() {
		return R.layout.fragment_about_me;
	}
}
