package me.gabl.fablefields.map.render;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import me.gabl.fablefields.map.logic.Address;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class RenderInstruction implements Comparable<RenderInstruction> {

    public final double z;
    private final Cell cell;
    private final Address address;

    public RenderInstruction(Cell cell, Address address, double z) {
        this.cell = cell;
        this.address = address;
        this.z = z;
    }

    public RenderInstruction(Cell cell, Address address) {
        this.cell = cell;
        this.address = address;
        this.z = 0;
    }

    public static RenderInstruction[] of(Address address, Cell cell1, double z1, Cell cell2, double z2) {
        return toArray(new RenderInstruction(cell1, address, z1), new RenderInstruction(cell2, address, z2));
    }

    // fishy
    private static RenderInstruction[] toArray(RenderInstruction... instructions) {
        return instructions;
    }

    public static RenderInstruction[] of(Cell cell, MapTileContext context) {
        return of(context.getAddress(), cell, 0);
    }

    public static RenderInstruction[] of(Address address, Cell cell, double z) {
        return toArray(new RenderInstruction(cell, address, z));
    }

    public static RenderInstruction[] of(Cell cell, Address address) {
        return of(address, cell, 0);
    }

    @Override
    public int compareTo(@NotNull RenderInstruction that) {
        return Double.compare(this.z, that.z);
    }

    public Cell cell() {
        return cell;
    }

    public Address address() {
        return address;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (RenderInstruction) obj;
        return Objects.equals(this.cell, that.cell) && Objects.equals(this.address, that.address) && Double.doubleToLongBits(this.z) == Double.doubleToLongBits(that.z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cell, address, z);
    }

    @Override
    public String toString() {
        return "RenderInstruction{" + "cell=" + cell + ", address=" + address + ", z=" + z + '}';
    }
}
