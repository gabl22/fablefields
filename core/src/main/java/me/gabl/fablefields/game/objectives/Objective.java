package me.gabl.fablefields.game.objectives;

import me.gabl.fablefields.asset.Asset;

public class Objective {

    public final String id;
    private final ObjectivesList objectivesList;
    public int progress;
    public final int maxProgress;

    final boolean hidden;

    public Objective(String id, ObjectivesList list, boolean hidden, int maxProgress) {
        this.id = id;
        this.objectivesList = list;
        this.hidden = hidden;
        this.maxProgress = maxProgress;
    }

    public Objective(String id, ObjectivesList objectivesList, boolean hidden) {
        this(id, objectivesList, hidden, 1);
    }

    public Objective(String id, ObjectivesList objectivesList, int maxProgress) {
        this(id, objectivesList, false, maxProgress);
    }

    public void complete() {
        progress = maxProgress;
        checkCompleted();
    }

    public void checkCompleted() {
        if (progress < maxProgress) return;
        objectivesList.markCompleted(this);
        onComplete();
    }

    public void onComplete() {
    }

    public void progress() {
        progress++;
        update();
        checkCompleted();
    }

    public void update() {
        if (hidden) return;
        objectivesList.hud.update(this);
        objectivesList.hud.clearIcons(this);
        addIcons();
    }

    public void addIcons() {

    }

    public void regress() {
        progress--;
        update();
    }

    public final void addIcon(String... names) {
        for (String name : names) {
            objectivesList.hud.addIcon(this, Asset.REGISTRY.getDrawable(name), 32);
        }
    }
}
