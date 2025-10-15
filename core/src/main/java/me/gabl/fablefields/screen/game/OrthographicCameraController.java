package me.gabl.fablefields.screen.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.Viewport;

public class OrthographicCameraController extends InputAdapter {
    final OrthographicCamera camera;
    final Viewport viewport;

    final Vector2 worldTouch = new Vector2();
    final Vector2 mouseWorld = new Vector2();
    final Vector2 mouseScreen = new Vector2();
    final Actor center;
    boolean followCenter = true;
    boolean dragging = false;

    public OrthographicCameraController(Viewport viewport, Actor center) {
        this.camera = (OrthographicCamera) viewport.getCamera();

        this.center = center;
        this.viewport = viewport;
        setStandardView();
    }

    public void update() {
        if (this.followCenter) {
            this.camera.position.set(this.center.getX(), this.center.getY(), 0);
        }
        this.camera.update();
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (dragging) {
            dragging = false;
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        dragging = true;
        followCenter = false;
        camera.update();
        alignCamera(worldTouch, new Vector2(x, y));
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        worldTouch.set(screenX, screenY);
        viewport.unproject(worldTouch);
        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        if (this.camera.zoom < 0.02) {
            this.camera.zoom += amountY / 2048;
        } else if (this.camera.zoom < 0.06) {
            this.camera.zoom += amountY / 256;
        } else {
            this.camera.zoom += amountY / 32;
        }

        if (this.camera.zoom < 0.003) {
            this.camera.zoom = 0.003f;
        }
        if (this.camera.zoom > 2) {
            this.camera.zoom = 2f;
        }
        camera.update();
        if (!followCenter) {
            alignCamera(mouseWorld, mouseScreen);
        }
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        mouseScreen.set(screenX, screenY);
        mouseWorld.set(screenX, screenY);
        viewport.unproject(mouseWorld);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.C) {
            setStandardView();
            return true;
        }
        return false;
    }

    public void alignCamera(Vector2 worldCoords, Vector2 screenCoords) {
        float screenX = screenCoords.x;
        float screenY = screenCoords.y;
        camera.translate(viewport.unproject(screenCoords).sub(worldCoords).scl(-1));
        screenCoords.set(screenX, screenY);
        camera.update();
    }

    private void setStandardView() {
        this.camera.zoom = 0.03f;
        this.camera.position.set(this.center.getX(), this.center.getY(), 0);
        this.followCenter = true;
        this.camera.update();
    }
}
