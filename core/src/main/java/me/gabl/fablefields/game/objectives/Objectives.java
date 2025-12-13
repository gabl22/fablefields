package me.gabl.fablefields.game.objectives;

import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.game.inventory.item.GenericItems;
import me.gabl.fablefields.game.inventory.item.Seed;
import me.gabl.fablefields.game.inventory.item.tool.Tools;
import me.gabl.fablefields.map.material.PlantMaterial;
import me.gabl.fablefields.task.eventbus.Subscribe;
import me.gabl.fablefields.task.eventbus.event.*;

public class Objectives {

    private Objectives() {
        throw new UnsupportedOperationException();
    }

    public static Objective getWood(ObjectivesList objectivesList) {
        return new Objective("get_wood", objectivesList, 5) {

            @Override
            public void addIcons() {
                addIcon("item/axe", "item/wood", "item/shovel");
            }

            @Subscribe
            public void onInventoryAddItem(InventoryAddItemEvent event) {
                if (!GenericItems.WOOD.equals(event.type())) return;
                progress();
            }

            @Override
            public void onComplete() {
                objectivesList.inventory.addItem(Tools.SHOVEL);
                objectivesList.add(tillSoil(objectivesList));
            }
        };
    }

    public static Objective tillSoil(ObjectivesList objectivesList) {
        return new Objective("till_soil", objectivesList, 7) {

            @Override
            public void addIcons() {
                super.addIcon("item/shovel", "tile/soil/stage/0", "item/carrot", "item/cauliflower");
            }

            @Subscribe
            public void onTillSoil(TillSoilEvent _event) {
                progress();
            }

            @Subscribe
            public void onUntillSoil(UntillSoilEvent _event) {
                regress();
            }

            @Override
            public void onComplete() {
                objectivesList.inventory.addItem(Seed.CARROT, 5);
                objectivesList.inventory.addItem(Seed.CAULIFLOWER, 5);
                objectivesList.add(HiddenObjectives.awaitWaterNeeded(objectivesList));
            }
        };
    }

    public static Objective waterPlant(ObjectivesList list, PlantMaterial material) {
        return new Objective("water_plant", list, 1) {
            private PlantMaterial wateredMaterial;

            @Override
            public void addIcons() {
                super.addIcon("item/watering_can", "tile/soil/stage/0;tile/plant/"+material.id+"/stage/1", "item/"+material.id);
            }

            @Override
            protected String fillSpecificPlaceholders(String text) {
                return text.replace("%plant%", Asset.LANGUAGE_SERVICE.get("material/" + material.id));
            }

            @Subscribe
            public void onPlantWater(PlantWaterEvent event) {
                wateredMaterial = event.tile.getMaterial();
                complete();
            }

            @Override
            public void onComplete() {
                objectivesList.add(awaitPlantHarvestable(objectivesList, wateredMaterial));
            }
        };
    }

    public static Objective awaitPlantHarvestable(ObjectivesList list, PlantMaterial material) {
        return new Objective("await_plant_harvestable", list) {
            @Override
            public void addIcons() {
                addIcon("icon/hourglass", "icon/plant_grow", "tile/soil/stage/2;tile/plant/"+material.id+"/stage/2");
            }

            @Override
            protected String fillSpecificPlaceholders(String text) {
                return text.replace("%plant%", Asset.LANGUAGE_SERVICE.get("material/" + material.id));
            }

            @Subscribe
            public void onPlantGrow(PlantGrowEvent event) {
                if (event.tile.isFullyGrown()) complete();
            }
        };
    }
}
