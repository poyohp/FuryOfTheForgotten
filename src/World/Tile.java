package World;

import System.*;

import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;

public class Tile {
    public static final int startingTileSize = 16;
    private static final int numToMultiply = 5;
    public static final int tileMultipler = (int) (GamePanel.screenWidth/GamePanel.screenHeight) * numToMultiply;
    public static final int tileSize = startingTileSize*tileMultipler;
    public static final int normalTileSize = 16;

    private int value;
    private int row, col;
    private int imageRow, imageCol;
    private double worldXPos, worldYPos;
    private double screenXPos, screenYPos;

    public boolean walkable;

    Tile(int row, int col, int value, boolean walkable) {
        this.row = row;
        this.col = col;
        this.value = value - 1;

        setWorldXPos();
        setWorldYPos();

        getImageRowCol();

        this.walkable = walkable;
    }

    public void getImageRowCol() {
        imageRow = (value / 28);
        imageCol = (value % 28);
    }

    private void setWorldXPos() {
        worldXPos = col * tileSize;
    }

    private void setWorldYPos() {
        worldYPos = row * tileSize;
    }

    public double getScreenXPos() {return screenXPos;}

    public double getScreenYPos() {
        return screenYPos;
    }

    public int getValue() {
        return value;
    }

    public double getWorldXPos() {
        return worldXPos;
    }

    public double getWorldYPos() {
        return worldYPos;
    }

    public void setScreenXPos(int screenXPos) {
        this.screenXPos = screenXPos;
    }

    public void setScreenYPos(int screenYPos) {
        this.screenYPos = screenYPos;
    }

    public int getCol() { return this.col; }

    public int getRow() { return this.col; }


    public int getImageRow() { return imageRow; }

    public int getImageCol() { return imageCol; }
}
