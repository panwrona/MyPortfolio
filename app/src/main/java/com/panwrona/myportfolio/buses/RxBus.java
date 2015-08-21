package com.panwrona.myportfolio.buses;

import javax.inject.Inject;
import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

public class RxBus {
	private final Subject<Object, Object> _bus = new SerializedSubject<>(PublishSubject.create());

	@Inject
	public RxBus() {

	}

	public void send(Object o) {
		_bus.onNext(o);
	}

	public Observable<Object> toObserverable() {
		return _bus;
	}

	public boolean hasObservers() {
		return _bus.hasObservers();
	}

}
