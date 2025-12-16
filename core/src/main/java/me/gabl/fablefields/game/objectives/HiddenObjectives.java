package me.gabl.fablefields.game.objectives;

import me.gabl.fablefields.game.inventory.item.tool.Tools;
import me.gabl.fablefields.map.material.PlantMaterial;
import me.gabl.fablefields.task.eventbus.Subscribe;
import me.gabl.fablefields.task.eventbus.event.PlantGrowEvent;

public class HiddenObjectives {

    private HiddenObjectives() {
        throw new UnsupportedOperationException();
    }

    public static Objective awaitWaterNeeded(ObjectivesList list) {
        return new Objective("await_water_needed", list, true) {

            private PlantMaterial material;

            @Subscribe
            public void onPlantGrow(PlantGrowEvent event) {
                if (event.tile.needsWater()) {
                    material = event.tile.getMaterial();
                    complete();
                }
            }

            @Override
            public void onComplete() {
                objectivesList.inventory.addItem(Tools.WATERING_CAN);
                objectivesList.add(Objectives.waterPlant(objectivesList, material));
            }
        };
    }
}
