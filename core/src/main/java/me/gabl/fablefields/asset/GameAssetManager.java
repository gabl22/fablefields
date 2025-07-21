package me.gabl.fablefields.asset;

import com.badlogic.gdx.assets.AssetManager;
import me.gabl.fablefields.player.Action;
import me.gabl.fablefields.player.ActionLayer;

public class GameAssetManager extends AssetManager {

    public GameAssetManager() {
        super();
        super.setLoader(AnimationD.class, new AnimationLoader(super.getFileHandleResolver()));
    }

    public void queuePreLoad() {
        super.load(Asset.LOGO);
        super.load(Asset.UI_BOX);
        super.update(100);
        super.finishLoading();
        Asset.completePreLoad();
    }

    public void queueHuman() {
        for (Action action : Action.values()) {
            for (ActionLayer layer : ActionLayer.values()) {
                super.load(resolveHumanCharacterPath(action, layer), AnimationD.class, action.getParameters());
            }
        }
        super.load(Asset.UI_SKIN);
    }

    private static String resolveHumanCharacterPath(Action action, ActionLayer layer) {
        return "characters/human/" + action.id + "/" + layer.id + "_" + action.id + ".png";
    }
}
