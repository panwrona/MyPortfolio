package com.panwrona.myportfolio.screen_coding.screen_tools;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.data.database.entities.Tool;
import java.util.ArrayList;
import java.util.List;

public class ToolsRecyclewViewAdapter extends RecyclerView.Adapter<ToolsViewHolder> {

	private List<Tool> mTools = new ArrayList<>();

	@Override
	public ToolsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext())
			.inflate(R.layout.recycler_item_tool, parent, false);
		return new ToolsViewHolder(view, htmlUrl -> parent.getContext()
			.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(htmlUrl))));
	}

	@Override
	public void onBindViewHolder(ToolsViewHolder holder, int position) {
		holder.updateViews(mTools.get(position));
	}

	@Override
	public int getItemCount() {
		return mTools.size();
	}

	public void setTools(List<Tool> tools) {
		mTools = tools;
		notifyDataSetChanged();
	}
}
