package Entities;
import Handlers.CollisionHandler;
import Handlers.ImageHandler;
import Handlers.KeyHandler;
import System.Main;
import System.GamePanel;
import World.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    Boolean interact;
    Boolean attack;
    public KeyHandler keyHandler;
    BufferedImage sprites = ImageHandler.loadImage("src/Assets/Entities/Players/Skeleton/Sprites.png");
    public int animationState = 0;
    int column1 = 7, column2 = 39, column3 = 71, column4 = 103, row1 = 7, row2 = 39, row3 = 71, row4 = 103, row5 = 135, row6 = 167, row7 = 199, row8 = 231, row9 = 263, row10 = 295, row11 = 327, row12 = 359, row13 = 391, row14 = 423, row15 = 455;
    int updateFrames = 12;
    Color transparent = new Color(0,0,0,0);
    CollisionHandler collisionHandler = new CollisionHandler();
    Tile[][] tiles;

    public Player(int health, double speed, int width, int height, String name, double worldX, double worldY, int xOffset, int yOffset, int hitBoxWidth, int hitBoxHeight, KeyHandler keyHandler) {
        super(health, speed, width, height, name, worldX, worldY, xOffset, yOffset, hitBoxWidth, hitBoxHeight);

        setScreenPosition();

        this.keyHandler = keyHandler;

    }

    private void setTileSet(Tile[][] tiles) {
        this.tiles = tiles;
    }

    void setScreenPosition() {
        this.screenX = (int) (GamePanel.screenWidth / 2) - (double) this.getWidth() / 2;
        this.screenY = (int) (GamePanel.screenHeight / 2) - (double) this.getHeight() / 2;
    }

    boolean checkMoving() {
        if (keyHandler.leftPress || keyHandler.downPress || keyHandler.upPress || keyHandler.rightPress) {
            return true;
        } else {
            return false;
        }
    }

    public void updateWorldValues(int worldX, int worldY) {
        this.worldX = worldX;
        this.worldY = worldY;
    }

    public void update(Tile[][] baseLayerTiles) {
        this.tiles = baseLayerTiles;
        updateEntityPosition();
        if (!attacking) move();
        hitbox.update(this);
        updateFrames();
    }

    private void updateFrames() {
        if (updateFrames == 0) {
            if (animationState < 3) {
                animationState++;
            } else {
                animationState = 0;
            }
            updateFrames = 12;
        } else {
            updateFrames--;
        }
    }

    private void move() {
        if (keyHandler.upPress) {
            direction = 'u';
            if (!collisionHandler.playerWithTileCollision(this, tiles)) this.worldY -= getSpeed();

        } else if (keyHandler.downPress) {
            direction = 'd';
            if (!collisionHandler.playerWithTileCollision(this, tiles)) worldY += getSpeed();
        }
        if (keyHandler.leftPress) {
            direction = 'l';
            if (!collisionHandler.playerWithTileCollision(this, tiles)) worldX -= getSpeed();

        } else if (keyHandler.rightPress) {
            direction = 'r';
            if (!collisionHandler.playerWithTileCollision(this, tiles)) worldX += getSpeed();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        drawHealth(g2);

        if (!attacking) {
            if (direction == 'd') {
                if (!checkMoving()) {
                    switch (animationState) {
                        case 0:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column1, row1, column1 + Tile.normalTileSize, row1 + Tile.normalTileSize, transparent, null);
                            break;
                        case 1:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column2, row1, column2 + Tile.normalTileSize, row1 + Tile.normalTileSize, transparent, null);
                            break;
                        case 2:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column3, row1, column3 + Tile.normalTileSize, row1 + Tile.normalTileSize, transparent, null);
                            break;
                        case 3:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column4, row1, column4 + Tile.normalTileSize, row1 + Tile.normalTileSize, transparent, null);
                            break;
                    }
                } else {
                    switch (animationState) {
                        case 0:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column1, row4, column1 + Tile.normalTileSize, row4 + Tile.normalTileSize, transparent, null);
                            break;
                        case 1:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column2, row4, column2 + Tile.normalTileSize, row4 + Tile.normalTileSize, transparent, null);
                            break;
                        case 2:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column3, row4, column3 + Tile.normalTileSize, row4 + Tile.normalTileSize, transparent, null);
                            break;
                        case 3:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column4, row4, column4 + Tile.normalTileSize, row4 + Tile.normalTileSize, transparent, null);
                            break;
                    }
                }
            } else if (direction == 'r') {
                if (!checkMoving()) {
                    switch (animationState) {
                        case 0:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column1, row2, column1 + Tile.normalTileSize, row2 + Tile.normalTileSize, transparent, null);
                            break;
                        case 1:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column2, row2, column2 + Tile.normalTileSize, row2 + Tile.normalTileSize, transparent, null);
                            break;
                        case 2:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column3, row2, column3 + Tile.normalTileSize, row2 + Tile.normalTileSize, transparent, null);
                            break;
                        case 3:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column4, row2, column4 + Tile.normalTileSize, row2 + Tile.normalTileSize, transparent, null);
                            break;
                    }
                } else {
                    switch (animationState) {
                        case 0:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column1, row5, column1 + Tile.normalTileSize, row5 + Tile.normalTileSize, transparent, null);
                            break;
                        case 1:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column2, row5, column2 + Tile.normalTileSize, row5 + Tile.normalTileSize, transparent, null);
                            break;
                        case 2:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column3, row5, column3 + Tile.normalTileSize, row5 + Tile.normalTileSize, transparent, null);
                            break;
                        case 3:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column4, row5, column4 + Tile.normalTileSize, row5 + Tile.normalTileSize, transparent, null);
                            break;
                    }
                }
            } else if (direction == 'u') {
                if (!checkMoving()) {
                    switch (animationState) {
                        case 0:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column1, row3, column1 + Tile.normalTileSize, row3 + Tile.normalTileSize, transparent, null);
                            break;
                        case 1:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column2, row3, column2 + Tile.normalTileSize, row3 + Tile.normalTileSize, transparent, null);
                            break;
                        case 2:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column3, row3, column3 + Tile.normalTileSize, row3 + Tile.normalTileSize, transparent, null);
                            break;
                        case 3:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column4, row3, column4 + Tile.normalTileSize, row3 + Tile.normalTileSize, transparent, null);
                            break;
                    }
                } else {
                    switch (animationState) {
                        case 0:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column1, row6, column1 + Tile.normalTileSize, row6 + Tile.normalTileSize, transparent, null);
                            break;
                        case 1:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column2, row6, column2 + Tile.normalTileSize, row6 + Tile.normalTileSize, transparent, null);
                            break;
                        case 2:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column3, row6, column3 + Tile.normalTileSize, row6 + Tile.normalTileSize, transparent, null);
                            break;
                        case 3:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column4, row6, column4 + Tile.normalTileSize, row6 + Tile.normalTileSize, transparent, null);
                            break;
                    }
                }
            } else {
                if (!checkMoving()) {
                    switch (animationState) {
                        case 0:
                            g2.drawImage(sprites, (int) screenX + getWidth(), (int) screenY, (int) screenX, (int) screenY + this.getHeight(), column1, row2, column1 + Tile.normalTileSize, row2 + Tile.normalTileSize, transparent, null);
                            break;
                        case 1:
                            g2.drawImage(sprites, (int) screenX + getWidth(), (int) screenY, (int) screenX, (int) screenY + this.getHeight(), column2, row2, column2 + Tile.normalTileSize, row2 + Tile.normalTileSize, transparent, null);
                            break;
                        case 2:
                            g2.drawImage(sprites, (int) screenX + getWidth(), (int) screenY, (int) screenX, (int) screenY + this.getHeight(), column3, row2, column3 + Tile.normalTileSize, row2 + Tile.normalTileSize, transparent, null);
                            break;
                        case 3:
                            g2.drawImage(sprites, (int) screenX + getWidth(), (int) screenY, (int) screenX, (int) screenY + this.getHeight(), column4, row2, column4 + Tile.normalTileSize, row2 + Tile.normalTileSize, transparent, null);
                            break;
                    }
                } else {
                    switch (animationState) {
                        case 0:
                            g2.drawImage(sprites, (int) screenX + getWidth(), (int) screenY, (int) screenX, (int) screenY + this.getHeight(), column1, row5, column1 + Tile.normalTileSize, row5 + Tile.normalTileSize, transparent, null);
                            break;
                        case 1:
                            g2.drawImage(sprites, (int) screenX + getWidth(), (int) screenY, (int) screenX, (int) screenY + this.getHeight(), column2, row5, column2 + Tile.normalTileSize, row5 + Tile.normalTileSize, transparent, null);
                            break;
                        case 2:
                            g2.drawImage(sprites, (int) screenX + getWidth(), (int) screenY, (int) screenX, (int) screenY + this.getHeight(), column3, row5, column3 + Tile.normalTileSize, row5 + Tile.normalTileSize, transparent, null);
                            break;
                        case 3:
                            g2.drawImage(sprites, (int) screenX + getWidth(), (int) screenY, (int) screenX, (int) screenY + this.getHeight(), column4, row5, column4 + Tile.normalTileSize, row5 + Tile.normalTileSize, transparent, null);
                            break;
                    }
                }
            }
        } else {
            if (direction == 'd') {
                    switch (animationState) {
                        case 0:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column1, row13, column1 + Tile.normalTileSize, row13 + Tile.normalTileSize, transparent, null);
                            break;
                        case 1, 2:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column2, row13, column2 + Tile.normalTileSize, row13 + Tile.normalTileSize, transparent, null);
                            break;
                        case 3:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column3, row13, column3 + Tile.normalTileSize, row13 + Tile.normalTileSize, transparent, null);
                            break;
                    }
            } else if (direction == 'r') {
                    switch (animationState) {
                        case 0:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column1, row14, column1 + Tile.normalTileSize, row14 + Tile.normalTileSize, transparent, null);
                            break;
                        case 1, 2:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column2, row14, column2 + Tile.normalTileSize, row14 + Tile.normalTileSize, transparent, null);
                            break;
                        case 3:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column3, row14, column3 + Tile.normalTileSize, row14 + Tile.normalTileSize, transparent, null);
                            break;
                    }
            } else if (direction == 'u') {
                    switch (animationState) {
                        case 0:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column1, row15, column1 + Tile.normalTileSize, row15 + Tile.normalTileSize, transparent, null);
                            break;
                        case 1, 2:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column2, row15, column2 + Tile.normalTileSize, row15 + Tile.normalTileSize, transparent, null);
                            break;
                        case 3:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column3, row15, column3 + Tile.normalTileSize, row15 + Tile.normalTileSize, transparent, null);
                            break;
                    }
            } else {
                    switch (animationState) {
                        case 0:
                            g2.drawImage(sprites, (int) screenX + getWidth(), (int) screenY, (int) screenX, (int) screenY + this.getHeight(), column1, row14, column1 + Tile.normalTileSize, row14 + Tile.normalTileSize, transparent, null);
                            break;
                        case 1, 2:
                            g2.drawImage(sprites, (int) screenX + getWidth(), (int) screenY, (int) screenX, (int) screenY + this.getHeight(), column2, row14, column2 + Tile.normalTileSize, row14 + Tile.normalTileSize, transparent, null);
                            break;
                        case 3:
                            g2.drawImage(sprites, (int) screenX + getWidth(), (int) screenY, (int) screenX, (int) screenY + this.getHeight(), column3, row14, column3 + Tile.normalTileSize, row14 + Tile.normalTileSize, transparent, null);
                            break;
                    }
            }
        }
    }
}
