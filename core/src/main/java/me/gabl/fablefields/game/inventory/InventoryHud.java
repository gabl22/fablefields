package me.gabl.fablefields.game.inventory;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.preference.KeyAction;
import me.gabl.fablefields.preference.Settings;
import me.gabl.fablefields.screen.game.HoverListener;
import me.gabl.fablefields.screen.ui.Hud;

import java.util.ArrayList;
import java.util.List;

public class InventoryHud extends Hud {

    private static final int COLUMNS = 10;
    private static final int ROWS = 3;
    private static final int SLOTS = COLUMNS * ROWS;

    private static final Label.LabelStyle slotCountStyle;

    static {
        BitmapFont font = new BitmapFont();
        font.setColor(Color.WHITE);
        slotCountStyle = new Label.LabelStyle(font, Color.BLACK);

    }

    private final List<Container<Image>> slotImages;
    private final List<Container<Label>> slotCount;

    private final NinePatchDrawable slotBackground;
    private final NinePatchDrawable slotSelectedBackground;

    private Table root;
    private Table visualInventory;
    private HoverListener inventoryHover;
    private final Inventory inventory;

    public InventoryHud(SpriteBatch batch, Inventory inventory) {
        super(batch);
        this.inventory = inventory;
        assert inventory.size == COLUMNS * ROWS;
        slotImages = new ArrayList<>(COLUMNS * ROWS);
        slotCount = new ArrayList<>(COLUMNS * ROWS);

        slotBackground = new NinePatchDrawable(Asset.UI_BOX_LIGHT);
        slotSelectedBackground = new NinePatchDrawable(Asset.UI_BOX_DARK);
    }

    @Override
    public void show() {
        root = new Table();
        root.setFillParent(true);
        root.bottom().left();
        stage.addActor(root);

        visualInventory = new Table();
        fillEmptyInventory();
        visualInventory.bottom().left();

        root.add(visualInventory);

        stage.addActor(root);
        inventoryHover = new HoverListener();
        visualInventory.addListener(inventoryHover);
        render();
    }

    private void fillEmptyInventory() {
        for (int i = 0; i < COLUMNS * ROWS; i++) {
            Image image = new Image();
//            image.setVisible(false); disables hover detection
            Label label = new Label(null, slotCountStyle);
            label.setFontScale(1.2f);
            label.setVisible(true);

            Container<Image> imageContainer = new Container<>(image);
            imageContainer.size(50, 50);
            imageContainer.pad(10);
            imageContainer.background(slotBackground);
            imageContainer.fill();
            slotImages.add(imageContainer);

            Container<Label> labelContainer = new Container<>(label);
            labelContainer.bottom().right().pad(2);
            labelContainer.setFillParent(true);
            slotCount.add(labelContainer);
        }

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLUMNS; c++) {
                int i = r * COLUMNS + c;
                Stack slot = new Stack();
                slot.add(slotImages.get(i));
                slot.add(slotCount.get(i));
                visualInventory.add(slot).size(50, 50);
            }
            visualInventory.row();
        }
    }

    public void render() {
        for (int i = 0; i < inventory.size; i++) {
            setSlot(i, inventory.slots[i]);
        }
    }

    public void setSlot(int slotId, Slot slot) {
        Image image = slotImages.get(slotId).getActor();
        Label label = slotCount.get(slotId).getActor();
        boolean containsItem = slot != null && slot.item != null && slot.item.type != null && slot.count > 0;
//        image.setVisible(containsItem);
        label.setVisible(containsItem && slot.count > 1);

        if (containsItem) {
            image.setDrawable(slot.item.render());
            label.setText(slot.count);
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
        slotImages.get(inventory.selectedSlot).background(slotBackground);
        inventory.selectedSlot = slot;
        slotImages.get(inventory.selectedSlot).background(slotSelectedBackground);
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
        selectSlot(inventory.selectedSlot + amount);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (Settings.keybind.get(keycode) == KeyAction.SWITCH_SLOT_BAR) {
            selectSlot(inventory.selectedSlot + 10);
            return true;
        }
        if (keycode >= Input.Keys.NUM_0 && keycode <= Input.Keys.NUM_9) {
            if (keycode == Input.Keys.NUM_0)
                keycode += 10;
            selectSlot((inventory.selectedSlot / 10) * 10 + keycode - Input.Keys.NUM_0 - 1);
            return true;
        }
        return false;
    }

    public Stage getStage() {
        return stage;
    }
}
