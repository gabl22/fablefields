package me.gabl.fablefields.screen.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.game.inventory.ItemType;
import me.gabl.fablefields.screen.ui.Hud;
import me.gabl.fablefields.screen.ui.UiSkin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ToolTipHud extends Hud {

    private final Label label;
    private final Table root;
    private final Image itemImage;
    private final Image base;

    public ToolTipHud(SpriteBatch batch) {
        super(batch);
        this.root = new Table();

        Skin skin = UiSkin.skin();
        this.label = new Label(null, skin, "background"); // need any symbol for correct label.height
        Texture baseTexture = Asset.manager.get(Asset.getTexture("ui/itemdisc.png"));
        this.base = new Image(baseTexture);
        this.itemImage = new Image();
        itemImage.setSize(16, 16);
        label.setHeight(31);
        label.setWrap(false);
        root.addActor(label);
        root.addActor(base);
        root.addActor(itemImage);
        base.setPosition(0, 0);
        base.scaleBy(2f);
        itemImage.scaleBy(2f);
        itemImage.setPosition((base.getWidth() - itemImage.getWidth()) / 2 + 5,
                (base.getHeight() - itemImage.getHeight()) / 2 + 5);
        label.setPosition(base.getWidth() * base.getScaleX() - 12,
                (base.getHeight() * base.getScaleY() - label.getHeight()) / 2);
        stage.addActor(root);
    }


    public void update(@Nullable String text, @Nullable ItemType itemType) {
        if (text == null || itemType == null) {
            hide();
            return;
        }
        this.label.setText("  " + text);
        label.setWidth(label.getPrefWidth());
        itemImage.setDrawable(itemType.render());
        root.pack();
    }

    public void updatePosition(@NotNull Vector2 screenPosition) {
        //todo merge duplicate below/in ItemHoverHud
        Vector2 cursorPosition = stage.screenToStageCoordinates(screenPosition);
        float width = stage.getViewport().getWorldWidth();
        float height = stage.getViewport().getWorldHeight();
        float rootHalfWidth = root.getWidth() / 2;
        float rootHalfHeight = root.getHeight() / 2;
        float xDivertHorizontal = 25 + rootHalfWidth;
        float yDivertVertical = 25 + rootHalfHeight;
        float x = cursorPosition.x + xDivertHorizontal;
        float y = cursorPosition.y + yDivertVertical;
        if (x < rootHalfWidth) x = rootHalfWidth;
        if (x > width - rootHalfWidth) x = width - rootHalfWidth;
        if (y < rootHalfHeight) y = rootHalfHeight;
        if (y > height - rootHalfHeight) y = height - rootHalfHeight;
        root.setPosition(x, height - y);
    }

    @Override
    public void hide() {
        this.root.setVisible(false);
    }

    @Override
    public boolean isHovering() {
        return false;
    }

    @Override
    public void show() {
        this.root.setVisible(true);
    }
}
