package me.gabl.fablefields.map;

import me.gabl.fablefields.map.logic.MapTile;
import me.gabl.fablefields.map.render.RenderMaterial;

import java.util.Objects;

public abstract class Material implements RenderMaterial {

    public final String id; //used for equals, logging

    public Material(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass())
            return false;

        Material material = (Material) o;
        return Objects.equals(this.id, material.id);
    }

    public boolean materialEquals(Material material) {
        if (material == null)
            return false;
        return this.id.equals(material.id);
    }

    public boolean materialEquals(MapTile mapTile) {
        return mapTile != null && materialEquals(mapTile.material);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }

    @Override
    public String toString() {
        return "Material{" + "id='" + id + '\'' + '}';
    }

    public static boolean equals(Material m1, Material m2) {
        return (m1 == null && m2 == null) || (m1 != null && m1.materialEquals(m2));
    }
}
