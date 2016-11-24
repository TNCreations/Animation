package gdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import java.util.*;

public class GdxGame extends ApplicationAdapter implements InputProcessor {

    Random ranGen = new Random(1);
    SpriteBatch batch;
    Sprite sprVlad;
    ShapeRenderer shapeRenderer;
    Texture txSheet, txBackground, txTemp, txOne, txHouse, txHammer;
    Animation araniVlad[];
    TextureRegion trTemp, trHouse; // a single temporary texture region
    int fW, fH, fSx, fSy; // height and width of SpriteSheet image - and the starting upper coordinates on the Sprite Sheet
    int nFrame, nPos, nBar = 0, nBarWidth = 0;
    int nX = 100, nY = 100, nXHouse = 0, nYHouse = 0;
    boolean[] arbKeys = new boolean[512];
    boolean isPlaced = false;
    Timer timer = new Timer();

    @Override
    public void create() {
        Gdx.input.setInputProcessor((this));
        nFrame = 0;
        nPos = 0; // the position in the SpriteSheet - 0 to 7
        araniVlad = new Animation[8];
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        txSheet = new Texture("Vlad.png");
        txHouse = new Texture("house.png");
        txBackground = new Texture("background.png");
        txHammer = new Texture("hammer.png");
        fW = txSheet.getWidth() / 8;
        fH = txSheet.getHeight() / 8;
        //System.out.println(fW + " " + fH);
        for (int i = 0; i < 8; i++) {
            Sprite[] arSprVlad = new Sprite[8];
            for (int j = 0; j < 8; j++) {
                fSx = j * fW;
                fSy = i * fH;
                sprVlad = new Sprite(txSheet, fSx, fSy, fW, fH);
                arSprVlad[j] = new Sprite(sprVlad);
            }
            araniVlad[i] = new Animation(5.2f, arSprVlad);

        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        nFrame++;
        trTemp = araniVlad[nPos].getKeyFrame(nFrame, true);
        batch.begin();
        batch.draw(txBackground, 0, 0);
        batch.draw(trTemp, nX, nY);
        batch.draw(txHouse, nXHouse, nYHouse, 100, 100);
        if (nX < -30) {
            nX = 630;
        } else if (nX > 630) {
            nX = -30;
        }
        if (nY < -30) {
            nY = 480;
        } else if (nY > 480) {
            nY = -30;
        }
        if (isPlaced) {
            if (Gdx.input.getX() >= nXHouse && Gdx.input.getX() <= nXHouse + txHouse.getWidth() + 36
                    && Gdx.graphics.getHeight() + 36 - Gdx.input.getY() >= nYHouse
                    
                    && Gdx.graphics.getHeight() + 36 - Gdx.input.getY() <= nYHouse + txHouse.getHeight() + 36) {

                batch.draw(txHammer, Gdx.input.getX() - 32, Gdx.graphics.getHeight() - 32 - Gdx.input.getY());
            }
        }
        if (arbKeys[Input.Keys.UP]) {
            nPos = 1;
            nY += 1;
            if (arbKeys[Input.Keys.LEFT]) {
                nPos = 3;
                nX -= 1;
            } else if (arbKeys[Input.Keys.RIGHT]) {
                nPos = 2;
                nX += 1;
            }
        } else if (arbKeys[Input.Keys.DOWN]) {
            nPos = 4;
            nY -= 1;
            if (arbKeys[Input.Keys.LEFT]) {
                nPos = 6;
                nX -= 1;
            } else if (arbKeys[Input.Keys.RIGHT]) {
                nPos = 5;
                nX += 1;
            }
        } else if (arbKeys[Input.Keys.LEFT]) {
            nPos = 7;
            nX -= 1;
        } else if (arbKeys[Input.Keys.RIGHT]) {
            nPos = 0;
            nX += 1;
        }

        batch.end();
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.rect(nXHouse, nYHouse - 10, nBarWidth, 10);
        if (nBar >= 10) {
            txHouse = new Texture("house2.png");
            nBar = 0;
            nBarWidth = 0;
            shapeRenderer.setColor(ranGen.nextInt(), ranGen.nextInt(), ranGen.nextInt(), ranGen.nextInt());
        }
        shapeRenderer.end();
    }

    @Override
    public boolean keyDown(int i) {
        arbKeys[i] = true;
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        arbKeys[i] = false;
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        isPlaced = true;

        if (Gdx.input.getX() >= nXHouse && Gdx.input.getX() <= nXHouse + txHouse.getWidth()
                && Gdx.graphics.getHeight() - Gdx.input.getY() >= nYHouse
                && Gdx.graphics.getHeight() - Gdx.input.getY() <= nYHouse + txHouse.getHeight()) {

            nBar++;
            nBarWidth += 10;
        }

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
        if (!isPlaced) {
            nXHouse = Gdx.input.getX() - 32; //Gdx.graphics.getHeight() -32 - Gdx.input.getY()
            nYHouse = Gdx.graphics.getHeight() - 32 - Gdx.input.getY();
        }
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
