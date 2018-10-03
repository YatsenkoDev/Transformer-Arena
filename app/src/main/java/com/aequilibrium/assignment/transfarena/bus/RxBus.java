package com.aequilibrium.assignment.transfarena.bus;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

@Singleton
public class RxBus {

    private final Subject<Object> busSubject = PublishSubject.create().toSerialized();

    @Inject
    public RxBus() {
    }

    public <T> Disposable register(final Class<T> eventClass, Consumer<T> onNext) {
        return busSubject
                .filter(event -> event.getClass().equals(eventClass))
                .map(o -> (T) o)
                .subscribe(onNext);
    }

    public void post(Object event) {
        busSubject.onNext(event);
    }

}