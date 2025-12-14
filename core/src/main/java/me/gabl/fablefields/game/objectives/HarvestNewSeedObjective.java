package me.gabl.fablefields.game.objectives;

import me.gabl.fablefields.game.inventory.item.Seed;
import me.gabl.fablefields.map.material.PlantMaterial;
import me.gabl.fablefields.task.eventbus.Subscribe;
import me.gabl.fablefields.task.eventbus.event.PlantHarvestEvent;
import org.jetbrains.annotations.NotNull;

public class HarvestNewSeedObjective extends Objective {

    private final PlantMaterial required;
    private final PlantMaterial reward;
    private final int rewardCount;

    public HarvestNewSeedObjective(ObjectivesList objectivesList, int maxProgress, @NotNull PlantMaterial required,
            @NotNull PlantMaterial reward, int rewardCount) {
        super("generic/harvest_new_seed", objectivesList, maxProgress);
        this.required = required;
        this.reward = reward;
        this.rewardCount = rewardCount;
    }

    @Override
    protected String fillSpecificPlaceholders(String text) {
        return text.replace("%plant%", required.toString()).replace("%reward%", reward.toString())
                .replace("%reward_count%", String.valueOf(rewardCount));
    }

    @Subscribe
    public void onPlantHarvest(PlantHarvestEvent event) {
        if (required.materialEquals(event.tile)) progress(event.awardedCrop);
    }

    @Override
    public String[] getIconNames() {
        return new String[]{"icon/plant_grow", "item/"+required.id+"_seed", "tile/soil/stage/3;tile/plant/"+required.id+"/stage/3", "item/hoe", "item/"+reward.id+"_seed"};
    }

    @Override
    public void onComplete() {
        objectivesList.inventory.addItem(Seed.getSeed(reward), rewardCount);
    }
}
