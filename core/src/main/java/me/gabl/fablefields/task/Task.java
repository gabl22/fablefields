package me.gabl.fablefields.task;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class Task implements Comparable<Task> {
    public Consumer<SyncContext> action;
    double nextExecution;

    public Task(Consumer<SyncContext> action) {
        this.action = action;
    }

    public Task() {}

    @Override
    public int compareTo(@NotNull Task that) {
        return Double.compare(this.nextExecution, that.nextExecution);
    }
}
