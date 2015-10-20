package com.panwrona.myportfolio.screen_about_me.views;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.panwrona.myportfolio.R;
import java.util.List;

class AboutMeRecyclerViewAdapter extends RecyclerView.Adapter<AboutMeViewHolder> {

	private final List<AboutMeItem> aboutMeItems;

	public AboutMeRecyclerViewAdapter(List<AboutMeItem> aboutMeItems) {
		this.aboutMeItems = aboutMeItems;
	}

	@Override
	public AboutMeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_aboutme_main, parent, false);
		return new AboutMeViewHolder(view);
	}

	@Override
	public void onBindViewHolder(AboutMeViewHolder holder, int position) {
		holder.setIcon(aboutMeItems.get(position).getDrawable());
		holder.setTitle(aboutMeItems.get(position).getTitle());
	}

	@Override
	public int getItemCount() {
		return aboutMeItems.size();
	}
}
