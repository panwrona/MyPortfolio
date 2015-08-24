package com.panwrona.myportfolio.data.api;

import com.panwrona.myportfolio.data.entities.GithubRepo;
import com.panwrona.myportfolio.data.entities.GithubRepoResponse;
import com.panwrona.myportfolio.data.entities.OwnerResponse;

import java.util.List;

import rx.Observable;

public interface RestApi {

	Observable<List<GithubRepo>> getGithubRepos();
	Observable<OwnerResponse> getOwner();
}
