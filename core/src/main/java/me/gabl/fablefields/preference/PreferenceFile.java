package me.gabl.fablefields.preference;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import me.gabl.common.log.Logger;
import me.gabl.fablefields.util.GdxLogger;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

public class PreferenceFile<T> {

    private static final String FOLDER = "xmlgdxpref";
    private static final Logger log = GdxLogger.get(PreferenceFile.class);

    private final String name;
    private final int version;
    private final Class<T> type;
    protected final Json json;
    protected T data;

    private final Supplier<T> constructor;


    public PreferenceFile(String name, Class<T> type, int version) {
        this(name, type, version, null);
    }

    public PreferenceFile(String name, Class<T> type, int version, Supplier<T> constructor) {
        this.name = name;
        this.version = version;
        this.type = type;
        this.constructor = constructor;
        this.json = getJson();
    }

    private Json getJson() {
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        return json;
    }

    private FileHandle getFile() {
        return getFile(FOLDER + "/" + name + ".json");
    }

    private FileHandle getTempFile() {
        return getFile(FOLDER + "/" + name + ".temp.json");
    }

    private FileHandle getFile(String path) {
        return Gdx.files.local(path);
    }

    public T get() {
        return data;
    }

    public boolean hasData() {
        return data != null;
    }

    protected void tryMigrate(int oldVersion) {
    }

    protected void setDefaultValues() {
    }

    private T getInstance() {
        if (constructor != null) {
            return constructor.get();
        }
        try {
            return type.getDeclaredConstructor().newInstance();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            throw new IllegalArgumentException("Unable to create instance of " + type.getName(), e);
        }
    }

    public PreferenceFile<T> load() {
        FileHandle file = getFile();
        if (!file.exists()) {
            setToDefault();
            return this;
        }
        read();
        return this;
    }

    public void setToDefault() {
        this.data = getInstance();
        log.info("Setting default value for " + name);
        setDefaultValues();
        save();
    }

    private void read() {
        try {
            Envelope<?> env = json.fromJson(Envelope.class, getFile().readString());
            data = (T) env.data;
            if (data == null) {
                setToDefault();
                return;
            }
            if (env.version != version) {
                tryMigrate(env.version);
                save();
            }
        } catch (Exception e) {
            log.error("Exception while reading: " + e.getMessage(), e);
            setToDefault();
        }
    }

    public void save() {
        saveToFile();
    }

    private void saveToFile() {
        Envelope<T> envelope = new Envelope<>(version, data);
        String output = json.toJson(envelope);
        FileHandle tempFile = getTempFile();
        FileHandle file = getFile();
        tempFile.writeString(output, false);
        if (file.exists()) {
            file.delete();
        }
        tempFile.moveTo(file);
    }

    @NoArgsConstructor
    @AllArgsConstructor
    public static class Envelope<T> {
        private int version;
        private T data;
    }
}
