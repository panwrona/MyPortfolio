package com.panwrona.myportfolio.actions;

public interface Action {
	public abstract ActionType getType();
	public abstract DataBundle getData();
}
