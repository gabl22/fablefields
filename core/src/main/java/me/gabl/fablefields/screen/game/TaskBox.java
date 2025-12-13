package me.gabl.fablefields.screen.game;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import me.gabl.fablefields.screen.ui.UiSkin;

public class TaskBox extends Table {


    private final Label titleLabel;
    private final Label bodyLabel;
    private final Table iconsTable;

    public TaskBox(String title, String body, float fixedWidth) {
        super(UiSkin.skin());
        Skin skin = UiSkin.skin();
        setBackground(skin.getDrawable("box-light-2"));
        titleLabel = new Label(title, skin, "title");
        titleLabel.setFontScale(1.15f); //fake bold
        titleLabel.setColor(0f, 0f, 0f, 1f);

        bodyLabel = new Label(body, skin, "default-black");
        bodyLabel.setWrap(true);
        iconsTable = new Table();
        iconsTable.left();
        defaults().left().top().expandX().fillX().padLeft(6f).padRight(6f);
        add(titleLabel);
        row();
        add(bodyLabel);
        row();
        add(iconsTable);
        setWidth(fixedWidth);
        invalidateHierarchy();
        pack();
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

    public void addIcon(Drawable drawable, float size) {
        Image icon = new Image(drawable);
        iconsTable.add(icon).size(size).padRight(4f);
        repack();
    }
}