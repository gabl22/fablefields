package me.gabl.fablefields.map;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import lombok.Getter;
import lombok.Setter;

public abstract class Tile implements TiledMapTile {

    @Getter
    @Setter
    protected Material material;
    protected BlendMode blendMode = BlendMode.ALPHA;
    protected float offsetX, offsetY;
    protected MapProperties properties;
    protected MapObjects objects;
    protected int id;

    public Tile() {
    }

    public Tile(Tile copy) {
        this.material = copy.material;
        this.blendMode = copy.blendMode;
        this.offsetX = copy.offsetX;
        this.offsetY = copy.offsetY;
        if (copy.properties != null) {
            this.getProperties().putAll(copy.properties);
        }
        this.objects = copy.objects;
        this.id = copy.id;
    }

    @Override
    public MapProperties getProperties() {
        if (this.properties == null) {
            this.properties = new MapProperties();
        }
        return this.properties;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public BlendMode getBlendMode() {
        return this.blendMode;
    }

    @Override
    public void setBlendMode(BlendMode blendMode) {
        this.blendMode = blendMode;
    }

    @Override
    public float getOffsetX() {
        return this.offsetX;
    }

    @Override
    public void setOffsetX(float offsetX) {
        this.offsetX = offsetX;
    }

    @Override
    public float getOffsetY() {
        return this.offsetY;
    }

    @Override
    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
    }

    @Override
    public MapObjects getObjects() {
        if (this.objects == null) {
            this.objects = new MapObjects();
        }
        return this.objects;
    }
}
