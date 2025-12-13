package me.gabl.fablefields.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.game.objectives.Objective;
import me.gabl.fablefields.screen.ui.Hud;

import java.util.HashMap;
import java.util.Map;

public class ObjectivesHud extends Hud {

    private final Map<Objective, TaskBox> objectiveBoxes = new HashMap<>();

    private final Table list;

    public ObjectivesHud(SpriteBatch batch) {
        super(batch);
        list = new Table();
        list.top().right();
//        this.taskBox = new TaskBox("Aquatitios", "Dreh eine Runde im Wasser!", 200f);
    }

    @Override
    public void show() {
        Table root = new Table();
        root.setFillParent(true);
        root.top().right();
        root.pad(10f);

//        list.add(taskBox).width(220f).left().top().row();
//        list.add(new TaskBox("Title", "Body", 200f)).width(220f).padTop(6f).left().row();

        root.add(list).left().top();

        stage.addActor(root);
    }

    public Stage getStage() {
        return stage;
    }

    @Override
    public boolean isHovering() {
        if (list == null) {
            return false;
        }

        Vector2 stageCoords = stage.screenToStageCoordinates(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
        Actor hit = stage.hit(stageCoords.x, stageCoords.y, true);
        while (hit != null) {
            if ((hit instanceof TaskBox) && hit.isDescendantOf(list)) {
                return true;
            }
            hit = hit.getParent();
        }
        return false;
    }

    public void add(Objective objective) {
        TaskBox box = new TaskBox(title(objective), body(objective), 200f);
        list.add(box).width(220f).padTop(6f).left().row();
        objectiveBoxes.put(objective, box);
    }

    private String title(Objective objective) {
        return fillPlaceholders(Asset.LANGUAGE_SERVICE.get("objective/" + objective.id + "/title"), objective);
    }

    private String body(Objective objective) {
        return fillPlaceholders(Asset.LANGUAGE_SERVICE.get("objective/" + objective.id + "/body"), objective);
    }

    private String fillPlaceholders(String text, Objective objective) {
        return text.replace("%progress%", objective.progress + "/" + objective.maxProgress)
                .replace("%remaining%", String.valueOf(objective.maxProgress - objective.progress));
    }

    public void update(Objective objective) {
        TaskBox box = objectiveBoxes.get(objective);
        box.setTitle(title(objective));
        box.setBody(body(objective));
    }

    public void remove(Objective objective) {
        objectiveBoxes.remove(objective).remove();
    }
}