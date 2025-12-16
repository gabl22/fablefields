package me.gabl.fablefields.screen.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.Viewport;
import lombok.Getter;

public class OrthographicCameraController extends InputAdapter {

    final OrthographicCamera camera;
    final Viewport viewport;

    final float dragTolerance2 = 10f * 10f;
    final Vector2 worldTouch = new Vector2();
    final Vector2 mouseWorld = new Vector2();
    final Vector2 mouseScreen = new Vector2();
    final Vector2 touchDown = new Vector2();
    final Actor center;
    boolean followCenter = true;
    @Getter
    boolean dragging = false;
    boolean snapDrag = false;

    boolean hasTouchedDown = false;

    public OrthographicCameraController(Viewport viewport, Actor center) {
        this.camera = (OrthographicCamera) viewport.getCamera();

        this.center = center;
        this.viewport = viewport;
        setStandardView();
    }

    private void setStandardView() {
        this.camera.zoom = 0.03f;
        this.camera.position.set(this.center.getX(), this.center.getY(), 0);
        this.followCenter = true;
        this.camera.update();
    }

    public void update() {
        if (this.followCenter && !this.snapDrag) {
            this.camera.position.set(this.center.getX(), this.center.getY(), 0);
        }
        this.camera.update();
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        snapDrag = false;
        if (dragging) {
            dragging = false;
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (!hasTouchedDown) {
            setTouchDown(screenX, screenY);
            return true;
        }
        mouseScreen.set(screenX, screenY);
        if (dragging || dragToleranceReached()) {
            snapDrag = false;
            dragging = true;
            followCenter = false;
        } else {
            snapDrag = true;
        }
        alignCamera(worldTouch, mouseScreen);
        return true;
    }

    private boolean dragToleranceReached() {
        return dragTolerance2 <= (mouseScreen.x - touchDown.x) * (mouseScreen.x - touchDown.x) + (mouseScreen.y - touchDown.y) * (mouseScreen.y - touchDown.y);
    }

    public void alignCamera(Vector2 worldCoords, Vector2 screenCoords) {
        float screenX = screenCoords.x;
        float screenY = screenCoords.y;
        camera.translate(viewport.unproject(screenCoords).sub(worldCoords).scl(-1));
        screenCoords.set(screenX, screenY);
        camera.update();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        setTouchDown(screenX, screenY);
        return false;
    }

    private void setTouchDown(int screenX, int screenY) {
        hasTouchedDown = true;
        touchDown.set(screenX, screenY);
        worldTouch.set(screenX, screenY);
        viewport.unproject(worldTouch);
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
}
