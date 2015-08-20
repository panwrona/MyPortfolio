package com.panwrona.myportfolio.stores;

import com.panwrona.myportfolio.actions.Action;
import com.panwrona.myportfolio.dispatcher.Dispatcher;

public abstract class Store {

	final Dispatcher dispatcher;

	protected Store(Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	void emitStoreChange() {
		dispatcher.emitChange(changeEvent());
	}

	abstract StoreChangeEvent changeEvent();
	public abstract void onAction(Action action);
	public interface StoreChangeEvent{}
}
