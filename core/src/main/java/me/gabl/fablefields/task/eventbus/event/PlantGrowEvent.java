package me.gabl.fablefields.task.eventbus.event;

import me.gabl.fablefields.map.material.PlantTile;

public class PlantGrowEvent extends PlantEvent {

    public PlantGrowEvent(PlantTile tile) {
        super(tile);
    }
}
