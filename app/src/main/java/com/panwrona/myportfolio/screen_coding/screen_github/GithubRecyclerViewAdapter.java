package com.panwrona.myportfolio.screen_coding.screen_github;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.panwrona.myportfolio.R;
import com.panwrona.myportfolio.data.entities.GithubRepo;

import java.util.ArrayList;
import java.util.List;


public class GithubRecyclerViewAdapter extends RecyclerView.Adapter<GithubViewHolder> {

    private List<GithubRepo> mGithubRepos = new ArrayList<>();

    @Override
    public GithubViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_github, parent, false);
        return new GithubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GithubViewHolder holder, int position) {
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
