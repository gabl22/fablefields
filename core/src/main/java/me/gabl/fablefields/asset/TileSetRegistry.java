package me.gabl.fablefields.asset;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import lombok.NoArgsConstructor;
import me.gabl.fablefields.util.Envelope;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TileSetRegistry {

    private final FileHandle registryFile;
    private final Map<String, Float> registry;
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

    private Map<String, Float> load() {
        return json.fromJson(Registry.class, registryFile).getData();
    }

    /**
     * @return an unmodifiable set of all tileset ids referenced by the registry
     */
    public Set<Integer> getTileSetIds() {
        return registry.values().stream().map(Float::intValue).collect(Collectors.toUnmodifiableSet());
    }

    /**
     * @param name Mapping key
     *             throws {@link NullPointerException} or {@link ArrayIndexOutOfBoundsException} if name is not found
     *             or mapped to an illegally high value
     * @return Drawable
     */
    public Drawable getDrawable(String name) {
        return tileSet.getDrawable(getRegistryReference(name));
    }

    public Image getImage(String name) {
        return tileSet.getImage(getRegistryReference(name));
    }

    public TiledMapTile getTile(String name) {
        return tileSet.getTile(getRegistryReference(name));
    }

    public TextureRegion getTextureRegion(String name) {
        return tileSet.getTextureRegion(getRegistryReference(name));
    }

    public Integer getRegistryReference(String name) {
        return (registry.getOrDefault(name, -1f).intValue());
    }

    @NoArgsConstructor
    private static class Registry extends Envelope<Map<String, Float>> {

        public Registry(Map<String, Float> data) {
            super(data);
        }
    }
}
