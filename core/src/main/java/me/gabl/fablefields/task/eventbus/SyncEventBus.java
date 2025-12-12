package me.gabl.fablefields.task.eventbus;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.Map.Entry;

public class SyncEventBus implements EventBus {

    private final Map<Class<?>, Set<Subscriber>> subscribers;
    private final Map<Object, Map<Class<?>, Set<Subscriber>>> listeners;

    private final Map<Class<?>, Set<Class<?>>> hierarchy;

    public SyncEventBus() {
        this.subscribers = new HashMap<>();
        this.listeners = new HashMap<>();
        this.hierarchy = new HashMap<>();
    }

    @Override
    public void addListener(Object listener) {
        Map<Class<?>, Set<Subscriber>> containedSubscribers = MethodScanner.findSubscribers(listener);
        this.listeners.put(listener, containedSubscribers);
        for (Entry<Class<?>, Set<Subscriber>> entry : containedSubscribers.entrySet()) {
            subscribers.computeIfAbsent(entry.getKey(), (_any) -> new HashSet<>()).addAll(entry.getValue());
        }
    }

    @Override
    public void removeListener(Object listener) {
        Map<Class<?>, Set<Subscriber>> removedSubscribers = this.listeners.remove(listener);
        for (Entry<Class<?>, Set<Subscriber>> entry : removedSubscribers.entrySet()) {
            if (!subscribers.containsKey(entry.getKey())) {
                continue;
            }
            Set<Subscriber> classSubscribers = subscribers.get(entry.getKey());
            classSubscribers.removeAll(entry.getValue());
            if (classSubscribers.isEmpty()) {
                subscribers.remove(entry.getKey());
            }
        }
        removedSubscribers.forEach((key, value) -> subscribers.get(key).removeAll(value));
    }

    @Override
    public void fire(Object event) {
        Class<?> eventClass = event.getClass();
        for (Class<?> hierarchyClass : collectHierarchy(eventClass)) {
            if (!subscribers.containsKey(hierarchyClass)) {
                continue;
            }
            for (Subscriber subscriber : subscribers.get(hierarchyClass)) {
                invoke(subscriber, event);
            }
        }
    }

    private Set<Class<?>> collectHierarchy(Class<?> concrete) {
        return hierarchy.computeIfAbsent(concrete, concreteClass -> {
            Set<Class<?>> hierarchy = new HashSet<>();
            Stack<Class<?>> stack = new Stack<>();
            stack.push(concreteClass);
            while (!stack.isEmpty()) {
                Class<?> current = stack.pop();
                if (hierarchy.contains(current)) {
                    continue;
                }
                hierarchy.add(current);
                if (current.getSuperclass() != null) {
                    stack.push(current.getSuperclass());
                }
                Arrays.stream(current.getInterfaces()).forEach(stack::push);
            }
            return hierarchy;
        });
    }

    private void invoke(Subscriber subscriber, Object event) {
        try {
            subscriber.invoke(event);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
