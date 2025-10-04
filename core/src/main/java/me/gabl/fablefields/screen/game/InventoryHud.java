package me.gabl.fablefields.screen.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.preference.KeyAction;
import me.gabl.fablefields.preference.Settings;
import me.gabl.fablefields.screen.ui.Hud;

import java.util.ArrayList;
import java.util.List;

public class InventoryHud extends Hud {

    private static final int COLUMNS = 10;
    private static final int ROWS = 3;
    private static final int SLOTS = COLUMNS * ROWS;

    private final List<Container<Image>> slotImages;

    private final NinePatchDrawable slotBackground;
    private final NinePatchDrawable slotSelectedBackground;

    private Table root;
    private Table inventory;
    private HoverListener inventoryHover;

    private int selectedSlot = 0;

    public InventoryHud(SpriteBatch batch) {
        super(batch);
        slotImages = new ArrayList<>(COLUMNS * ROWS);

        slotBackground = new NinePatchDrawable(Asset.UI_BOX_LIGHT);
        slotSelectedBackground = new NinePatchDrawable(Asset.UI_BOX_DARK);
    }

    @Override
    public void show() {
        root = new Table();
        root.setFillParent(true);
        root.bottom().left();
        stage.addActor(root);

        inventory = new Table();
        fillEmptyInventory();
        inventory.bottom().left();

        root.add(inventory);

        stage.addActor(root);
        inventoryHover = new HoverListener();
        inventory.addListener(inventoryHover);

        setSlot(0, 3050);
        setSlot(1, 3113);
        setSlot(2, 2922);
        setSlot(3, 817);
        selectSlot(3);
    }

    private void fillEmptyInventory() {
        for (int i = 0; i < COLUMNS * ROWS; i++) {
            Image itemImage = new Image();
            //            itemImage.setVisible(false);

            Container<Image> container = new Container<>(itemImage);
            container.size(40, 40);
            container.pad(10);
            container.background(slotBackground);
            container.fill();

            slotImages.add(container);
        }

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLUMNS; c++) {
                int i = r * COLUMNS + c;
                inventory.add(slotImages.get(i)).size(40, 40);
            }
            inventory.row();
        }
    }

    public void setSlot(int slot, int tileSetId) {
        Image image = slotImages.get(slot).getActor();
        Drawable drawable = new Image(Asset.TILESET.getTile(tileSetId).getTextureRegion()).getDrawable();
        image.setDrawable(drawable);
    }

    public void selectSlot(int slot) {
        slot = slot % SLOTS;
        if (slot < 0)
            slot += SLOTS;
        slotImages.get(selectedSlot).background(slotBackground);
        this.selectedSlot = slot;
        slotImages.get(selectedSlot).background(slotSelectedBackground);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        //        table.setScale(1 / Math.min(width / 640f, height / 480f));
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        if (!inventoryHover.isHovering())
            return false;
        scrollSlots((int) amountY);
        return true;
    }

    public void scrollSlots(int amount) {
        selectSlot(selectedSlot + amount);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (Settings.keybind.get(keycode) == KeyAction.SWITCH_SLOT_BAR) {
            selectSlot(selectedSlot + 10);
            return true;
        }
        if (keycode >= Input.Keys.NUM_0 && keycode <= Input.Keys.NUM_9) {
            if (keycode == Input.Keys.NUM_0)
                keycode += 10;
            selectSlot((selectedSlot / 10) * 10 + keycode - Input.Keys.NUM_0 - 1);
            return true;
        }
        return false;
    }

    public Stage getStage() {
        return stage;
    }
}
