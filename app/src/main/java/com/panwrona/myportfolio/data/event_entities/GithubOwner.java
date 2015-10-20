package com.panwrona.myportfolio.data.event_entities;

import com.panwrona.myportfolio.data.entities.Owner;

final class GithubOwner {

	private Owner owner;

	public GithubOwner(Owner owner) {
		this.owner = owner;
	}

	public Owner getOwner() {
		return owner;
	}
}
