package me.gabl.fablefields.game.objectives;

import me.gabl.fablefields.asset.Asset;

public class Objective {

    public final String id;
    protected final ObjectivesList objectivesList;
    private int progress;
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

    public Objective(String id, ObjectivesList objectivesList) {
        this(id, objectivesList, false);
    }

    public void setProgress(int progress) {
        this.progress = progress;
        update();
        checkCompleted();
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
        progress(1);
    }

    public void progress(int progression) {
        setProgress(progress + progression);
    }

    public void update() {
        if (hidden) return;
        objectivesList.hud.update(this);
        getIconNames();
    }

    public String[] getIconNames() {

        return null;
    }

    public void regress() {
        progress--;
        update();
    }

    public String title() {
        return fillPlaceholders(Asset.LANGUAGE_SERVICE.get("objective/" + id + "/title"));
    }

    public String body() {
        return fillPlaceholders(Asset.LANGUAGE_SERVICE.get("objective/" + id + "/body"));
    }

    private String fillPlaceholders(String text) {
        return fillSpecificPlaceholders(text.replace("%progress%", progress + "/" + maxProgress)
                .replace("%remaining%", String.valueOf(maxProgress - progress))
                .replace("%maxprogress%", String.valueOf(maxProgress)));
    }

    protected String fillSpecificPlaceholders(String text) {
        return text;
    }
}
