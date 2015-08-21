package com.panwrona.myportfolio.buses;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;
import javax.inject.Inject;

public class ActionBus extends Bus {
	@Inject
	public ActionBus() {
		super(ThreadEnforcer.ANY);
	}
}
