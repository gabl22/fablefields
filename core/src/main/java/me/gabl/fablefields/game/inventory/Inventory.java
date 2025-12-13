package me.gabl.fablefields.game.inventory;

import me.gabl.fablefields.screen.game.GameScreen;
import me.gabl.fablefields.task.eventbus.EventBus;
import me.gabl.fablefields.task.eventbus.event.InventoryAddItemEvent;
import me.gabl.fablefields.task.eventbus.event.InventorySwapSlotEvent;

import java.util.function.Predicate;

public class Inventory {

    public final int size;
    // slot[i] nullable
    final Slot[] slots;
    Runnable onUpdate;
    int selectedSlot;
    private final EventBus bus;

    public Inventory(int size, GameScreen screen) {
        this.slots = new Slot[size];
        this.size = size;
        this.selectedSlot = 0;
        this.bus = screen.eventBus;
    }

    public Slot getSelectedSlot() {
        return slots[selectedSlot];
    }

    public Item getSelectedItem() {
        if (slots[selectedSlot] == null) return null;
        return slots[selectedSlot].item;
    }

    public int countOf(ItemType type) {
        return countItems(slot -> slot.item.typeEquals(type));
    }

    public int countItems(Predicate<Slot> predicate) {
        int count = 0;
        for (Slot slot : slots) {
            if (predicate.test(slot)) {
                count++;
            }
        }
        return count;
    }

    public boolean[] match(Predicate<Slot> predicate) {
        boolean[] matches = new boolean[size];
        for (int i = 0; i < size; i++) {
            matches[i] = predicate.test(slots[i]);
        }
        return matches;
    }

    private Slot setSlot(int slotId, Slot slot) {
        Slot oldSlot = slots[slotId];
        slots[slotId] = slot;
        reportUpdate();
        return oldSlot;
    }

    public Slot getSlot(int slotId) {
        return slots[slotId];
    }

    private void reportUpdate() {
        if (onUpdate != null) onUpdate.run();
    }

    public void swap(int slotId1, int slotId2) {
        Slot oldSlot = slots[slotId1];
        slots[slotId1] = slots[slotId2];
        slots[slotId2] = oldSlot;
        bus.fire(new InventorySwapSlotEvent(this, slotId1, slotId2));
        reportUpdate();
    }

    void removeSelectedItem() {
        if (slots[selectedSlot] != null) {
            slots[selectedSlot].count--;
            if (slots[selectedSlot].count <= 0) slots[selectedSlot] = null;
        }
        reportUpdate();
    }

    /**
     * @param slot count & item to be added
     * @return true iff item has been added to inventory
     */
    public boolean addItem(Slot slot) {
        return addItem(slot.item, slot.count);
    }

    /**
     * adds one item to the inventory
     * @param type of item to be added
     * @return true iff item has been added to inventory
     */
    public boolean addItem(ItemType type) {
        return addItem(type, 1);
    }

    /**
     * @param count amount of item to be added
     * @param item  item to be added
     * @return true iff item has been added to inventory
     */
    public boolean addItem(Item item, int count) {
        if (item == null) return true;
        for (int i = 0; i < size; i++) {
            if (slots[i] == null) continue;
            if (item.mergeableWith(slots[i].item)) {
                slots[i] = new Slot(item, count + slots[i].count);
                bus.fire(new InventoryAddItemEvent(this, slots[i]));
                reportUpdate();
                return true;
            }
        }

        for (int i = 0; i < size; i++) {
            if (slots[i] == null || slots[i].item == null) {
                slots[i] = new Slot(item, count);
                bus.fire(new InventoryAddItemEvent(this, slots[i]));
                reportUpdate();
                return true;
            }
        }

        return false;
    }

    /**
     * @param type  Type of the item to be added. The Item is generated using {@link ItemType#createItem()}
     * @param count the amount of item to be added
     * @return true iff item has been added to inventory
     */
    public boolean addItem(ItemType type, int count) {
        return addItem(type.createItem(), count);
    }
}
