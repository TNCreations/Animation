/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by hafiz on 11/21/2016.
 */

public class Hero implements InputProcessor {
    private Animation arAnimatioin[];
    private TextureRegion trFinal; // a single temporary texture region
    private Texture TextureSheet;
    private float fX, fY, fSpeed;
    private boolean[] arbKeys;
    private SpriteSheetAnimator spriteSheetAnimator;
    private int nFrame, nMovementCycle, nTimer;

    public Hero() {
        Gdx.input.setInputProcessor(this);
        TextureSheet = new Texture(Gdx.files.internal("Vlad.png"));
        fX = 100;
        fY = 100;
        fSpeed = 2;
        arbKeys = new boolean[512];
        nMovementCycle = 0;
        spriteSheetAnimator = new SpriteSheetAnimator(TextureSheet, 8, 8, 8, 1f);
        arAnimatioin = spriteSheetAnimator.animate();
        nTimer = 0;
    }

    void draw(SpriteBatch batch) {
        nFrame ++;
        if (nFrame % 2 == 0) {
            nTimer++;
        }
        if (nTimer > 6) {
            nTimer = 0;
        }
        trFinal = arAnimatioin[nMovementCycle].getKeyFrame(nTimer, true);
        //if (bMoving) {
        batch.draw(trFinal, fX, fY);
        //}
        move();
    }

    void move() {
        if (arbKeys[Input.Keys.UP] && arbKeys[Input.Keys.LEFT]) {
            nMovementCycle = 3;
            fX -= fSpeed;
            fY += fSpeed;
        } else if (arbKeys[Input.Keys.UP] && arbKeys[Input.Keys.RIGHT]) {
            nMovementCycle = 2;
            fX += fSpeed;
            fY += fSpeed;
        } else if (arbKeys[Input.Keys.DOWN] && arbKeys[Input.Keys.LEFT]) {
            nMovementCycle = 6;
            fX -= fSpeed;
            fY -= fSpeed;
        } else if (arbKeys[Input.Keys.DOWN] && arbKeys[Input.Keys.RIGHT]) {
            nMovementCycle = 5;
            fX += fSpeed;
            fY -= fSpeed;
        } else if (arbKeys[Input.Keys.UP]) {
            nMovementCycle = 1;
            fY += fSpeed;
        } else if (arbKeys[Input.Keys.DOWN]) {
            nMovementCycle = 4;
            fY -= fSpeed;
        } else if (arbKeys[Input.Keys.LEFT]) {
            nMovementCycle = 7;
            fX -= fSpeed;
        } else if (arbKeys[Input.Keys.RIGHT]) {
            nMovementCycle = 0;
            fX += fSpeed;
        } else {
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        arbKeys[keycode] = true;
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        arbKeys[keycode] = false;
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
