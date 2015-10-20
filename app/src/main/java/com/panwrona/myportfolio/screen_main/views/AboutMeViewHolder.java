package com.panwrona.myportfolio.screen_main.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.customviews.circle_category_views.CircleCategoryView;
import com.panwrona.myportfolio.screen_main.views.interfaces.OnAboutMeViewItemClickListener;

public final class AboutMeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

	@Bind(R.id.cardview_about_me_logo) CircleCategoryView ivAboutMeLogo;
	OnAboutMeViewItemClickListener listener;

	public AboutMeViewHolder(View view, OnAboutMeViewItemClickListener listener) {
		super(view);
		ButterKnife.bind(this, view);
		view.setOnClickListener(this);
		this.listener = listener;
	}

	@Override
	public void onClick(View v) {
		listener.onItemClick(this);
	}

	public ImageView getIvAboutMeLogo() {
		return ivAboutMeLogo;
	}
}
