package com.panwrona.myportfolio.stores;

import com.panwrona.myportfolio.actions.Action;
import com.panwrona.myportfolio.actions.github_actions.GithubActions;
import com.panwrona.myportfolio.data.entities.GithubRepo;
import com.panwrona.myportfolio.dispatcher.Dispatcher;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.List;

public class GithubStore extends Store {

	private static GithubStore instance;
	private final List<GithubRepo> reposList;

	protected GithubStore(Dispatcher dispatcher) {
		super(dispatcher);
		reposList = new ArrayList<>();
	}

	public static GithubStore get(Dispatcher dispatcher) {
		if(instance == null) {
			instance = new GithubStore(dispatcher);
		}
		return instance;
	}

	public List<GithubRepo> getReposList() {
		return reposList;
	}

	@Override StoreChangeEvent changeEvent() {
		return new GithubStoreChangeEvent();
	}

	@Subscribe
	@SuppressWarnings("unchecked")
	@Override public void onAction(Action action) {
		switch (action.getType()) {
			case GithubActions.DOWNLOAD_REPOS:

		}
	}

	public class GithubStoreChangeEvent implements StoreChangeEvent {}
}
