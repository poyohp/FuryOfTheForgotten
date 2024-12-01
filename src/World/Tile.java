package World;

import System.*;

public class Tile {
    public static final int normalTileSize = 16;
    private static final int numToMultiply = 5;
    public static final int tileMultipler = (int) (GamePanel.screenWidth/GamePanel.screenHeight) * numToMultiply;

    //Tile size is determined based on screen ratio
    public static final int tileSize = normalTileSize*tileMultipler;

    private int value, orgValue;
    private int row, col;
    private int imageRow, imageCol;
    private double worldXPos, worldYPos;
    private double screenXPos, screenYPos;

    public boolean walkable;

    /**
     * Constructor for the Tile class - sets positions and values
     * @param row Tile row
     * @param col Tile column
     * @param value Tile value (used for image)
     * @param walkable Whether the tile is walkable or not
     */
    Tile(int row, int col, int value, boolean walkable) {
        this.row = row;
        this.col = col;
        this.orgValue = value;
        this.value = value - 1;

        setWorldXPos();
        setWorldYPos();

        getImageRowCol();

        this.walkable = walkable;
    }

    /**
     * Get the row and column of the IMAGE (on the tileset) that corresponds to the value
     */
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

    public int getRow() { return this.row; }


    public int getImageRow() { return imageRow; }

    public int getImageCol() { return imageCol; }

    public int getOrgValue() {
        return orgValue;
    }
}
