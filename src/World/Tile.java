package World;

import System.GamePanel;

public class Tile {
    public static final int startingTileSize = 16;
    private static final int numToMultiply = 3;
    public static final int tileMultipler = (int) (GamePanel.screenWidth/GamePanel.screenHeight * numToMultiply);
    public static final int tileSize = startingTileSize*tileMultipler;

    private int value;
    private int row, col;
    private int worldXPos, worldYPos;
    private int screenXPos, screenYPos;

    boolean isCollidable;

    Tile(int row, int col, int value) {
        this.row = row;
        this.col = col;
        this.value = value;

        setWorldXPos();
        setWorldYPos();
    }

    private void setWorldXPos() {
        worldXPos = col * tileSize;
    }

    private void setWorldYPos() {
        worldYPos = row * tileSize;
    }

    // CURRENT IMAGE
    // IMAGE LIST

    //ANIMATION METHOD
    //

    public int getScreenXPos() {
        return screenXPos;
    }

    public int getScreenYPos() {
        return screenYPos;
    }

    public int getValue() {
        return value;
    }

    public int getWorldXPos() {
        return worldXPos;
    }

    public int getWorldYPos() {
        return worldYPos;
    }

    public void setScreenXPos(int screenXPos) {
        this.screenXPos = screenXPos;
    }

    public void setScreenYPos(int screenYPos) {
        this.screenYPos = screenYPos;
    }
}
