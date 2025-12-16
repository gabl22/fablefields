package me.gabl.fablefields.task.eventbus;

public interface EventBus {

    /**
     * Adds a listener to this EventBus. The listener will receive events via methods that are annotated
     * with {@link Subscribe} and have exactly one parameter.
     * @param listener listener
     */
    void addListener(Object listener);

    /**
     * Removes a previously registered listener from this EventBus. The listener will no longer receive events.
     *
     * @param listener listener, added using {@link #addListener(Object)}
     */
    void removeListener(Object listener);

    /**
     * Triggers all subscribers to receive the given event.
     * @param event event to fire across the EventBus
     */
    void fire(Object event);
}
