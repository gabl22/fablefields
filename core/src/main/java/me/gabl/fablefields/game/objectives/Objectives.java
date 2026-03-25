package me.gabl.fablefields.game.objectives;

import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.game.inventory.item.AnimalProduct;
import me.gabl.fablefields.game.inventory.item.GenericItems;
import me.gabl.fablefields.game.inventory.item.Ore;
import me.gabl.fablefields.game.inventory.item.Seed;
import me.gabl.fablefields.game.inventory.item.tool.Tools;
import me.gabl.fablefields.map.material.Materials;
import me.gabl.fablefields.map.material.Plant;
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
            public String[] getIconNames() {
                return new String[]{"item/axe", "item/wood", "item/shovel"};
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
            public String[] getIconNames() {
                return new String[]{"item/shovel", "tile/soil/stage/0", "item/carrot", "item/cauliflower"};
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
                objectivesList.add(plantSeeds(objectivesList));
            }
        };
    }

    public static Objective plantSeeds(ObjectivesList objectivesList) {
        return new Objective("plant_seeds", objectivesList, 2) {

            private PlantMaterial lastPlanted;

            @Override
            public String[] getIconNames() {
                return new String[]{"item/carrot_seed", "item/cauliflower_seed", "tile/soil/stage/0"};
            }

            @Override
            protected String fillSpecificPlaceholders(String text) {
                return text.replace("%noun%", noun("seed", remaining()));
            }

            @Subscribe
            public void onItemUse(ItemUseEvent event) {
                if (event.context.item.type instanceof Seed seed) {
                    lastPlanted = Plant.get(seed).material;
                    progress();
                }
            }

            @Override
            public void onComplete() {
                objectivesList.add(awaitWaterNeeded(objectivesList, lastPlanted));
            }
        };
    }

    public static Objective awaitWaterNeeded(ObjectivesList objectivesList, PlantMaterial planted) {
        return new Objective("await_water_needed", objectivesList) {

            private PlantMaterial material;

            @Override
            public String[] getIconNames() {
                return new String[]{"icon/hourglass", "tile/soil/stage/0;tile/plant/" + planted.id + "/stage/0",
                        "item/watering_can"};
            }

            @Override
            protected String fillSpecificPlaceholders(String text) {
                return text.replace("%plant%", Asset.LANGUAGE_SERVICE.get("material/" + planted.id));
            }

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
                objectivesList.add(waterPlant(objectivesList, material));
            }
        };
    }

    public static Objective waterPlant(ObjectivesList list, PlantMaterial material) {
        return new Objective("water_plant", list, 1) {
            private PlantMaterial wateredMaterial;

            @Override
            public String[] getIconNames() {
                return new String[]{"item/watering_can", "tile/soil/stage/0;tile/plant/" + material.id + "/stage/1",
                        "item" + "/" + material.id};
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
            private PlantMaterial grownMaterial;

            @Override
            public String[] getIconNames() {
                return new String[]{"icon/hourglass", "icon/plant_grow",
                        "tile/soil/stage/0;tile/plant/" + material.id + "/stage/1"};
            }

            @Override
            protected String fillSpecificPlaceholders(String text) {
                return text.replace("%plant%", Asset.LANGUAGE_SERVICE.get("material/" + material.id));
            }

            @Subscribe
            public void onPlantGrow(PlantGrowEvent event) {
                if (event.tile.isFullyGrown()) {
                    grownMaterial = event.tile.getMaterial();
                    complete();
                }
            }

            @Override
            public void onComplete() {
                objectivesList.add(harvestPlant(objectivesList, grownMaterial));
                objectivesList.player.inventory.addItem(Tools.HOE, 1);
            }
        };
    }

    public static Objective harvestPlant(ObjectivesList list, PlantMaterial material) {
        return new Objective("harvest_plant", list) {
            @Override
            public String[] getIconNames() {
                return new String[]{"item/hoe", "item/" + material.id, "item/" + material.id + "_seed"};
            }

            @Override
            protected String fillSpecificPlaceholders(String text) {
                return text.replace("%plant%", Asset.LANGUAGE_SERVICE.get("material/" + material.id));
            }

            @Subscribe
            private void onPlantHarvest(PlantHarvestEvent _event) {
                complete();
            }

            @Override
            public void onComplete() {
                objectivesList.inventory.addItem(Tools.PICKAXE);
                objectivesList.add(mineOre(objectivesList));
            }
        };
    }

    public static Objective mineOre(ObjectivesList objectivesList) {
        return new Objective("mine_ore", objectivesList, 3) {

            @Override
            public String[] getIconNames() {
                return new String[]{"item/pickaxe", "item/coal", "item/iron", "item/gold"};
            }

            @Override
            protected String fillSpecificPlaceholders(String text) {
                return text.replace("%noun%", noun("ore", remaining()));
            }

            @Subscribe
            public void onInventoryAddItem(InventoryAddItemEvent event) {
                if (event.type() instanceof Ore) progress();
            }

            @Override
            public void onComplete() {
                objectivesList.add(collectEggs(objectivesList));
            }
        };
    }

    public static Objective collectEggs(ObjectivesList objectivesList) {
        return new Objective("collect_eggs", objectivesList, 3) {

            @Override
            public String[] getIconNames() {
                return new String[]{"item/egg"};
            }

            @Override
            protected String fillSpecificPlaceholders(String text) {
                return text.replace("%noun%", noun("egg", remaining()));
            }

            @Subscribe
            public void onInventoryAddItem(InventoryAddItemEvent event) {
                if (AnimalProduct.EGG.equals(event.type())) progress();
            }

            @Override
            public void onComplete() {
                objectivesList.add(collectMilk(objectivesList));
            }
        };
    }

    public static Objective collectMilk(ObjectivesList objectivesList) {
        return new Objective("collect_milk", objectivesList, 2) {

            @Override
            public String[] getIconNames() {
                return new String[]{"item/milk_bucket"};
            }

            @Override
            protected String fillSpecificPlaceholders(String text) {
                return text.replace("%noun%", noun("cow", remaining()));
            }

            @Subscribe
            public void onInventoryAddItem(InventoryAddItemEvent event) {
                if (AnimalProduct.MILK_BUCKET.equals(event.type())) progress();
            }

            @Override
            public void onComplete() {
                objectivesList.add(new HarvestNewSeedObjective(objectivesList, 10, Materials.CARROT, Plant.SUNFLOWER,
                        3));
                objectivesList.screen.syncScheduler.schedule(new RefillObjectiveTask(objectivesList), 15);
            }
        };
    }
}
