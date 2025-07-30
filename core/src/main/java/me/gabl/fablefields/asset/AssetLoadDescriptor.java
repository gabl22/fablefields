package me.gabl.fablefields.asset;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.files.FileHandle;

public class AssetLoadDescriptor<T> extends AssetDescriptor<T> {

    public final int section;

    public AssetLoadDescriptor(String fileName, Class<T> assetType, int section) {
        super(fileName, assetType);
        this.section = section;
    }

    public AssetLoadDescriptor(FileHandle file, Class<T> assetType, int section) {
        super(file, assetType);
        this.section = section;
    }

    public AssetLoadDescriptor(String fileName, Class<T> assetType, AssetLoaderParameters<T> params, int section) {
        super(fileName, assetType, params);
        this.section = section;
    }

    public AssetLoadDescriptor(FileHandle file, Class<T> assetType, AssetLoaderParameters<T> params, int section) {
        super(file, assetType, params);
        this.section = section;
    }
}
