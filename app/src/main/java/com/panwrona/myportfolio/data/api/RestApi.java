package com.panwrona.myportfolio.data.api;

import com.panwrona.myportfolio.data.entities.GithubRepoResponse;
import com.panwrona.myportfolio.data.entities.OwnerResponse;
import rx.Observable;

public interface RestApi {

	Observable<GithubRepoResponse> getGithubRepos();
	Observable<OwnerResponse> getOwner();
}
