package com.panwrona.myportfolio.data.api;

import com.panwrona.myportfolio.data.entities.GithubRepoResponse;
import retrofit.http.GET;
import rx.Observable;

public interface RestAdapter {

	class Nodes {
		public static final String users = "/users";
		public static final String username = "/panwrona";
		public static final String repos = "/repos";
	}

	@GET(Nodes.users + Nodes.username + Nodes.repos)
	Observable<GithubRepoResponse> getGithubRepos();
}
