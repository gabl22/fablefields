package me.gabl.fablefields.game.objectives;

import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.game.inventory.item.Crop;
import me.gabl.fablefields.game.inventory.item.GenericItems;
import me.gabl.fablefields.task.eventbus.Subscribe;
import me.gabl.fablefields.task.eventbus.event.InventoryEvent;

public class CropRemoveCoinObjective extends Objective {

    private final Crop crop;
    private final int coinReward;

    public CropRemoveCoinObjective(ObjectivesList list, int coinReward, int cropCount, Crop crop) {
        super("generic/crop_remove_coin", list, false, cropCount);
        this.coinReward = coinReward;
        this.crop = crop;
    }

    @Override
    protected String fillSpecificPlaceholders(String text) {
        return text.replace("%reward%", String.valueOf(coinReward)).replace("%plant%", Asset.LANGUAGE_SERVICE.get("material/" + crop.id));
    }

    @Subscribe
    public void onInventoryChange(InventoryEvent _event) {
        setProgress(objectivesList.inventory.countOf(crop));
        checkCompleted();
    }

    @Override
    public void onComplete() {
        if (objectivesList.inventory.removeItem(crop, maxProgress)) {
            objectivesList.inventory.addItem(GenericItems.COIN, coinReward);
        }
    }

    @Override
    public String[] getIconNames() {
        return new String[]{"item/" + crop.id + "_seed", "item/hoe", "item/" + crop.id, "item/coin"};
    }
}
