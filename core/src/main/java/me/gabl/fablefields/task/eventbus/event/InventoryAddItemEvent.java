package me.gabl.fablefields.task.eventbus.event;

import me.gabl.fablefields.game.inventory.Inventory;
import me.gabl.fablefields.game.inventory.Slot;

public class InventoryAddItemEvent extends InventoryEvent {

    public final Slot slot;

    public InventoryAddItemEvent(Inventory inventory, Slot slot) {
        super(inventory);
        this.slot = slot;
    }
}
