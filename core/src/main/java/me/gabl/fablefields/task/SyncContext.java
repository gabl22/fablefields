package me.gabl.fablefields.task;

import lombok.RequiredArgsConstructor;

//seconds
@RequiredArgsConstructor
public class SyncContext {

    private final Scheduler scheduler;
    protected float overTime; // time between actual and planned execution, non-negative
    protected Task task;

    public void reschedule() {
        scheduler.schedule(task);
    }

    // catching up
    public void rescheduleFixRate(float delay) {
        scheduler.schedule(task, delay - overTime);
    }

    public void rescheduleFixDelay(float delay) {
        scheduler.schedule(task, delay);
    }
}
