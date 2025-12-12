package me.gabl.fablefields.task.eventbus;

import lombok.Getter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Getter
public class Subscriber {

    private final Subscribe subscription;
    private final Object listener;
    private final Method method;
    private final Class<?> event;


    public Subscriber(Subscribe subscription, Object listener, Method method, Class<?> event) {
        this.subscription = subscription;
        this.listener = listener;
        this.method = method;
        this.event = event;
    }

    public void invoke(Object event) throws InvocationTargetException, IllegalAccessException {
        method.invoke(listener, event);
    }
}
