package com.aequilibrium.assignment.transfarena.bus;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Bus of events via RxJava Subject
 */
@Singleton
public class RxBus {

    private final Subject<Object> busSubject = PublishSubject.create().toSerialized();

    /**
     * Constructor to inject the bus instance
     */
    @Inject
    public RxBus() {
    }

    /**
     * Subsrtibes on events by class name
     *
     * @param eventClass class of event
     * @param onNext     event result consumer
     * @param <T>        generic type to support different classes
     * @return disposable of subscription
     */
    public <T> Disposable register(final Class<T> eventClass, Consumer<T> onNext) {
        return busSubject
                .filter(event -> event.getClass().equals(eventClass))
                .map(o -> (T) o)
                .subscribe(onNext);
    }

    /**
     * Notify about event
     *
     * @param event one of subscribed events
     */
    public void post(Object event) {
        busSubject.onNext(event);
    }

}