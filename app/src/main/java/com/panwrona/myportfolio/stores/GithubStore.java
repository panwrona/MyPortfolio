package com.panwrona.myportfolio.stores;

import com.panwrona.myportfolio.actions.DataBundle;
import com.panwrona.myportfolio.actions.github_actions.GithubAction;
import com.panwrona.myportfolio.buses.ActionBus;
import com.panwrona.myportfolio.buses.DataBus;
import com.panwrona.myportfolio.data.entities.GithubRepo;
import com.panwrona.myportfolio.data.entities.Owner;
import com.panwrona.myportfolio.data.event_entities.GithubOwner;
import com.panwrona.myportfolio.data.event_entities.GithubRepoList;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class GithubStore {
	private List<GithubRepo> githubRepos;
	private Owner owner;
	private DataBus dataBus;

	@Inject
	public GithubStore(ActionBus actionBus, DataBus dataBus) {
		this.dataBus = dataBus;
		dataBus.register(this);
		actionBus.register(this);
		githubRepos = new ArrayList<>();
	}

	public List<GithubRepo> getReposList() {
		return githubRepos;
	}

	@Subscribe
	public void onGithubActionReceived(GithubAction githubAction) {
		DataBundle data = githubAction.getData();

		switch (githubAction.getType()) {
			case DOWNLOAD_REPOS:
				this.githubRepos = (List<GithubRepo>) data.get(GithubAction.GithubDataKey.DESCRIPTION, null);
				dataBus.post(new GithubRepoList(this.githubRepos));
				break;
			case GET_OWNER:
				this.owner = (Owner)data.get(GithubAction.GithubDataKey.DESCRIPTION, null);
				dataBus.post(new GithubOwner(this.owner));
				break;
		}
	}
}
