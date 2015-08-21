package com.panwrona.myportfolio.actions.github_actions;

import com.panwrona.myportfolio.actions.Action;
import com.panwrona.myportfolio.actions.ActionType;
import com.panwrona.myportfolio.actions.DataBundle;
import com.panwrona.myportfolio.actions.DataKey;

public class GithubAction implements Action {
	private GithubActionType type;
	private DataBundle<GithubDataKey> bundle;

	public GithubAction(GithubActionType type) {
		this.type = type;
		this.bundle = new DataBundle<>();
	}

	public GithubAction(GithubActionType type, DataBundle<GithubDataKey> bundle) {
		this.type = type;
		this.bundle = bundle;
	}

	@Override public GithubActionType getType() {
		return type;
	}

	@Override public DataBundle getData() {
		return bundle;
	}

	public enum GithubActionType implements ActionType {
		DOWNLOAD_REPOS,
		GET_OWNER
	}

	public enum GithubDataKey implements DataKey {
		ID,
		DESCRIPTION
	}
}
