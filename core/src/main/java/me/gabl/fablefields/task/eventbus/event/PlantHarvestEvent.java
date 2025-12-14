package me.gabl.fablefields.task.eventbus.event;

import me.gabl.fablefields.map.material.PlantTile;

public class PlantHarvestEvent extends PlantEvent {

    private final int awardedSeeds;
    private final int awardedPlant;

    public PlantHarvestEvent(PlantTile tile, int awardedSeeds, int awardedPlant) {
        super(tile);
        this.awardedSeeds = awardedSeeds;
        this.awardedPlant = awardedPlant;
    }
}
