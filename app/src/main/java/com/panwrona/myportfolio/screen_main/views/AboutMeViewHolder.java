package com.panwrona.myportfolio.screen_main.views;

import android.app.ActivityOptions;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.panwrona.myportfolio.R;

public class AboutMeViewHolder extends RecyclerView.ViewHolder {

	@Bind(R.id.cardview_logo) ImageView imageView;

	public AboutMeViewHolder(View view) {
		super(view);
		ButterKnife.bind(view);
		imageView.setOnClickListener(v -> {
		});
	}
}
