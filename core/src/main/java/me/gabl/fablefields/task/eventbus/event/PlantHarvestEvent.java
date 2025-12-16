package me.gabl.fablefields.task.eventbus.event;

import me.gabl.fablefields.map.material.PlantTile;

public class PlantHarvestEvent extends PlantEvent {

    public final int awardedSeeds;
    public final int awardedCrop;

    public PlantHarvestEvent(PlantTile tile, int awardedSeeds, int awardedCrop) {
        super(tile);
        this.awardedSeeds = awardedSeeds;
        this.awardedCrop = awardedCrop;
    }
}
