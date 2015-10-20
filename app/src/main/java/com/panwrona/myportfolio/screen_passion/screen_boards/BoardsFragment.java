package com.panwrona.myportfolio.screen_passion.screen_boards;

import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.mvp.MvpFragment;

public class BoardsFragment extends MvpFragment<BoardsView, BoardsPresenter> implements BoardsView {
	@Override
	protected BoardsPresenter createPresenter() {
		return new BoardsPresenterImpl();
	}

	@Override
	public int getLayout() {
		return R.layout.fragment_boards;
	}

	public static BoardsFragment newInstance() {
		return new BoardsFragment();
	}
}
