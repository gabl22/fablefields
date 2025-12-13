package me.gabl.fablefields.game.objectives;

import me.gabl.fablefields.game.inventory.Inventory;
import me.gabl.fablefields.game.inventory.item.Context;
import me.gabl.fablefields.map.logic.MapChunk;
import me.gabl.fablefields.player.Player;
import me.gabl.fablefields.screen.game.GameScreen;
import me.gabl.fablefields.screen.game.ObjectivesHud;
import me.gabl.fablefields.task.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class Objectives {

    private final List<Objective> currentObjectives;
    final ObjectivesHud hud;
    private final EventBus bus;

    final Player player;
    final GameScreen screen;
    final MapChunk chunk;
    final Inventory inventory;

    public Objectives(GameScreen screen, Player player) {
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
        hud.remove(objective);
        bus.removeListener(objective);
    }

    void add(Objective objective) {
        currentObjectives.add(objective);
        hud.add(objective);
        bus.addListener(objective);
    }

    public void addTutorialObjectives() {
        add(Objective.getWood(this));
    }
}
