package com.panwrona.myportfolio.dispatcher;

import com.panwrona.myportfolio.actions.Action;
import com.panwrona.myportfolio.stores.Store;
import com.squareup.otto.Bus;

public class Dispatcher {
	private final Bus eventBus;
	private static Dispatcher instance;

	public static Dispatcher get(Bus eventBus) {
		if(instance == null) {
			instance = new Dispatcher(eventBus);
		}
		return instance;
	}

	Dispatcher(Bus eventBus) {
		this.eventBus = eventBus;
	}

	public void register(final Object obj) {
		eventBus.register(obj);
	}

	public void unregister(final Object obj) {
		eventBus.unregister(obj);
	}

	public void emitChange(Store.StoreChangeEvent event) {
		post(event);
	}

	public void dispatch(String type, Object... data) {
		if (isEmpty(type)) {
			throw new IllegalArgumentException("Type must not be empty");
		}

		if (data.length % 2 != 0) {
			throw new IllegalArgumentException("Data must be a valid list of key,value pairs");
		}

		Action.Builder actionBuilder = Action.type(type);
		int i = 0;
		while (i < data.length) {
			String key = (String) data[i++];
			Object value = data[i++];
			actionBuilder.bundle(key, value);
		}
		post(actionBuilder.build());
	}

	private boolean isEmpty(String type) {
		return type == null || type.isEmpty();
	}

	private void post(final Object event) {
		eventBus.post(event);
	}
}
