package me.gabl.fablefields.game.objectives;

import me.gabl.fablefields.game.inventory.item.Crop;
import me.gabl.fablefields.game.inventory.item.Seed;
import me.gabl.fablefields.map.material.Materials;
import me.gabl.fablefields.map.material.Plant;
import me.gabl.fablefields.map.material.PlantMaterial;
import me.gabl.fablefields.task.SchedulerTask;
import me.gabl.fablefields.task.SyncContext;
import me.gabl.fablefields.util.MathUtil;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RefillObjectiveTask extends SchedulerTask {

    private final ObjectivesList list;

    public RefillObjectiveTask(ObjectivesList list) {
        this.list = list;
    }

    @Override
    public void execute(SyncContext context) {
        context.rescheduleFixDelay(MathUtil.RANDOM.nextFloat(15, 90));
        List<Objective> currentObjectives = list.getCurrentObjectives();
        if (currentObjectives.size() >= 5) return;

        PlantMaterial targetMaterial = MathUtil.random(Materials.PLANT_MATERIALS);
        Seed seed = Seed.getSeed(targetMaterial);

        if (list.inventory.hasAnyItem(slot -> slot.item != null && seed.equals(slot.item.type))) {
            Crop crop = Crop.getCrop(targetMaterial);
            int cropsRequired = getCropsRequired(targetMaterial);
            int coinReward = (int) Math.floor(Math.pow(cropsRequired, 0.85) / 3);
            Objective objective = new CropRemoveCoinObjective(list, cropsRequired, coinReward, crop);
            list.add(objective);
        } else {
            Set<Seed> availableSeeds = list.inventory.streamNotNull()
                    .filter(slot -> slot.item != null && slot.item.type instanceof Seed)
                    .map(slot -> (Seed) slot.item.type).collect(Collectors.toSet());
            PlantMaterial required = Plant.get(MathUtil.random(availableSeeds)).material;
            int cropsRequired = getCropsRequired(targetMaterial);
            Objective objective = new HarvestNewSeedObjective(list, cropsRequired, required, targetMaterial, 3);
            list.add(objective);
        }
    }

    private int getCropsRequired(PlantMaterial material) {
        Seed seed = Seed.getSeed(material);
        Crop crop = Crop.getCrop(material);
        return Math.max(10, list.inventory.countOf(seed) + list.inventory.countOf(crop));
    }
}
