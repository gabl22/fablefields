package me.gabl.fablefields.game.objectives;

import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.map.material.Plant;
import me.gabl.fablefields.map.material.PlantMaterial;
import me.gabl.fablefields.task.eventbus.Subscribe;
import me.gabl.fablefields.task.eventbus.event.PlantHarvestEvent;
import org.jetbrains.annotations.NotNull;

public class HarvestNewSeedObjective extends Objective {

    final Plant reward;
    private final PlantMaterial required;
    private final int rewardCount;

    public HarvestNewSeedObjective(ObjectivesList objectivesList, int maxProgress, @NotNull PlantMaterial required,
            @NotNull Plant reward, int rewardCount) {
        super("generic/harvest_new_seed", objectivesList, maxProgress);
        this.required = required;
        this.reward = reward;
        this.rewardCount = rewardCount;
    }

    @Override
    protected String fillSpecificPlaceholders(String text) {
        return text.replace("%plant%", required.toString())
                .replace("%reward%", Asset.LANGUAGE_SERVICE.get("material/" + reward.id))
                .replace("%reward_count%", String.valueOf(rewardCount));
    }

    @Subscribe
    public void onPlantHarvest(PlantHarvestEvent event) {
        if (required.materialEquals(event.tile)) progress(event.awardedCrop);
    }

    @Override
    public String[] getIconNames() {
        return new String[]{"icon/plant_grow", "item/" + required.id + "_seed", "item/" + required.id, "item/hoe",
                "item/" + reward.id + "_seed"};
    }

    @Override
    public void onComplete() {
        objectivesList.inventory.addItem(reward.seed, rewardCount);
    }
}
