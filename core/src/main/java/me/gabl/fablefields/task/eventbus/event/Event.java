package me.gabl.fablefields.task.eventbus.event;

import me.gabl.fablefields.task.eventbus.EventBus;

public interface Event {

    default void fire(EventBus bus) {
        bus.fire(this);
    }
}
