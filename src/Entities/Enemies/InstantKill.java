package Entities.Enemies;

import Handlers.LevelHandler;
import System.Main;
import Entities.Players.Player;
import Handlers.ImageHandler;
import World.Tile;

import java.awt.image.BufferedImage;
import java.util.Random;

import java.awt.*;

public class InstantKill extends Enemy {

    Random rand = new Random();
    double upBound, downBound, leftBound, rightBound;

    BufferedImage pancakeBunnySprite = ImageHandler.loadImage("Assets/Entities/Enemies/Bunnies/pancakeBunny.png");
    final int MAX_ROWS = 4;
    final int MAX_COLS = 3;
    int spriteW = 1022/3;
    int spriteH = 1154/4;
    int currentRow = 0;
    int currentCol = 0;
    int updateFrames = 12;

    int movesPerTile;
    int direction;
    boolean setOnDirection;
    int timesMovedInDirection;

    public boolean active = true;

    public InstantKill(int health, int speed, int width, int height, String name, int worldX, int worldY, int xOffset, int yOffset, int hitBoxWidth, int hitBoxHeight, Player player, boolean isFollowing) {
        super(health, speed, width, height, name, worldX, worldY, xOffset, yOffset, hitBoxWidth, hitBoxHeight, player, isFollowing);

        setOnDirection = false;
        timesMovedInDirection = 0;
        direction = 0;

        movesPerTile = speed;
        setSpeed((double) Tile.tileSize / movesPerTile);
    }

    public void update(LevelHandler levelHandler) {
        if(levelHandler.getCurrentLevel().levelNum == 5) {
            this.active = false;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        if(active) {
            updateRowsAndCols();
            g2.drawImage(pancakeBunnySprite,
                    (int)this.screenX, (int)this.screenY, (int)this.screenX + this.getWidth(), (int)this.screenY + this.getHeight(),  //destination
                    currentCol * spriteW, currentRow * spriteH, (currentCol+1) * spriteW, (currentRow+1) * spriteH,
                    null);
        }
    }

    /**
     * Used for updating animation
     */
    public boolean updateFrames() {
        if (updateFrames == 0) {
            updateFrames = 20;
            return true;
        } else {
            updateFrames--;
            return false;
        }
    }

    public void updateRowsAndCols() {
        if (updateFrames()) {
            currentCol++;
            if (currentCol >= MAX_COLS) currentCol = 0;
            currentRow++;
            if (currentRow >= MAX_ROWS) currentRow = 0;
        }
    }

    @Override
    public void move() {

        updateEntityPosition();

        if (rand.nextInt(50) > 10) return; // Don't move all the time

        if (timesMovedInDirection < movesPerTile) timesMovedInDirection++; // Continue moving in the same direction
        else {
            timesMovedInDirection = 0;
            direction = rand.nextInt(4); // Get a new direction
        }

        if (direction == 0) {
            double newLeft = (this.worldX - this.getSpeed());
            if (newLeft > leftBound) {
                this.worldX -= this.getSpeed(); // Left
            }
        } else if (direction == 1) {
            double newRight = (this.worldX + this.getSpeed());
            if (newRight < rightBound) {
                this.worldX += this.getSpeed(); // Right
            }
        } else if (direction == 2) {
            double newUp = (this.worldY - this.getSpeed());
            if (newUp > upBound) {
                this.worldY -= this.getSpeed(); // Up
            }
        } else if (direction == 3) {
            double newDown = (this.worldY + this.getSpeed());
            if (newDown < downBound) {
                this.worldY += this.getSpeed(); // Down
            }
        }
    }

    @Override
    public void hitPlayer() {
        if(active) {
            Main.updateGameState(4);
        }
    }

    @Override
    public void isHit(double damage) {
        if(active) {
            Main.updateGameState(4);
        }
    }

    public void setBounds(double upBound, double downBound, double leftBound, double rightBound, int mapCols, int mapRows) {
        while (true) {
            int randomY = rand.nextInt(1, mapRows-1);
            int randomX = rand.nextInt(1, mapCols-1);

            this.worldX = Tile.tileSize * randomX;
            this.worldY = Tile.tileSize * randomY;

            // Check if bunny is far enough from the player
            if (Math.abs(this.entityToFollow.worldX - this.worldX) > Tile.tileSize*3 && Math.abs(this.entityToFollow.worldY - this.worldY) > Tile.tileSize*3) break;
        }


        this.upBound = upBound * Tile.tileSize;
        this.downBound = downBound * Tile.tileSize;
        this.leftBound = leftBound * Tile.tileSize;
        this.rightBound = rightBound * Tile.tileSize;
    }

}
