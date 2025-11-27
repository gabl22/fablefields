package me.gabl.fablefields.task;

public interface Scheduler {

    //in seconds
    void schedule(Task task, float delay);

    void setDelay(Task task, float delay);

    void schedule(Task task);

    boolean isScheduled(Task task);
}
