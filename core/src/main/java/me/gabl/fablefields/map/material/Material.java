package me.gabl.fablefields.map.material;

import me.gabl.fablefields.map.logic.MapChunk;
import me.gabl.fablefields.map.logic.MapTile;
import me.gabl.fablefields.map.render.ContextAddress;
import me.gabl.fablefields.map.render.RenderMaterial;

import java.util.Objects;

public abstract class Material implements RenderMaterial {

    public final String id; //used for equals, logging

    public Material(String id) {
        this.id = id;
    }

    public static boolean equals(Material m1, Material m2) {
        return (m1 == null && m2 == null) || (m1 != null && m1.materialEquals(m2));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) return false;

        Material material = (Material) o;
        return Objects.equals(this.id, material.id);
    }

    public boolean materialEquals(MapTile mapTile) {
        return mapTile != null && materialEquals(mapTile.material);
    }

    public boolean materialEquals(Material material) {
        if (material == null) return false;
        return this.id.equals(material.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }

    @Override
    public String toString() {
        return "Material{" + "id='" + id + '\'' + '}';
    }

    @Deprecated
    public MapTile createMapTile() {
        return createMapTile(null);
    }

    public MapTile createMapTile(ContextAddress address) {
        return new MapTile(this, address);
    }

    public MapTile createMapTile(ContextAddress address, MapChunk chunk) {
        return createMapTile(address);
    }
}
