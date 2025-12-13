package me.gabl.fablefields.task.eventbus.event;

import me.gabl.fablefields.game.inventory.item.UseContext;

public class ItemUseEvent implements Event {

    public final UseContext context;

    public ItemUseEvent(UseContext context) {
        this.context = context;
    }
}
