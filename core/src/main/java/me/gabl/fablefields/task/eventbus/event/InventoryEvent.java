package me.gabl.fablefields.task.eventbus.event;

import lombok.AllArgsConstructor;
import me.gabl.fablefields.game.inventory.Inventory;

@AllArgsConstructor
public class InventoryEvent {

    public final Inventory inventory;
}
