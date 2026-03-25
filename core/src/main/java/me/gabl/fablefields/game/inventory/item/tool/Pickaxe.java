package me.gabl.fablefields.game.inventory.item.tool;

import me.gabl.fablefields.game.entity.OreLump;
import me.gabl.fablefields.game.entity.Tree;
import me.gabl.fablefields.game.inventory.item.GenericItems;
import me.gabl.fablefields.game.inventory.item.Ore;
import me.gabl.fablefields.game.inventory.item.UseContext;
import me.gabl.fablefields.player.Action;
import me.gabl.fablefields.player.Range;
import me.gabl.fablefields.player.RunningAction;

public class Pickaxe extends Tool {

    public Pickaxe() {
        super("pickaxe");
    }

    @Override
    public void use(UseContext context) {
        RunningAction cutTree = RunningAction.get(Action.MINING).copyAnimation();
        cutTree.setOnFinished(() -> {
            OreLump oreLump = (OreLump) context.hitActor;
            oreLump.reduce();
            context.player.inventory.addItem(Ore.get(oreLump.getType()), 1);
        });
        context.player.replaceAction(cutTree);
    }


    @Override
    public boolean isUsable(UseContext context) {
        if (context.hitActor == null) return false;
        return context.hitActor instanceof OreLump lump && context.entityInRange(Range.TOOL, lump);
    }

    @Override
    public String getUseToolTip(UseContext context) {
        OreLump lump = (OreLump) context.hitActor;

        return tooltip("mine").replace("%ore%",
                language("material/ore/"+lump.getType().id())
        );
    }
}
