package me.gabl.fablefields.map.material;

import lombok.Getter;
import me.gabl.fablefields.map.Cell;
import me.gabl.fablefields.map.logic.MapChunk;
import me.gabl.fablefields.map.logic.MapTile;
import me.gabl.fablefields.map.render.ContextAddress;
import me.gabl.fablefields.map.render.MapTileContext;
import me.gabl.fablefields.map.render.RenderInstruction;
import me.gabl.fablefields.map.render.ZIndex;

// Prozess:
// Pflanze angepflanzt
// 0: -> 1 t
// 1: muss bewässert werden
// wenn bei 1 bewässert: -> 2 t -> 3 t...
public class PlantTile extends MapTile {

    final float[] growthStages;
    @Getter
    private final PlantGrowTask growTask;
    @Getter
    private int growthStage = 0;
    private boolean watered = false;

    public PlantTile(PlantMaterial material, float[] growthStages, ContextAddress address, MapChunk chunk) {
        super(material, address);
        this.growthStages = growthStages;
        this.growTask = new PlantGrowTask(this, chunk);
    }

    public boolean isFullyGrown() {
        return growthStage == growthStages.length;
    }

    public void water() {
        if (needsWater()) {
            watered = true;
            growTask.reschedule();
        }
    }

    public boolean needsWater() {
        return growthStage == 1 && !watered;
    }

    public void grow() {
        growthStage++;
        //change graphics etc
    }

    @Override
    public RenderInstruction[] render(MapTileContext context) {
        int offset = getMaterial().tileSetOffset;
        ContextAddress address = context.getAddress();
        if (growthStage == 0 || growthStage == 1) {
            return RenderInstruction.of(Cell.get(offset + (growthStage == 0 ? 819 : 883)), address);
        }
        if (growthStage == 2) {
            return new RenderInstruction[]{new RenderInstruction(Cell.get(offset + 1011), address),
                    new RenderInstruction(Cell.get(offset + 947), address.up(), ZIndex.TALL_OVERLAY)};
        }

        if (growthStage == 3) {
            return new RenderInstruction[]{new RenderInstruction(Cell.get(offset + 1139), address),
                    new RenderInstruction(Cell.get(offset + 1075), address.up(), ZIndex.TALL_OVERLAY)};
        }

        throw new IllegalStateException("Unsupported growth stage: " + growthStage);
    }

    @Override
    public PlantMaterial getMaterial() {
        return (PlantMaterial) super.getMaterial();
    }
}
