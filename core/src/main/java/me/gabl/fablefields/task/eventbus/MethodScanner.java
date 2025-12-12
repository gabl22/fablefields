package me.gabl.fablefields.task.eventbus;

import java.lang.reflect.Method;
import java.util.*;

public class MethodScanner {

    static Map<Class<?>, Set<Subscriber>> findSubscribers(Object listener) {
        Class<?> type = listener.getClass();
        Map<Class<?>, Set<Subscriber>> result = new HashMap<>();
        for (Class<?> currentClass = type; currentClass != null && !Object.class.equals(currentClass); currentClass =
                currentClass.getSuperclass()) {
            for (Method method : currentClass.getDeclaredMethods()) {
                Subscribe subscribe = method.getAnnotation(Subscribe.class);
                if (subscribe == null) {
                    continue;
                }

                if (method.getParameterCount() != 1) {
                    throw new IllegalArgumentException("Method " + method + " must have exactly one parameter.");
                }
                add(result, new Subscriber(subscribe, listener, method, method.getParameterTypes()[0]));
            }
        } return result;
    }

    private static void add(Map<Class<?>, Set<Subscriber>> subscribers, Subscriber subscriber) {
        subscribers.computeIfAbsent(subscriber.getEvent(), t -> new HashSet<>()).add(subscriber);
    }
}
