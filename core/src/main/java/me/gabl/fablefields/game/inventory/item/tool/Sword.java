package me.gabl.fablefields.game.inventory.item.tool;

import me.gabl.fablefields.game.entity.Animal;
import me.gabl.fablefields.game.inventory.item.UseContext;
import me.gabl.fablefields.player.Range;

public final class Sword extends Tool {

    Sword() {
        super("sword");
    }

    @Override
    public void use(UseContext context) {
        Animal animal = (Animal) context.hitActor;
        if (animal != null) animal.inflictDamage(5.0f);
    }

    @Override
    public boolean isUsable(UseContext context) {
        if (!context.cursorInRange(Range.WEAPON_SHORT)) return false;
        return context.hitActor instanceof Animal;
    }

    @Override
    public String getUseToolTip(UseContext context) {
        Animal animal = context.hitActor instanceof Animal ? (Animal) context.hitActor : null;
        if (animal == null) return null;
        return tooltip("hit").replace("%entity%", language("entity/"+animal.id));
    }
}
