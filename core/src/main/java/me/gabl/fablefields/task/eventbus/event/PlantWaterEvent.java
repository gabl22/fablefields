package me.gabl.fablefields.task.eventbus.event;

import lombok.AllArgsConstructor;
import me.gabl.fablefields.map.material.PlantTile;

@AllArgsConstructor
public class PlantWaterEvent implements Event {

    public final PlantTile tile;

}
