package com.panwrona.myportfolio.screen_about_me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Bind;
import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.mvp.MvpFragment;
import com.panwrona.myportfolio.screen_about_me.views.AboutMeItem;
import com.panwrona.myportfolio.screen_about_me.views.AboutMeRecyclerViewAdapter;
import java.util.ArrayList;
import java.util.List;

public class AboutMeFragment extends MvpFragment<AboutMeFragmentView, AboutMeFragmentPresenter> implements AboutMeFragmentView {
	public static final String TAG = AboutMeFragment.class.getSimpleName();

	@Bind(R.id.fragment_about_me_rv) RecyclerView mRecyclerView;

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

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		List<AboutMeItem> aboutMeItemList = new ArrayList<>();
		aboutMeItemList.add(new AboutMeItem(R.drawable.about_me_aboutme, "About Me"));
		aboutMeItemList.add(new AboutMeItem(R.drawable.about_me_strengths, "Strengths"));
		aboutMeItemList.add(new AboutMeItem(R.drawable.about_me_school, "School"));
		aboutMeItemList.add(new AboutMeItem(R.drawable.about_me_passion, "Passion"));
		aboutMeItemList.add(new AboutMeItem(R.drawable.about_me_contact, "Contact"));

		mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
		mRecyclerView.setAdapter(new AboutMeRecyclerViewAdapter(aboutMeItemList));
	}
}
