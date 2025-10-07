package me.gabl.fablefields.game.inventory;

import java.util.function.Predicate;

public class Inventory {

    public final int size;
    // slot[i] nullable
    final Slot[] slots;
    int selectedSlot;

    public Inventory(int size) {
        this.slots = new Slot[size];
        this.size = size;
        this.selectedSlot = 0;
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

    public Slot setSlot(int slotId, Slot slot) {
        Slot oldSlot = slots[slotId];
        slots[slotId] = slot;
        return oldSlot;
    }

    public void swap(int slotId1, int slotId2) {
        Slot oldSlot = slots[slotId1];
        slots[slotId1] = slots[slotId2];
        slots[slotId2] = oldSlot;
    }
}
