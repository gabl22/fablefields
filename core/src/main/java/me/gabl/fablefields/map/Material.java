package me.gabl.fablefields.map;

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

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }

    @Override
    public String toString() {
        return "Material{" + "id='" + id + '\'' + '}';
    }
}
