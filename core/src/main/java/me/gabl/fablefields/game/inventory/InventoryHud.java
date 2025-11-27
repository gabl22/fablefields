package me.gabl.fablefields.game.inventory;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.preference.KeyAction;
import me.gabl.fablefields.preference.Settings;
import me.gabl.fablefields.screen.game.HoverListener;
import me.gabl.fablefields.screen.ui.Hud;

import java.util.ArrayList;
import java.util.List;

public class InventoryHud extends Hud {

    public static final int COLUMNS = 10;
    public static final int ROWS = 3;
    public static final int SLOTS = COLUMNS * ROWS;

    private static final Label.LabelStyle slotCountStyle;

    static {
        BitmapFont font = new BitmapFont();
        font.setColor(Color.WHITE);
        slotCountStyle = new Label.LabelStyle(font, Color.BLACK);
    }

    private final List<Container<Image>> slotImages;
    private final List<Container<Label>> slotCount;

    private final List<Stack> slots;

    private final NinePatchDrawable slotBackground;
    private final NinePatchDrawable slotSelectedBackground;
    private final NinePatchDrawable slotSwapBackground;
    private final Inventory inventory;
    private int swapSlot = -1;

    private Table visualInventory;
    private HoverListener inventoryHover;

    public InventoryHud(SpriteBatch batch, Inventory inventory) {
        super(batch);
        this.inventory = inventory;
        inventory.onUpdate = this::update;
        assert inventory.size == COLUMNS * ROWS;
        slotImages = new ArrayList<>(COLUMNS * ROWS);
        slotCount = new ArrayList<>(COLUMNS * ROWS);
        slots = new ArrayList<>(COLUMNS * ROWS);

        slotBackground = new NinePatchDrawable(Asset.UI_BOX_LIGHT);
        slotSelectedBackground = new NinePatchDrawable(Asset.UI_BOX_DARK);
        slotSwapBackground = new NinePatchDrawable(Asset.UI_BOX_WHITE);
    }

    public void update() {
        render();
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
        label.setVisible(containsItem && slot.count > 1);

        if (containsItem) {
            image.setDrawable(slot.item.render());
            label.setText(slot.count);
        } else {
            image.setDrawable(null);
            label.setText(null);
        }
    }

    //removes 1 of the items selected
    public void removeSelectedItem() {
        inventory.removeSelectedItem();
        render(inventory.selectedSlot);
    }

    public void render(int slotId) {
        setSlot(slotId, inventory.slots[slotId]);
    }

    @Override
    public void show() {
        Table root = new Table();
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

            Stack stack = new Stack(imageContainer, labelContainer);
            slots.add(stack);
        }

        slotImages.get(inventory.selectedSlot).background(slotSelectedBackground);

        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < COLUMNS; column++) {
                final int index = row * COLUMNS + column;
                Stack slot = slots.get(index);
                visualInventory.add(slot).size(50, 50);

                slot.addListener(new ClickListener(0) {
                    @Override
                    public void clicked(InputEvent _event, float _x, float _y) {
                        selectSlot(index);
                        setSwapSlot(-1);
                    }
                });

                slot.addListener(new ClickListener(1) {
                    @Override
                    public void clicked(InputEvent _event, float _x, float _y) {
                        if (swapSlot == -1) {
                            setSwapSlot(index);
                            return;
                        }
                        inventory.swap(swapSlot, index);
                        setSwapSlot(-1);
                    }
                });
            }
            visualInventory.row();
        }
    }

    public void selectSlot(int slot) {
        slot = slot % SLOTS;
        if (slot < 0) slot += SLOTS;
        int oldSelected = inventory.selectedSlot;
        inventory.selectedSlot = slot;
        setSlotBackground(oldSelected);
        setSlotBackground(inventory.selectedSlot);
    }

    public void setSwapSlot(int slot) {
        int oldSwap = swapSlot;
        swapSlot = slot;
        setSlotBackground(oldSwap);
        setSlotBackground(swapSlot);
    }

    public void setSlotBackground(int slot) {
        if (slot < 0) return;
        Container<Image> image = slotImages.get(slot);
        if (swapSlot == slot) {
            image.background(slotSwapBackground);
        } else if (inventory.selectedSlot == slot) {
            image.background(slotSelectedBackground);
        } else {
            image.background(slotBackground);
        }
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        if (!inventoryHover.isHovering()) return false;
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
            if (keycode == Input.Keys.NUM_0) keycode += 10;
            selectSlot((inventory.selectedSlot / 10) * 10 + keycode - Input.Keys.NUM_0 - 1);
            return true;
        }
        return false;
    }

    public Stage getStage() {
        return stage;
    }
}
