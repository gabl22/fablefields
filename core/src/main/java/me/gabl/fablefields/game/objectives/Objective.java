package me.gabl.fablefields.game.objectives;

import me.gabl.fablefields.game.inventory.ItemType;
import me.gabl.fablefields.game.inventory.item.GenericItems;
import me.gabl.fablefields.game.inventory.item.tool.Tools;
import me.gabl.fablefields.task.eventbus.Subscribe;
import me.gabl.fablefields.task.eventbus.event.InventoryAddItemEvent;

public class Objective {

    public final String id;
    public int progress;
    public int maxProgress;

    private final Objectives objectives;


    public Objective(String id, Objectives objectives) {
        this.id = id;
        this.objectives = objectives;
    }

    public Objective(String id, Objectives objectives, int maxProgress) {
        this(id, objectives);
        this.maxProgress = maxProgress;
    }

    public void checkCompleted() {
        if (progress < maxProgress) return;
        objectives.markCompleted(this);
        onComplete();
    }

    public void update() {
        objectives.hud.update(this);
    }

    public void onComplete() {}

    public static Objective getWood(Objectives objectives) {
        return new Objective("get_wood", objectives, 5) {
            @Subscribe
            public void onInventoryAddItem(InventoryAddItemEvent event) {
                if (!GenericItems.WOOD.equals(event.type())) return;
                progress++;
                update();
                checkCompleted();
            }

            @Override
            public void onComplete() {
                objectives.inventory.addItem(Tools.SHOVEL);
            }
        };
    }
}
