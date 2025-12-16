package me.gabl.fablefields.task.eventbus.event;

import me.gabl.fablefields.game.inventory.Inventory;
import me.gabl.fablefields.game.inventory.Slot;

public class InventorySwapSlotEvent extends InventoryEvent {

    public final int slotId1;
    public final int slotId2;

    public InventorySwapSlotEvent(Inventory inventory, int slotId1, int slotId2) {
        super(inventory);
        this.slotId1 = slotId1;
        this.slotId2 = slotId2;
    }

    public Slot getSlot1() { return inventory.getSlot(slotId1); }
    public Slot getSlot2() { return inventory.getSlot(slotId2); }
}
