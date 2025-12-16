package me.gabl.fablefields.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import me.gabl.fablefields.game.objectives.Objective;
import me.gabl.fablefields.screen.ui.Hud;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectivesHud extends Hud {

    private final Map<Objective, ObjectiveBox> objectiveBoxes = new HashMap<>();

    private final Table root;
    private Table list;

    public ObjectivesHud(SpriteBatch batch) {
        super(batch);
        this.root = new Table();
        root.setFillParent(true);
        root.top().right();
        root.pad(10f);

        stage.addActor(root);

        rebuild(List.of());
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
            if ((hit instanceof ObjectiveBox) && hit.isDescendantOf(list)) {
                return true;
            }
            hit = hit.getParent();
        }
        return false;
    }

    public void rebuild(List<Objective> objectives) {
        if (list != null) list.remove();
        root.removeActor(list);
        this.list = new Table();
        this.list.top().right();
        root.add(list).left().top();
        objectives.forEach(this::add);
    }

    public void add(Objective objective) {
        ObjectiveBox box = new ObjectiveBox(objective, 200f);
        list.add(box).width(220f).padBottom(6f).left().row();
        list.invalidate();
        list.layout();
        objectiveBoxes.put(objective, box);
    }

    public void update(Objective objective) {
        if (!objectiveBoxes.containsKey(objective)) return;

        objectiveBoxes.get(objective).rebuild();
    }
}