package com.panwrona.myportfolio.screen_main;

import com.panwrona.myportfolio.actions.github_actions.GithubActionsCreator;
import com.panwrona.myportfolio.buses.DataBus;
import com.panwrona.myportfolio.data.event_entities.GithubRepoList;
import com.panwrona.myportfolio.mvp.MvpBasePresenter;
import com.panwrona.myportfolio.screen_main.di.MainActivityComponent;
import com.squareup.otto.Subscribe;
import javax.inject.Inject;

public class MainActivityPresenterImpl extends MvpBasePresenter<MainActivityView>
	implements MainActivityPresenter {

	@Inject GithubActionsCreator githubActionsCreator;
	@Inject DataBus dataBus;

	public MainActivityPresenterImpl(MainActivity mainActivity) {
		((MainActivity)mainActivity).getComponent().inject(this);
	}

	@Override public void subscribe() {
		githubActionsCreator.subscribe();
	}

	@Override public void unsubscribe() {
		githubActionsCreator.unSubscribe();
	}

	@Override public void registerDataBus() {
		dataBus.register(this);
	}

	@Override public void unregisterDataBus() {
		dataBus.unregister(this);
	}

	@Override public void getGithubRepos() {
		githubActionsCreator.downloadRepos();
	}

	@Subscribe public void onGithubDataChange(GithubRepoList githubRepoList) {
		updateViews(githubRepoList);
	}

	private void updateViews(GithubRepoList githubRepoList) {
		if (isViewAttached()) {
			getView().updateReposList(githubRepoList.getGithubRepos());
		}
	}
}
