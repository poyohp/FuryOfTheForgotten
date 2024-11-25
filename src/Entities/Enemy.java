package Entities;

import World.Tile;

import java.awt.*;

public class Enemy extends Entity {
    private int vision;
    public boolean onPath;
    Player player;

    Enemy(int health, int speed, int width, int height, String name, int worldX, int worldY, int xOffset, int yOffset, Player player) {
        super(health, speed, width, height, name, worldX, worldY, xOffset, yOffset);
        player = this.player;
    }

    @Override
    void update() {
        move();
    }

    void move() {

        if (onPath) {
            int topRow = (int)player.entityTop/ Tile.tileSize; //top row of the player
            int bottomRow = (int)player.entityBottom/Tile.tileSize;
            int leftCol = (int)player.entityLeft/Tile.tileSize; //left row of the player
            int rightCol = (int)player.entityRight/Tile.tileSize;

            searchPath(topRow, leftCol);

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

    public void searchPath(int goalCol, int goalRow) {
        int startCol = (int)this.entityTop/ Tile.tileSize; //top row of the enemy
        int startRow = (int)player.entityLeft/Tile.tileSize; //left row of the enemy
    }

    @Override
    void draw(Graphics2D g2) {
        /*
        DRAW enemy image
         */
    }
}
