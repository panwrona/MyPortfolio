package com.panwrona.myportfolio.screen_passion.screen_music;

import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.mvp.MvpFragment;

public class MusicFragment extends MvpFragment<MusicView, MusicPresenter> implements MusicView {

	@Override
	protected MusicPresenter createPresenter() {
		return new MusicPresenterImpl();
	}

	@Override
	public int getLayout() {
		return R.layout.fragment_music;
	}

	public static MusicFragment newInstance() {
		return new MusicFragment();
	}
}
