package com.panwrona.myportfolio.data.api;

import com.panwrona.myportfolio.data.entities.GithubRepo;
import com.panwrona.myportfolio.data.entities.OwnerResponse;
import com.panwrona.myportfolio.utils.Constants;

import java.util.List;

import retrofit.RequestInterceptor;
import retrofit.android.AndroidLog;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ApiManager implements RestApi {

	private RestAdapter restAdapter;
	private RequestInterceptor requestInterceptor;

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

	@Override public Observable<List<GithubRepo>> getGithubRepos() {
		return restAdapter.getGithubRepos().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
	}

	private <T> Observable.Transformer<T, T> applySchedulers() {
		return observable -> observable.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread());
	}

	@Override public Observable<OwnerResponse> getOwner() {
		return restAdapter.getOwner().compose(applySchedulers());
	}
}
