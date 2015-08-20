package com.panwrona.myportfolio.data.api;

import com.panwrona.myportfolio.data.entities.GithubRepoResponse;
import com.panwrona.myportfolio.utils.Constants;
import retrofit.android.AndroidLog;
import rx.Observable;

public class ApiManager implements RestApi {

	private RestAdapter restAdapter;

	public ApiManager() {
		retrofit.RestAdapter restAdapter = initRestAdapter();
		this.restAdapter = restAdapter.create(RestAdapter.class);
	}

	private retrofit.RestAdapter initRestAdapter() {
		return new retrofit.RestAdapter.Builder().
			setLogLevel(retrofit.RestAdapter.LogLevel.FULL).
			setLog(new AndroidLog("TEST")).
			setEndpoint(Constants.REST_ENDPOINT).
			build();
	}

	@Override public Observable<GithubRepoResponse> getGithubRepos() {
		return null;
	}
}
