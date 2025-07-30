package me.gabl.fablefields.screen;

import com.badlogic.gdx.utils.Disposable;
import me.gabl.fablefields.Main;

public class Screens implements Disposable {

    public final LoadingScreen loadingScreen;

    public Screens(Main main) {
        this.loadingScreen = new LoadingScreen(main);
    }

    @Override
    public void dispose() {
        this.loadingScreen.dispose();
    }
}
