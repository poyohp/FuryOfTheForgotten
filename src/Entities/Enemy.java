package Entities;

import Pathfinding.APathfinding;
import Pathfinding.Node;
import World.Tile;

import java.awt.*;
import java.util.ArrayList;

public class Enemy extends Entity {
    private int vision = 300;
    public int rangedVision = 0;
    public boolean isFollowing = false;
    public boolean onPath;
    Player player;
    APathfinding pathFinder;
    Tile[][] tileset;

    public Enemy(int health, int speed, int width, int height, String name, int worldX, int worldY, int xOffset, int yOffset, int hitBoxWidth, int hitBoxHeight, Player player, Tile[][] tileset, boolean isFollowing) {
        super(health, speed, width, height, name, worldX, worldY, xOffset, yOffset, hitBoxWidth, hitBoxHeight);
        this.player = player;
        this.tileset = tileset;
        this.isFollowing = isFollowing;

        pathFinder = new APathfinding(tileset);

        setScreenPosition();
        this.onPath = true;
    }

    public void update() {
        updateEntityPosition();
        setScreenPosition();
        hitbox.update(this);
        if (playerInVision() || isFollowing) move();
    }

    private boolean playerInVision() {
        if (Math.abs(player.entityLeft - this.entityLeft) < vision && Math.abs(player.entityTop - this.entityTop) < vision) {
            isFollowing = true;
            return true;
        }
        else return false;
    }

    private boolean notInRangedArea() {
        if (Math.abs(player.entityLeft - this.entityLeft) < rangedVision && Math.abs(player.entityTop - this.entityTop) < rangedVision) return true;
        else return false;
    }

    public void move() {

        if (onPath) {
            int goalRow = (int) (player.entityTop/ Tile.tileSize); //top row of the player
            int goalCol = (int) (player.entityLeft/Tile.tileSize); //left row of the player

            searchPath(goalRow, goalCol);

        } //else: different random actions if the player is not in enemy vision
    }

    void attack() {
        /*
        IF player is within enemy vision:
			DISPLAY attack image
            Create NEW enemyAttack(worldX, worldY)
         */
    }

    void setScreenPosition() {
        // gets player coordinates and offsets by the player's place on the screen
        screenX = worldX - player.worldX + player.screenX;
        screenY = worldY - player.worldY + player.screenY;
    }

    public void searchPath(int goalRow, int goalCol) {
        int startRow = (int) (this.entityTop/Tile.tileSize); //top row of the enemy
        int startCol = (int) (this.entityLeft/Tile.tileSize); //left row of the enemy

        pathFinder.setNodes(tileset[startRow][startCol], tileset[goalRow][goalCol]);

        if (pathFinder.search()) {
            ArrayList<Node> path = pathFinder.shortestPath;

            double nextCol = path.get(0).col;
            double nextRow = path.get(0).row;

            double nextWorldX = nextCol * Tile.tileSize;
            double nextWorldY = nextRow * Tile.tileSize;

            if (worldX > nextWorldX) {
                worldX -= getSpeed();
            }
            else if (worldX < nextWorldX) {
                worldX += getSpeed();
            }
            else if (worldY > nextWorldY) {
                worldY -= getSpeed();
            }
            else if (worldY < nextWorldY) {
                worldY += getSpeed();
            }
        }

    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.PINK);
        g2.fillRect((int) screenX, (int) screenY, this.getWidth(), this.getHeight());
        g2.setColor(Color.RED);
        g2.drawRect((int) screenX, (int) screenY, this.getWidth(), this.getHeight());
    }
}
