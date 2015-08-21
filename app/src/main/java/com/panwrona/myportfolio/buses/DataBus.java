package com.panwrona.myportfolio.buses;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

public class DataBus extends Bus {
	public DataBus() {
		super(ThreadEnforcer.MAIN);
	}

	public DataBus(ThreadEnforcer thread) {
		super(thread);
	}

}
