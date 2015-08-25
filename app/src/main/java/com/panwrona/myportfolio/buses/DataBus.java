package com.panwrona.myportfolio.buses;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;
import javax.inject.Inject;

public class DataBus extends Bus {
	@Inject
	public DataBus() {
		super(ThreadEnforcer.MAIN);
	}

	public DataBus(ThreadEnforcer thread) {
		super(thread);
	}

}
