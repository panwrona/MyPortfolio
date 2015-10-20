package com.panwrona.myportfolio.data.event_entities;

import com.panwrona.myportfolio.data.entities.GithubRepo;
import java.util.List;

final class GithubRepoList {

	private List<GithubRepo> githubRepos;

	public GithubRepoList(List<GithubRepo> githubRepos) {
		this.githubRepos = githubRepos;
	}

	public List<GithubRepo> getGithubRepos() {
		return githubRepos;
	}
}
