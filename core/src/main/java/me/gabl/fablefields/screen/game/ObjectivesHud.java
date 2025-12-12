package me.gabl.fablefields.screen.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import me.gabl.fablefields.screen.ui.Hud;

public class ObjectivesHud extends Hud {

    private final TaskBox taskBox;

    private Table root;
    private Table list;

    public ObjectivesHud(SpriteBatch batch) {
        super(batch);
        this.taskBox = new TaskBox("Aquatitios", "Dreh eine Runde im Wasser!", 200f);
    }

    @Override
    public void show() {
        root = new Table();
        root.setFillParent(true);
        root.top().right();
        root.pad(10f);

        list = new Table();
        list.top().right();

        list.add(taskBox).width(220f).left().top().row();
        list.add(new TaskBox("Title", "Body", 200f)).width(220f).padTop(6f).left().row();

        root.add(list).left().top();

        stage.addActor(root);
    }

    public Stage getStage() {
        return stage;
    }

    @Override
    public boolean isHovering() {
        return false; //todo
    }
}