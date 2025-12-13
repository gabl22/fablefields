package me.gabl.fablefields.task.eventbus.event;

import me.gabl.fablefields.game.inventory.Inventory;
import me.gabl.fablefields.game.inventory.ItemType;
import me.gabl.fablefields.game.inventory.Slot;
import org.jetbrains.annotations.NotNull;

public class InventoryAddItemEvent extends InventoryEvent {

    @NotNull
    public final Slot slot;

    public InventoryAddItemEvent(Inventory inventory, @NotNull Slot slot) {
        super(inventory);
        this.slot = slot;
    }

    public ItemType type() {
        return slot.item == null ? null : slot.item.type;
    }
}
