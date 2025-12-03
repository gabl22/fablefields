package me.gabl.fablefields.asset.tileset;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import me.gabl.fablefields.asset.TileSet;

import java.util.HashMap;
import java.util.Map;

public class TileSetRegistry {

    private final FileHandle registryFile;
    private final Map<String, Integer> registry;
    private final Json json;
    private final TileSet tileSet;

    public TileSetRegistry(FileHandle registryFile, TileSet tileSet) {
        this.registryFile = registryFile;
        this.tileSet = tileSet;
        this.registry = new HashMap<>();
        this.json = new Json();
        this.json.setOutputType(OutputType.json);
    }

    public void read() {
        registry.clear();
        registry.putAll(load());
    }

    @SuppressWarnings("unchecked")
    private Map<String, Integer> load() {
        return json.fromJson(Map.class, registryFile);
    }

    /**
     * @param name Mapping key
     * throws {@link NullPointerException} or {@link ArrayIndexOutOfBoundsException} if name is not found or mapped to an illegally high value
     * @return Drawable
     */
    public Drawable getDrawable(String name) {
        return tileSet.getDrawable(registry.getOrDefault(name, -1));
    }

    public Image getImage(String name) {
        return tileSet.getImage(registry.getOrDefault(name, -1));
    }

    public TextureRegion getTextureRegion(String name) {
        return tileSet.getTextureRegion(registry.getOrDefault(name, -1));
    }
}
