package com.panwrona.myportfolio.data.api;

import com.panwrona.myportfolio.data.entities.GithubRepo;
import com.panwrona.myportfolio.data.entities.OwnerResponse;
import com.panwrona.myportfolio.utils.Constants;

import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.android.AndroidLog;

public final class ApiManager implements RestApi {

	private RestAdapter restAdapter;
	private final RequestInterceptor requestInterceptor;

	public ApiManager() {
		requestInterceptor = request -> request.addHeader(Constants.USER_AGENT, "panwrona");
		retrofit.RestAdapter restAdapter = initRestAdapter();
		this.restAdapter = restAdapter.create(RestAdapter.class);
	}

	private retrofit.RestAdapter initRestAdapter() {
		return new retrofit.RestAdapter.Builder().
			setLogLevel(retrofit.RestAdapter.LogLevel.FULL).
			setLog(new AndroidLog("TEST")).
			setRequestInterceptor(requestInterceptor).
			setEndpoint(Constants.REST_ENDPOINT).
			build();
	}

	@Override
	public void getGithubRepos(Callback<List<GithubRepo>> callback) {
		restAdapter.getGithubRepos(callback);
	}
}
