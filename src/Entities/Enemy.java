package Entities;

import Pathfinding.APathfinding;
import Pathfinding.Node;
import World.Tile;
import System.GamePanel;

import java.awt.*;
import java.util.ArrayList;

public class Enemy extends Entity {
    private int vision;
    public boolean onPath;
    Player player;
    APathfinding pathFinder;
    Tile[][] tileset;

    public Enemy(int health, int speed, int width, int height, String name, int worldX, int worldY, int xOffset, int yOffset, int hitBoxWidth, int hitBoxHeight, Player player, Tile[][] tileset) {
        super(health, speed, width, height, name, worldX, worldY, xOffset, yOffset, hitBoxWidth, hitBoxHeight);
        this.player = player;
        this.tileset = tileset;

        pathFinder = new APathfinding(tileset);

        setScreenPosition();
        this.onPath = true;
    }

    @Override
    public void update() {
        setScreenPosition();
        move();
    }

    public void move() {

        if (onPath) {
            int goalRow = (int) (player.entityTop/ Tile.tileSize); //top row of the player
            int bottomRow = (int)player.entityBottom/Tile.tileSize;
            int goalCol = (int) (player.entityLeft/Tile.tileSize); //left row of the player
            int rightCol = (int)player.entityRight/Tile.tileSize;

            searchPath(goalRow, goalCol);

        } //else: different random actions if the player is not in enemy vision

        /*
		IF player is within enemy vision:
			Use A* Algorithm to follow player
         */
    }

    void attack() {
        /*
        IF player is within enemy vision:
			DISPLAY attack image
            Create NEW enemyAttack(worldX, worldY)
         */
    }

    void setScreenPosition() {
        screenX = worldX - player.worldX + player.screenX;
        screenY = worldY - player.worldY + player.screenY;
    }

    public void searchPath(int goalRow, int goalCol) {
        int startRow = (int) (this.entityTop/Tile.tileSize); //top row of the enemy
        int startCol = (int) (player.entityLeft/Tile.tileSize); //left row of the enemy

        pathFinder.setNodes(tileset[startRow][startCol], tileset[goalRow][goalCol]);

        if (pathFinder.search()) {
            System.out.print("found path!");

            ArrayList<Node> path = pathFinder.shortestPath;

            double nextCol = path.get(0).col;
            double nextRow = path.get(0).row;

            double nextWorldX = nextCol * Tile.tileSize;
            double nextWorldY = nextRow * Tile.tileSize;

            if (worldX > nextWorldX) worldX -= getSpeed();
            else if (worldX < nextWorldX) worldX += getSpeed();
            else if (worldY > nextWorldY) worldY -= getSpeed();
            else if (worldY < nextWorldY) worldY += getSpeed();


            if (nextRow == goalRow && nextCol == goalCol) onPath = false;
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
