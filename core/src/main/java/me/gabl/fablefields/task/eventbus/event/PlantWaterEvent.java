package me.gabl.fablefields.task.eventbus.event;

import me.gabl.fablefields.map.material.PlantTile;

public class PlantWaterEvent extends PlantEvent {

    public PlantWaterEvent(PlantTile tile) {
        super(tile);
    }
}
