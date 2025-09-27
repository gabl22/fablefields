package me.gabl.fablefields.map;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import me.gabl.fablefields.asset.Asset;
import me.gabl.fablefields.map.logic.MapLayer;
import me.gabl.fablefields.map.render.MapTileContext;
import me.gabl.fablefields.map.render.RenderMaterial;

import java.util.Objects;
import java.util.function.Consumer;

public abstract class MaterialX implements RenderMaterial {

    public static final MaterialX SAND = new MaterialX(_ -> {
    }, "sand"
    ) {
        @Override
        public Cell.GfxPair generateCell(MapTileContext context) {
            assert context.layer == MapLayer.GROUND;
            return Cell.pair(Cell.get(69));
        }
    };

    public static final MaterialX DIRT = new MaterialX(_ -> {
    }, "dirt"
    ) {
        @Override
        public Cell.GfxPair generateCell(MapTileContext context) {
            return Cell.pair(Cell.get(70));
        }
    };

    public static final MaterialX WATER = new MaterialX(_ -> {
    }, "water"
    ) {
        @Override
        public Cell.GfxPair generateCell(MapTileContext context) {
            if (context.layer != MapLayer.GROUND) {
                return null;
            }

            TiledMapTileLayer.Cell base = Cell.get(21 * 64 + 11 + (context.x % 4) - (context.y % 4) * 64);

            CellNeighborAnalysis analysis = CellNeighborAnalysis.get(context);
            if (MaterialX.SAND.equals(analysis.dominantNeighborX)) {
                return Cell.pair(base, Cell.get(2435, analysis));
            }

            if (MaterialX.DIRT.equals(analysis.dominantNeighborX)) {
                return Cell.pair(base, Cell.get(2499, analysis));
            }
            return Cell.pair(base);
        }
    };

    public final Consumer<MapCell<? extends Tile>> renderCell;
    public final String id; //used for equals, logging

    public MaterialX(Consumer<MapCell<? extends Tile>> renderCell, String id) {
        this.renderCell = renderCell;
        this.id = id;
    }

    public MaterialX(String id) {
        this.id = id;
        this.renderCell = _ -> {
        };
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass())
            return false;

        MaterialX material = (MaterialX) o;
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
        return "Material{" + "renderCell=" + renderCell + ", id='" + id + '\'' + '}';
    }
}
