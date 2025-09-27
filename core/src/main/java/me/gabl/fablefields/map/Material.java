package me.gabl.fablefields.map;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import me.gabl.fablefields.map.logic.MapLayer;
import me.gabl.fablefields.map.render.MapTileContext;
import me.gabl.fablefields.map.render.RenderMaterial;

import java.util.Objects;

public abstract class Material implements RenderMaterial {

    public static final Material SAND = new Material("sand") {
        @Override
        public Cell.GfxPair generateCell(MapTileContext context) {
            assert context.layer == MapLayer.GROUND;
            return Cell.pair(Cell.get(69));
        }
    };

    public static final Material DIRT = new Material("dirt") {
        @Override
        public Cell.GfxPair generateCell(MapTileContext context) {
            return Cell.pair(Cell.get(70));
        }
    };

    public static final Material WATER = new Material("water") {
        @Override
        public Cell.GfxPair generateCell(MapTileContext context) {
            if (context.layer != MapLayer.GROUND) {
                return null;
            }

            TiledMapTileLayer.Cell base = Cell.get(21 * 64 + 11 + (context.x % 4) - (context.y % 4) * 64);

            CellNeighborAnalysis analysis = CellNeighborAnalysis.get(context);
            if (Material.SAND.equals(analysis.dominantNeighbor)) {
                return Cell.pair(base, Cell.get(2435, analysis));
            }

            if (Material.DIRT.equals(analysis.dominantNeighbor)) {
                return Cell.pair(base, Cell.get(2499, analysis));
            }
            return Cell.pair(base);
        }
    };

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

    // 4: Water Shadow
    // 5: Sand Shadow


    @Override
    public String toString() {
        return "Material{" + "id='" + id + '\'' + '}';
    }
}
