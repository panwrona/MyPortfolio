package com.panwrona.myportfolio.screen_passion.screen_boards;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;
import android.widget.TextView;

import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.mvp.MvpFragment;
import com.panwrona.myportfolio.utils.GUIUtils;

import butterknife.Bind;

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
