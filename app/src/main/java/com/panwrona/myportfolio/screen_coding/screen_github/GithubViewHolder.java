package com.panwrona.myportfolio.screen_coding.screen_github;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.data.entities.GithubRepo;

public class GithubViewHolder extends RecyclerView.ViewHolder {

	@Bind(R.id.github_tv_description) TextView mTvDescription;
	@Bind(R.id.github_tv_forks) TextView mTvForks;
	@Bind(R.id.github_tv_reponame) TextView mTvReponame;
	@Bind(R.id.github_tv_watchers) TextView mTvWatchers;
	@Bind(R.id.github_tv_stars) TextView mTvStars;
	@Bind(R.id.github_btn_see_on_github) TextView mTvSeeOnGithub;
	@Bind(R.id.recycler_item_github_rl_main) RelativeLayout mRlMain;

	private final OnGithubLinkClickListener listener;

	public GithubViewHolder(View itemView, OnGithubLinkClickListener listener) {
		super(itemView);
		ButterKnife.bind(this, itemView);
		this.listener = listener;
	}

	public void updateViews(GithubRepo githubRepo) {
		new Handler(Looper.getMainLooper()).post(() -> {
			if (githubRepo.getName() != null) {
				mTvReponame.setText(githubRepo.getName());
			}
			if (githubRepo.getDescription() != null) {
				mTvDescription.setText(githubRepo.getDescription());
			}
			mTvForks.setText("" + githubRepo.getForksCount());
			mTvStars.setText("" + githubRepo.getStargazersCount());
			mTvWatchers.setText("" + githubRepo.getWatchersCount());
			mTvSeeOnGithub.setOnClickListener(v -> listener.onGithubLinkClicked(githubRepo.getHtmlUrl()));
		});
	}

	public View getItemView() {
		return mRlMain;
	}
}
