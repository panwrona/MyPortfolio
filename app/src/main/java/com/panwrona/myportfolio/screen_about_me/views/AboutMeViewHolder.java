package com.panwrona.myportfolio.screen_about_me.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.panwrona.myportfolio.R;

public class AboutMeViewHolder extends RecyclerView.ViewHolder {

	@Bind(R.id.item_aboutme_tv_title) TextView mTvTitle;
	@Bind(R.id.item_aboutme_iv_icon) ImageView mIvIcon;

	public AboutMeViewHolder(View itemView) {
		super(itemView);
		ButterKnife.bind(this, itemView);
	}

	public void setIcon(int icon) {
		mIvIcon.setImageResource(icon);
	}

	public void setTitle(String title) {
		mTvTitle.setText(title);
	}
}
