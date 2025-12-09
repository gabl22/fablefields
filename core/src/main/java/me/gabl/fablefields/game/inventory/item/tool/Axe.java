package me.gabl.fablefields.game.inventory.item.tool;

import me.gabl.fablefields.game.entity.Tree;
import me.gabl.fablefields.game.inventory.item.GenericItems;
import me.gabl.fablefields.game.inventory.item.UseContext;
import me.gabl.fablefields.player.Action;
import me.gabl.fablefields.player.Range;
import me.gabl.fablefields.player.RunningAction;

public class Axe extends Tool {

    public Axe() {
        super("axe");
    }

    @Override
    public void use(UseContext context) {
        RunningAction cutTree = RunningAction.get(Action.AXE).copyAnimation();
        cutTree.setOnFinished(() -> {
            context.hitActor.remove();
            context.player.inventory.addItem(GenericItems.WOOD, 1);
        });
        context.player.replaceAction(cutTree);
    }


    @Override
    public boolean isUsable(UseContext context) {
        if (context.hitActor == null) return false;
        return context.hitActor instanceof Tree tree && context.entityInRange(Range.TOOL, tree);
    }

    @Override
    public String getUseToolTip() {
        return tooltip("chop_tree");
    }
}
