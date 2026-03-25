package me.gabl.fablefields.game.inventory.item;

import me.gabl.fablefields.game.entity.OreLump;
import me.gabl.fablefields.game.inventory.ItemType;

public class Ore extends ItemType {

    public static final Ore COAL = new Ore("coal");
    public static final Ore IRON = new Ore("iron");
    public static final Ore GOLD = new Ore("gold");
    public static final Ore DIAMOND = new Ore("diamond");
    public static final Ore STONE = new Ore("stone");

    public Ore(String id) {
        super(id);
    }

    @Override
    public String getName() {
        return language("material/ore/" + id);
    }

    public static Ore get(OreLump.Type type) {
        return switch (type.id()) {
            case "coal" -> COAL;
            case "iron" -> IRON;
            case "gold" -> GOLD;
            case "diamond" -> DIAMOND;
            case "stone" -> STONE;
            default -> null;
        };
    }
}
