package com.panwrona.myportfolio.screen_about_me.screen_education;

import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.mvp.MvpFragment;

public class EducationFragment extends MvpFragment<EducationFragmentView, EducationFragmentPresenter> implements EducationFragmentView {
	@Override
	protected EducationFragmentPresenter createPresenter() {
		return new EducationFragmentPresenterImpl();
	}

	@Override
	public int getLayout() {
		return R.layout.fragment_education;
	}

	public static EducationFragment newInstance() {
		return new EducationFragment();
	}
}
