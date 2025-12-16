package me.gabl.fablefields.map.material;

import me.gabl.fablefields.map.logic.MapChunk;
import me.gabl.fablefields.task.ActSynchronousScheduler;
import me.gabl.fablefields.task.SchedulerTask;
import me.gabl.fablefields.task.SyncContext;
import me.gabl.fablefields.task.eventbus.EventBus;
import me.gabl.fablefields.task.eventbus.event.PlantGrowEvent;

public class PlantGrowTask extends SchedulerTask {

    private final PlantTile tile;
    private final MapChunk chunk;
    private final EventBus eventBus;

    PlantGrowTask(PlantTile tile, MapChunk chunk, EventBus bus) {
        this.tile = tile;
        this.chunk = chunk;
        eventBus = bus;
    }

    @Override
    public void execute(SyncContext context) {
        tile.grow();
        eventBus.fire(new PlantGrowEvent(tile));
        chunk.getRenderComponent().updateCells(tile.address.x(), tile.address.y());
        if (tile.getGrowthStage() >= tile.growthStages.length) {
            return;
        }

        if (tile.getGrowthStage() == 1) {
            return;
        }

        context.rescheduleFixRate(tile.growthStages[tile.getGrowthStage()]);
    }

    public void reschedule() {
        if (!isScheduled()) {
            getScheduler().schedule(this, tile.growthStages[tile.getGrowthStage()]);
        }
    }

    public void schedule(ActSynchronousScheduler scheduler) {
        scheduler.schedule(this, tile.growthStages[tile.getGrowthStage()]);
    }
}
