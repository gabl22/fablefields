package me.gabl.fablefields.task;

import java.util.PriorityQueue;
import java.util.function.Consumer;

public class ActSynchronousScheduler implements Scheduler {

    private final PriorityQueue<Task> tasks;
    private final SyncContext context;
    private double relativeTime;

    public ActSynchronousScheduler() {
        this.tasks = new PriorityQueue<>();
        this.context = new SyncContext(this);
    }

    public void commitExecute(float delta) {
        commit(delta);
        executeTasks();
    }

    public void commit(float delta) {
        relativeTime += delta;
    }

    private void executeTasks() {
        while (!tasks.isEmpty()) {
            Task task = tasks.peek();
            if (task.nextExecution > relativeTime)
                return;
            Consumer<SyncContext> action = tasks.remove().action;
            if (action == null)
                continue;
            context.overTime = (float) (relativeTime - task.nextExecution);
            context.task = task;
            action.accept(context);
        }
    }

    //in seconds
    @Override
    public void schedule(Task task, float delay) {
        task.nextExecution = relativeTime + delay;
        addTask(task);
    }

    @Override
    public void setDelay(Task task, float delay) {
        task.nextExecution = relativeTime + delay;
    }

    /**
     * Schedules the task asap
     */
    @Override
    public void schedule(Task task) {
        task.nextExecution = Double.NEGATIVE_INFINITY;
        addTask(task);
    }

    @Override
    public boolean isScheduled(Task task) {
        return tasks.contains(task);
    }

    private void addTask(Task task) {
        tasks.add(task);
        if (task instanceof SchedulerTask schedulerTask) {
            schedulerTask.setScheduler(this);
        }
    }
}
