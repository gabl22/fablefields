package me.gabl.fablefields.game.objectives;

import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.game.inventory.item.AnimalProduct;
import me.gabl.fablefields.game.inventory.item.GenericItems;
import me.gabl.fablefields.task.eventbus.Subscribe;
import me.gabl.fablefields.task.eventbus.event.InventoryAddItemEvent;

public class CollectAnimalProductObjective extends Objective {

    final AnimalProduct product;
    private final int coinReward;

    public CollectAnimalProductObjective(ObjectivesList list, int coinReward, int count, AnimalProduct product) {
        super("generic/collect_animal_product", list, false, count);
        this.product = product;
        this.coinReward = coinReward;
    }

    @Override
    protected String fillSpecificPlaceholders(String text) {
        return text.replace("%product%", Asset.LANGUAGE_SERVICE.get("item/" + product.id))
                .replace("%reward%", String.valueOf(coinReward))
                .replace("%coin_noun%", noun("coin", coinReward));
    }

    @Subscribe
    public void onInventoryAddItem(InventoryAddItemEvent event) {
        if (product.equals(event.type())) progress();
    }

    @Override
    public String[] getIconNames() {
        return new String[]{"item/" + product.id, "item/coin"};
    }

    @Override
    public void onComplete() {
        objectivesList.inventory.addItem(GenericItems.COIN, coinReward);
    }
}
