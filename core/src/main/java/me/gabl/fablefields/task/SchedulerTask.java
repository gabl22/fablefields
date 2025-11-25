package me.gabl.fablefields.task;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Consumer;

public abstract class SchedulerTask extends Task implements Consumer<SyncContext> {

    @Setter(AccessLevel.PACKAGE) @Getter
    private Scheduler scheduler;

    protected SchedulerTask() {
        super();
        super.action = this;
    }

    @Override
    public void accept(SyncContext context) {
        execute(context);
    }

    public abstract void execute(SyncContext context);

    public boolean isScheduled() {
        return scheduler.isScheduled(this);
    }
}
