package com.panwrona.myportfolio.screen_coding.screen_github;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.data.entities.GithubRepo;
import java.util.ArrayList;
import java.util.List;

class GithubRecyclerViewAdapter extends RecyclerView.Adapter<GithubViewHolder> {

	private List<GithubRepo> mGithubRepos = new ArrayList<>();

	@Override
	public GithubViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext())
			.inflate(R.layout.recycler_item_github, parent, false);
		return new GithubViewHolder(view, htmlUrl -> parent.getContext()
			.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(htmlUrl))));
	}

	@Override
	public void onBindViewHolder(GithubViewHolder holder, int position) {
		holder.getItemView().animate().alpha(1).setDuration(300).start();
		holder.updateViews(mGithubRepos.get(position));
	}

	@Override
	public int getItemCount() {
		return mGithubRepos.size();
	}

	public void setGithubRepos(List<GithubRepo> githubRepos) {
		mGithubRepos = githubRepos;
		notifyDataSetChanged();
	}
}
