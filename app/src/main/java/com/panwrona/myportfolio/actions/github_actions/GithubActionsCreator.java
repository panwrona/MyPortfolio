package com.panwrona.myportfolio.actions.github_actions;

import com.panwrona.myportfolio.dispatcher.Dispatcher;

public class GithubActionsCreator {
	private static GithubActionsCreator instance;
	final Dispatcher dispatcher;

	public GithubActionsCreator(Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	public static GithubActionsCreator get(Dispatcher dispatcher) {
		if(instance == null) {
			instance = new GithubActionsCreator(dispatcher);
		}
		return instance;
	}

	public void downloadRepos() {
		dispatcher.dispatch(GithubActions.DOWNLOAD_REPOS);
	}

	public void getOwner() {
		dispatcher.dispatch(GithubActions.GET_OWNER);
	}
}
