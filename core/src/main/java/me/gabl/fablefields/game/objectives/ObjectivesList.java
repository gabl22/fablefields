package me.gabl.fablefields.game.objectives;

import me.gabl.fablefields.game.inventory.Inventory;
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
        if (!objective.hidden) hud.remove(objective);
        bus.removeListener(objective);
    }

    public void addTutorialObjectives() {
        add(Objectives.getWood(this));
        //todo remove
        player.inventory.addItem(Tools.SHOVEL);
        add(Objectives.tillSoil(this));
    }

    void add(Objective objective) {
        currentObjectives.add(objective);
        if (!objective.hidden) hud.add(objective);
        objective.addIcons();
        bus.addListener(objective);
    }
}
