package me.gabl.fablefields.screen.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.game.inventory.ItemType;
import me.gabl.fablefields.game.inventory.Slot;
import me.gabl.fablefields.screen.ui.Hud;
import me.gabl.fablefields.screen.ui.UiSkin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemHoverNameHud extends Hud {

    private final Label label;
    private final Group root;

    public ItemHoverNameHud(SpriteBatch batch) {
        super(batch);
        this.root = new Group();

        Skin skin = UiSkin.skin();
        this.label = new Label("!", skin, "background"); // need any symbol for correct label.height
        label.setPosition( -12,  - label.getHeight() / 2);
        root.addActor(label);
        stage.addActor(root);
        hide();
    }

    public void updatePosition(@NotNull Vector2 screenPosition) {
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
        root.setPosition(x, y);
    }

    @Override
    public void hide() {
        this.root.setVisible(false);
    }

    @Override
    public boolean isHovering() {
        return false;
    }

    //dirty workaround
    @Deprecated @Override
    public void show() {
//        this.root.setVisible(true);
    }

    private void showTooltip() {
        root.setVisible(true);
    }

    public void update(Slot stack) {
        if (stack == null || stack.item == null) {
            hide();
            return;
        }

        showTooltip();
        this.label.setText(stack.item.type.getName());
        label.setWidth(label.getPrefWidth());
    }
}
