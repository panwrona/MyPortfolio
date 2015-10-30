package com.panwrona.myportfolio.screen_coding.screen_tools;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.ButterKnife;

public class ToolsViewHolder extends RecyclerView.ViewHolder {


	public ToolsViewHolder(View itemView) {
		super(itemView);
		ButterKnife.bind(this, itemView);
	}
}
