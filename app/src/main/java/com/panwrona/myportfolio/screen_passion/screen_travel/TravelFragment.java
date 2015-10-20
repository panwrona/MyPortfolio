package com.panwrona.myportfolio.screen_passion.screen_travel;

import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.mvp.MvpFragment;

public class TravelFragment extends MvpFragment<TravelView, TravelPresenter> implements TravelView {
	@Override
	protected TravelPresenter createPresenter() {
		return new TravelPresenterImpl();
	}

	@Override
	public int getLayout() {
		return R.layout.fragment_travel;
	}

	public static TravelFragment newInstance() {
		return new TravelFragment();
	}
}
