package com.panwrona.myportfolio.screen_main.views;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.screen_coding.CodingActivity;
import com.panwrona.myportfolio.screen_main.MainActivity;
import com.panwrona.myportfolio.screen_main.views.interfaces.OnAboutMeViewItemClickListener;
import com.panwrona.myportfolio.screen_main.views.interfaces.OnCodingViewItemClickListener;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter {
	public static final int ITEM_ABOUT_ME = 0;
	public static final int ITEM_CODING = 1;
	public static final int ITEM_SPORT = 2;
	public static final int ITEM_CREDITS = 3;

	private final MainActivity mainActivity;

	public MainRecyclerViewAdapter(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}

	@Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View view;
		switch (i) {
			case ITEM_ABOUT_ME:
				view = LayoutInflater.from(viewGroup.getContext())
					.inflate(R.layout.cardview_main_about_me, viewGroup, false);
				return new AboutMeViewHolder(view, onAboutMeViewItemClickListener);
			case ITEM_CODING:
				view = LayoutInflater.from(viewGroup.getContext())
					.inflate(R.layout.cardview_main_coding, viewGroup, false);
				return new CodingViewHolder(view);
			case ITEM_SPORT:
				view = LayoutInflater.from(viewGroup.getContext())
					.inflate(R.layout.cardview_main_sport, viewGroup, false);
				return new SportViewHolder(view);
			case ITEM_CREDITS:
				view = LayoutInflater.from(viewGroup.getContext())
					.inflate(R.layout.cardview_main_credits, viewGroup, false);
				return new CreditsViewHolder(view);
		}
		return null;
	}

	@Override public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

	}

	@Override public int getItemCount() {
		return 4;
	}

	@Override
	public int getItemViewType(int position) {
		switch(position) {
			case 0:
				return ITEM_ABOUT_ME;
			case 1:
				return ITEM_CODING;
			case 2:
				return ITEM_SPORT;
			case 3:
				return ITEM_CREDITS;
		}
		return super.getItemViewType(position);
	}

	private OnCodingViewItemClickListener onRecyclerViewItemClicked = new OnCodingViewItemClickListener() {
		@Override
		public void onItemClick(CodingViewHolder viewHolder) {

		}
	};

	private OnAboutMeViewItemClickListener onAboutMeViewItemClickListener = new OnAboutMeViewItemClickListener() {
		@Override
		public void onItemClick(AboutMeViewHolder viewHolder) {
			Intent intent = new Intent(mainActivity, CodingActivity.class);
			int[] location = new int[2];
			ImageView aboutMeLogo = viewHolder.getIvAboutMeLogo();
			aboutMeLogo.getLocationInWindow(location);

			Point epicenter = new Point(location[0] + aboutMeLogo.getMeasuredWidth() / 2,
				location[1] + aboutMeLogo.getMeasuredHeight() / 2);
			intent.putExtra(CodingActivity.EXTRA_EPICENTER, epicenter);

			ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(mainActivity,
				aboutMeLogo, aboutMeLogo.getTransitionName());
			mainActivity.startActivity(intent, options.toBundle());
		}
	};
}
