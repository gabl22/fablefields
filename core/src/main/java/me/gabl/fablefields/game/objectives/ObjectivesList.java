package me.gabl.fablefields.game.objectives;

import lombok.Getter;
import me.gabl.fablefields.game.inventory.Inventory;
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

    private void rebuildHud() {
        hud.rebuild(currentObjectives.stream().filter(o -> !o.hidden).toList());
    }

    public void addTutorialObjectives() {
        add(Objectives.getWood(this));
    }

    void add(Objective objective) {
        currentObjectives.add(objective);
        if (!objective.hidden) hud.add(objective);
        bus.addListener(objective);
    }
}
