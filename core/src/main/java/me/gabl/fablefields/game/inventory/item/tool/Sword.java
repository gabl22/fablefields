package me.gabl.fablefields.game.inventory.item.tool;

import me.gabl.fablefields.game.entity.Animal;
import me.gabl.fablefields.game.inventory.item.UseContext;
import me.gabl.fablefields.player.Range;

public final class Sword extends Tool {

    Sword() {
        super("sword", 3050);
    }

    @Override
    public void use(UseContext context) {
        Animal animal = (Animal) context.hitActor;
        if (animal != null) animal.inflictDamage(0.0f);
    }

    @Override
    public boolean isUsable(UseContext context) {
        if (!context.cursorInRange(Range.WEAPON_SHORT)) return false;
        return context.hitActor instanceof Animal;
    }
}
