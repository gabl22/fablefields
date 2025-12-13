package me.gabl.fablefields.game.objectives;

import me.gabl.fablefields.game.inventory.item.GenericItems;
import me.gabl.fablefields.game.inventory.item.Seed;
import me.gabl.fablefields.game.inventory.item.tool.Tools;
import me.gabl.fablefields.task.eventbus.Subscribe;
import me.gabl.fablefields.task.eventbus.event.InventoryAddItemEvent;
import me.gabl.fablefields.task.eventbus.event.TillSoilEvent;
import me.gabl.fablefields.task.eventbus.event.UntillSoilEvent;

public class HiddenObjectives {

    private HiddenObjectives() {
        throw new UnsupportedOperationException();
    }

    public static Objective awaitWaterNeeded(ObjectivesList objectivesList) {
        return new Objective("await_water_needed", objectivesList, true) {

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
}
