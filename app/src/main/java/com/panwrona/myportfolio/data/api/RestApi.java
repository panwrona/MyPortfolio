package com.panwrona.myportfolio.data.api;

import com.panwrona.myportfolio.data.entities.GithubRepo;
import com.panwrona.myportfolio.data.entities.OwnerResponse;

import java.util.List;

import retrofit.Callback;

interface RestApi {
	void getGithubRepos(Callback<List<GithubRepo>> callback);
}
