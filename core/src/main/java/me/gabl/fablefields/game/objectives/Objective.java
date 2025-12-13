package me.gabl.fablefields.game.objectives;

import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.game.inventory.item.GenericItems;
import me.gabl.fablefields.game.inventory.item.tool.Tools;
import me.gabl.fablefields.task.eventbus.Subscribe;
import me.gabl.fablefields.task.eventbus.event.InventoryAddItemEvent;

public class Objective {

    public final String id;
    private final Objectives objectives;
    public int progress;
    public int maxProgress;


    public Objective(String id, Objectives objectives, int maxProgress) {
        this(id, objectives);
        this.maxProgress = maxProgress;
    }

    public Objective(String id, Objectives objectives) {
        this.id = id;
        this.objectives = objectives;
    }

    public void addIcons() {

    }

    public void update() {
        objectives.hud.update(this);
        objectives.hud.clearIcons(this);
        addIcons();
    }

    public void checkCompleted() {
        if (progress < maxProgress) return;
        objectives.markCompleted(this);
        onComplete();
    }

    public void onComplete() {
    }

    public void addIcon(String... names) {
        for (String name : names) {
            objectives.hud.addIcon(this, Asset.REGISTRY.getDrawable(name), 32);
        }
    }


    public static Objective getWood(Objectives objectives) {
        return new Objective("get_wood", objectives, 5) {

            @Override
            public void addIcons() {
                addIcon("item/axe", "item/wood", "item/shovel");
            }

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
