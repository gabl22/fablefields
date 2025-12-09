package me.gabl.fablefields.asset;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import lombok.NoArgsConstructor;
import me.gabl.fablefields.util.Envelope;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class LanguageRegistry {

    private final FileHandle registryFile;
    private final Map<String, String> registry;
    private final Json json;

    public LanguageRegistry(FileHandle registryFile) {
        this.registryFile = registryFile;
        this.registry = new HashMap<>();
        this.json = new Json();
        this.json.setOutputType(OutputType.json);
    }

    public void read() {
        registry.clear();
        registry.putAll(load());
    }

    private Map<String, String> load() {
        return json.fromJson(Registry.class, registryFile).getData();
    }

    /**
     * @param name Mapping key
     *             throws {@link NullPointerException} or {@link ArrayIndexOutOfBoundsException} if name is not found
     * @return Drawable
     */
    public String get(@NotNull String name) {
        if (!registry.containsKey(name)) throw new NullPointerException("No mapping found for key: " + name);
        return registry.get(name);
    }

    @NoArgsConstructor
    private static class Registry extends Envelope<Map<String, String>> {

    }
}
