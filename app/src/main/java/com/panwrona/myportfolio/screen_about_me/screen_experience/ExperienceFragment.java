package com.panwrona.myportfolio.screen_about_me.screen_experience;

import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.mvp.MvpFragment;
import com.panwrona.myportfolio.utils.Constants;
import com.panwrona.myportfolio.utils.IntentUtils;

import butterknife.OnClick;

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

	@OnClick(R.id.fragment_experience_btn_droids_blog)
	public void onDroidsBlogClicked() {
		IntentUtils.startBrowser(getActivity(), Constants.DOR_BLOG_URL);
	}

	@OnClick(R.id.fragment_experience_btn_droids_homepage)
	public void onDroidsHomepageClicked() {
		IntentUtils.startBrowser(getActivity(), Constants.DOR_URL);
	}

	@OnClick(R.id.fragment_experience_btn_siemens)
	public void onSiemensClicked() {
		IntentUtils.startBrowser(getActivity(), Constants.SIEMENS_URL);
	}
}
