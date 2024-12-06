package Entities.Enemies;

import Entities.Player;
import Handlers.ImageHandler;
import World.Tile;

import java.util.Random;

import java.awt.*;

public class InstantKill extends Enemy {

    Random rand = new Random();
    double upBound, downBound, leftBound, rightBound;

    int movesPerTile;
    int direction;
    boolean setOnDirection;
    int timesMovedInDirection;

    public InstantKill(int health, int speed, int width, int height, String name, int worldX, int worldY, int xOffset, int yOffset, int hitBoxWidth, int hitBoxHeight, Player player, boolean isFollowing) {
        super(health, speed, width, height, name, worldX, worldY, xOffset, yOffset, hitBoxWidth, hitBoxHeight, player, isFollowing);

        setOnDirection = false;
        timesMovedInDirection = 0;
        direction = 0;

        movesPerTile = speed;
        setSpeed((double) Tile.tileSize / movesPerTile);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(ImageHandler.loadImage("src/Assets/Entities/Enemies/Bunnies/ghostBunny.png"), (int)this.screenX, (int)this.screenY, this.getWidth(), this.getHeight(), null);
        hitbox.draw(g2);
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

    public void setBounds(double upBound, double downBound, double leftBound, double rightBound) {
        int randomY = rand.nextInt(1, 24);
        int randomX = rand.nextInt(1, 24);

        this.worldX = Tile.tileSize * randomX;
        this.worldY = Tile.tileSize * randomY;

        this.upBound = upBound * Tile.tileSize;
        this.downBound = downBound * Tile.tileSize;
        this.leftBound = leftBound * Tile.tileSize;
        this.rightBound = rightBound * Tile.tileSize;
    }

}
