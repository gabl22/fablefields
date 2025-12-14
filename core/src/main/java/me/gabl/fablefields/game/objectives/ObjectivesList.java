package me.gabl.fablefields.game.objectives;

import lombok.Getter;
import me.gabl.fablefields.game.inventory.Inventory;
import me.gabl.fablefields.game.inventory.item.Crop;
import me.gabl.fablefields.game.inventory.item.tool.Tools;
import me.gabl.fablefields.map.logic.MapChunk;
import me.gabl.fablefields.player.Player;
import me.gabl.fablefields.screen.game.GameScreen;
import me.gabl.fablefields.screen.game.ObjectivesHud;
import me.gabl.fablefields.task.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class ObjectivesList {

    final ObjectivesHud hud;
    final Player player;
    final GameScreen screen;
    final MapChunk chunk;
    final Inventory inventory;
    @Getter
    private final List<Objective> currentObjectives;
    private final EventBus bus;

    public ObjectivesList(GameScreen screen, Player player) {
        currentObjectives = new ArrayList<>();
        this.screen = screen;
        this.hud = screen.objectivesHud;
        this.bus = screen.eventBus;
        this.player = player;
        this.chunk = screen.getChunk();
        this.inventory = player.inventory;
    }

    void markCompleted(Objective objective) {
        currentObjectives.remove(objective);
        if (!objective.hidden) rebuildHud();
        bus.removeListener(objective);
    }

    public void addTutorialObjectives() {
        add(Objectives.getWood(this));
        //todo remove
        player.inventory.addItem(Tools.SHOVEL);
        add(Objectives.tillSoil(this));
        add(new CropRemoveCoinObjective(this, 2, 10, Crop.CAULIFLOWER));
    }

    void remove(Objective objective) {
        currentObjectives.remove(objective);
        rebuildHud();
        bus.removeListener(objective);
    }

    void add(Objective objective) {
        currentObjectives.add(objective);
        if (!objective.hidden) hud.add(objective);
        bus.addListener(objective);
    }

    private void rebuildHud() {
        hud.rebuild(currentObjectives.stream().filter(o -> !o.hidden).toList());
    }
}
