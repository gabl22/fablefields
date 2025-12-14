package me.gabl.fablefields.screen.game;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.game.objectives.Objective;
import me.gabl.fablefields.screen.ui.UiSkin;
import me.gabl.fablefields.util.CompositeDrawable;

import java.util.Arrays;

public class ObjectiveBox extends Table {


    private final Label titleLabel;
    private final Label bodyLabel;
    private final Table iconsTable;

    private final Objective objective;
    private final float fixedWidth;

    public void rebuild() {
//        clearChildren();
        setTitle(objective.title());
        setBody(objective.body());
        updateIcons();

        setWidth(fixedWidth);
        invalidateHierarchy();
        pack();
    }

    public ObjectiveBox(Objective objective, float fixedWidth) {
        super(UiSkin.skin());
        this.objective = objective;
        this.fixedWidth = fixedWidth;
        Skin skin = UiSkin.skin();
        setBackground(skin.getDrawable("box-light-2"));
        titleLabel = new Label(objective.title(), skin, "title");
        titleLabel.setFontScale(1.15f); //fake bold
        titleLabel.setColor(0f, 0f, 0f, 1f);

        bodyLabel = new Label(objective.body(), skin, "default-black");
        bodyLabel.setWrap(true);
        iconsTable = new Table();
        iconsTable.left();
        defaults().left().top().expandX().fillX().padLeft(6f).padRight(6f);
        add(titleLabel);
        row();
        add(bodyLabel);
        row();
        add(iconsTable);

        rebuild();
    }

    public void setTitle(String title) {
        titleLabel.setText(title);
        repack();
    }

    private void repack() {
        invalidateHierarchy();
        pack();
    }

    public void setBody(String body) {
        bodyLabel.setText(body);
        repack();
    }

    public void clearIcons() {
        iconsTable.clearChildren();
        repack();
    }

    public void updateIcons() {
        clearIcons();
        for (String name : objective.getIconNames()) {
            Drawable drawable;
            if (name.contains(";")) {
                drawable = new CompositeDrawable(Arrays.stream(name.split(";")).map(Asset.REGISTRY::getDrawable).toArray(Drawable[]::new));
            } else {
                drawable = Asset.REGISTRY.getDrawable(name);
            }
            addIcon(drawable, 32);
        }
    }

    private void addIcon(Drawable drawable, float size) {
        Image icon = new Image(drawable);
        iconsTable.add(icon).size(size).padRight(4f);
        repack();
    }
}