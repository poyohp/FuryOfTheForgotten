package World;

public class Tile {
    public static final int startingTileSize = 16;
    public static final int tileMultipler = 4;
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

        setWorldX();
        setWorldY();
    }

    private void setWorldX() {
        worldXPos = col * tileSize;
    }

    private void setWorldY() {
        worldYPos = row * tileSize;
    }

    // CURRENT IMAGE
    // IMAGE LIST

    //ANIMATION METHOD

    // Getters and Setters
    public int getWorldXPos() {
        return worldXPos;
    }

    public void setWorldXPos(int worldXPos) {
        this.worldXPos = worldXPos;
    }

    public int getWorldYPos() {
        return worldYPos;
    }

    public void setWorldYPos(int worldYPos) {
        this.worldYPos = worldYPos;
    }

    public int getScreenXPos() {
        return screenXPos;
    }

    public void setScreenXPos(int screenXPos) {
        this.screenXPos = screenXPos;
    }

    public int getScreenYPos() {
        return screenYPos;
    }

    public void setScreenYPos(int screenYPos) {
        this.screenYPos = screenYPos;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
