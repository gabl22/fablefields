package me.gabl.fablefields.map.material;

import me.gabl.fablefields.map.logic.MapChunk;
import me.gabl.fablefields.task.ActSynchronousScheduler;
import me.gabl.fablefields.task.SyncContext;
import me.gabl.fablefields.task.SchedulerTask;

public class PlantGrowTask extends SchedulerTask {

    private final PlantTile tile;
    private final MapChunk chunk;

    PlantGrowTask(PlantTile tile, MapChunk chunk) {
        this.tile = tile;
        this.chunk = chunk;
    }

    @Override
    public void execute(SyncContext context) {
        tile.grow();
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
