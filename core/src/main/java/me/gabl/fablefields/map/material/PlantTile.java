package me.gabl.fablefields.map.material;

import lombok.Getter;
import me.gabl.fablefields.map.Cell;
import me.gabl.fablefields.map.logic.MapChunk;
import me.gabl.fablefields.map.logic.MapTile;
import me.gabl.fablefields.map.render.ContextAddress;
import me.gabl.fablefields.map.render.MapTileContext;
import me.gabl.fablefields.map.render.RenderInstruction;
import me.gabl.fablefields.map.render.ZIndex;
import me.gabl.fablefields.task.eventbus.EventBus;

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

    public PlantTile(PlantMaterial material, float[] growthStages, ContextAddress address, MapChunk chunk, EventBus bus) {
        super(material, address);
        this.growthStages = growthStages;
        this.growTask = new PlantGrowTask(this, chunk, bus);
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
    }

    @Override
    public RenderInstruction[] render(MapTileContext context) {
        ContextAddress address = context.getAddress();
        if (growthStage == 0 || growthStage == 1) {
            return RenderInstruction.of(Cell.get("plant/%s/stage/%s".formatted(material.id, growthStage)), address);
        }
        if (growthStage == 2 || growthStage == 3) {
            return new RenderInstruction[]{
                    new RenderInstruction(Cell.get("plant/%s/stage/%s".formatted(material.id, growthStage)), address),
                    new RenderInstruction(Cell.get("plant/%s/stage/%s_upper".formatted(material.id, growthStage)), address.up(), ZIndex.TALL_OVERLAY)
            };
        }

        throw new IllegalStateException("Unsupported growth stage: " + growthStage);
    }

    @Override
    public PlantMaterial getMaterial() {
        return (PlantMaterial) super.getMaterial();
    }
}
