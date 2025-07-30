package me.gabl.fablefields.asset;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import me.gabl.common.log.Logger;
import me.gabl.fablefields.util.GdxLoggerPublisher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GameAssetManager extends AssetManager {

    private static final Logger logger = new Logger(GameAssetManager.class.getCanonicalName(),
        GdxLoggerPublisher.centralPublisher
    );

    private final List<AssetLoadDescriptor<?>> assets;
    int loadedSection;

    public GameAssetManager() {
        super();
        this.assets = new ArrayList<>();
        super.setLoader(AnimationD.class, new AnimationLoader(super.getFileHandleResolver()));
    }

    public void registerAssets(Collection<AssetLoadDescriptor<?>> assets) {
        this.assets.addAll(assets);
    }

    public void registerAssets(AssetLoadDescriptor<?>... assets) {
        for (AssetLoadDescriptor<?> asset : assets) {
            this.registerAsset(asset);
        }
    }

    public void registerAsset(AssetLoadDescriptor<?> asset) {
        this.assets.add(asset);
    }

    public void loadAssets(int upToSection) {
        for (AssetLoadDescriptor<?> asset : this.assets) {
            if (asset.section <= upToSection) {
                if (!super.isLoaded(asset)) {
                    super.load(asset);
                }
            }
        }
        if (this.loadedSection < upToSection) {
            this.loadedSection = upToSection;
        }
    }

    public <T> T get(AssetDescriptor<T> descriptor) {
        if (this.isLoaded(descriptor)) {
            return super.get(descriptor);
        }
        logger.warn("Asset loaded on fly.", descriptor);
        this.load(descriptor);
        return this.finishLoadingAsset(descriptor);
    }

    public void complete() {
        super.finishLoading();
        Asset.completeLoad(this.loadedSection);
    }
}
