package me.gabl.fablefields.task.eventbus.event;

import lombok.RequiredArgsConstructor;
import me.gabl.fablefields.map.material.PlantTile;

@RequiredArgsConstructor
public class PlantEvent implements Event {

    public final PlantTile tile;

}
