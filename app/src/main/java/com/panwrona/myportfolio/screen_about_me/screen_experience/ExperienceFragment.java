package com.panwrona.myportfolio.screen_about_me.screen_experience;

import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.mvp.MvpFragment;

public class ExperienceFragment extends MvpFragment<ExperienceFragmentView, ExperienceFragmentPresenter> implements ExperienceFragmentView {

	public static ExperienceFragment newInstance() {
		return new ExperienceFragment();
	}

	@Override
	protected ExperienceFragmentPresenter createPresenter() {
		return new ExperienceFragmentPresenterImpl();
	}

	@Override
	public int getLayout() {
		return R.layout.fragment_experience;
	}
}
