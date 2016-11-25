/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gdx.game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by hafiz on 11/21/2016.
 */

public class SpriteSheetAnimator {
    private Sprite TempSpriteSheet;
    private Texture TextureSheet;
    private Animation arAnimatioin[];
    private int fW, fH, fX, fY; // height and width of TempSpriteSheet image - and the starting upper coordinates on the Sprite Sheet
    private int nRows, nColumns, nFPR;
    private float fChangeRate;

    public SpriteSheetAnimator(Texture TextureSheet, int nRows, int nColumns, int nFPR, float fChangeRate) {
        this.TextureSheet = TextureSheet;
        this.nRows = nRows;
        this.nColumns = nColumns;
        this.nFPR = nFPR;// frmaes per row
        arAnimatioin = new Animation[nRows];
        this.fChangeRate = fChangeRate;
    }
    public Animation[] animate () {
        fW = TextureSheet.getWidth() / nColumns;
        fH = TextureSheet.getHeight() / nRows;
        for (int i = 0; i < nRows; i++) {
            Sprite[] arTempSprites = new Sprite[nFPR];
            for (int j = 0; j < nColumns; j++) {
                fX = j * fW;
                fY = i * fH;
                System.out.println(fX + " : " + fY);
                TempSpriteSheet = new Sprite(TextureSheet, fX, fY, fW, fH);
                arTempSprites[j] = new Sprite(TempSpriteSheet);
            }
            arAnimatioin[i] = new Animation(fChangeRate, arTempSprites);
        }
        return arAnimatioin;
    }
}
