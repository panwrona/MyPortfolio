package com.panwrona.myportfolio.screen_main.views;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.panwrona.myportfolio.R;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter {
	private static final int ITEM_ABOUT_ME = 0;
	private static final int ITEM_CODING = 1;
	private static final int ITEM_SPORT = 2;
	private static final int ITEM_CREDITS = 3;


	@Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View view;
		switch (i) {
			case ITEM_ABOUT_ME:
				view = LayoutInflater.from(viewGroup.getContext())
					.inflate(R.layout.cardview_main_about_me, viewGroup, false);
				return new AboutMeViewHolder(view);
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
}
