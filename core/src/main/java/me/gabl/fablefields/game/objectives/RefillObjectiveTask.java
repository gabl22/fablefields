package me.gabl.fablefields.game.objectives;

import me.gabl.fablefields.game.inventory.item.AnimalProduct;
import me.gabl.fablefields.game.inventory.item.Seed;
import me.gabl.fablefields.map.material.Plant;
import me.gabl.fablefields.task.SchedulerTask;
import me.gabl.fablefields.task.SyncContext;
import me.gabl.fablefields.util.MathUtil;

import java.util.HashSet;
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
        Set<Plant> availablePlants = new HashSet<>();
        availablePlants.addAll(list.inventory.streamNotNull()
                .filter(slot -> slot.item != null && slot.item.type instanceof Seed)
                .map(slot -> Plant.get((Seed) slot.item.type)).collect(Collectors.toSet()));
        for (Objective objective : currentObjectives) {
            if (objective instanceof HarvestNewSeedObjective newSeedObjective) {
                availablePlants.add(newSeedObjective.reward);
                availablePlants.remove(Plant.get(newSeedObjective.required));
            }
        }

        if (currentObjectives.size() > 2) return;

        boolean hasAnimalProductObjective = currentObjectives.stream()
                .anyMatch(o -> o instanceof CollectAnimalProductObjective);

        // 30% chance to generate an animal product objective if no one is active rn active
        if (!hasAnimalProductObjective && MathUtil.RANDOM.nextFloat() < 0.3f) {
            AnimalProduct product = MathUtil.RANDOM.nextBoolean() ? AnimalProduct.EGG : AnimalProduct.MILK_BUCKET;
            int count = product == AnimalProduct.EGG ? MathUtil.RANDOM.nextInt(3, 6) : MathUtil.RANDOM.nextInt(2, 4);
            count += list.inventory.countOf(product);
            int coinReward = (int) Math.floor(Math.pow(count, 0.5));
            list.add(new CollectAnimalProductObjective(list, coinReward, count, product));
            return;
        }

        Plant plant = MathUtil.random(Plant.VALUES);

        if (availablePlants.contains(plant)) {
            int cropsRequired = getCropsRequired(plant);
            int coinReward = (int) Math.floor(Math.pow(cropsRequired, 0.85) / 3);
            Objective objective = new CropRemoveCoinObjective(list, coinReward, cropsRequired, plant.crop);
            list.add(objective);
        } else {
            Plant required = MathUtil.random(availablePlants);
            int cropsRequired = getCropsRequired(required);
            Objective objective = new HarvestNewSeedObjective(list, cropsRequired, required.material, plant, 3);
            list.add(objective);
        }
    }

    private int getCropsRequired(Plant plant) {
        return Math.max(10, list.inventory.countOf(plant.seed) / 2 + list.inventory.countOf(plant.crop) + 5);
    }
}
