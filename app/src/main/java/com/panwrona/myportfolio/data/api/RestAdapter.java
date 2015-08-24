package com.panwrona.myportfolio.data.api;

import com.panwrona.myportfolio.data.entities.GithubRepo;
import com.panwrona.myportfolio.data.entities.GithubRepoResponse;
import com.panwrona.myportfolio.data.entities.OwnerResponse;

import java.util.List;

import retrofit.http.GET;
import rx.Observable;

public interface RestAdapter {

	class Nodes {
		public static final String users = "/users";
		public static final String username = "/panwrona";
		public static final String repos = "/repos";
	}

	@GET(Nodes.users + Nodes.username + Nodes.repos)
	Observable<List<GithubRepo>> getGithubRepos();

	@GET(Nodes.users + Nodes.username)
	Observable<OwnerResponse> getOwner();
}
