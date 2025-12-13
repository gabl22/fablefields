package me.gabl.fablefields.game.objectives;

import me.gabl.fablefields.game.inventory.item.GenericItems;
import me.gabl.fablefields.game.inventory.item.Seed;
import me.gabl.fablefields.game.inventory.item.tool.Tools;
import me.gabl.fablefields.task.eventbus.Subscribe;
import me.gabl.fablefields.task.eventbus.event.InventoryAddItemEvent;
import me.gabl.fablefields.task.eventbus.event.TillSoilEvent;
import me.gabl.fablefields.task.eventbus.event.UntillSoilEvent;

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
            }
        };
    }
}
