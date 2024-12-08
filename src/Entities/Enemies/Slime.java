package Entities.Enemies;

import Entities.Players.Player;
import Handlers.ImageHandler;
import Pathfinding.APathfinding;
import Pathfinding.Node;
import World.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Slime extends Enemy{

    // Variables used for pathfinding
    public boolean onPath;
    APathfinding pathFinder;
    Tile[][] tileset;


    // Variables for drawing
    private int animationState = 1, updateFrames = 7;
    private int column1 = 0, column2 = 16, column3 = 32, column4 = 48, row1 = 0, row2 = 16, row3 = 32, row4 = 48, width = 15, height = 15;
    BufferedImage slimes;

    /**
     * Enemy that follows player
     *
     * @param health       enemy health
     * @param speed        enemy speed
     * @param width        enemy width
     * @param height       enemy height
     * @param name         enemy name
     * @param worldX       world x position
     * @param worldY       world y position
     * @param xOffset      x offset for hitbox
     * @param yOffset      y offset for hitbox
     * @param hitBoxWidth  hitbox width
     * @param hitBoxHeight hitbox height
     * @param player       player to follow
     * @param tileset      tileset to use for pathfinding
     * @param isFollowing  whether enemy is following player or not
     */
    public Slime(int health, int speed, int width, int height, String name, int worldX, int worldY, int xOffset, int yOffset, int hitBoxWidth, int hitBoxHeight, Player player, Tile[][] tileset, boolean isFollowing) {
        super(health, speed, width, height, name, worldX, worldY, xOffset, yOffset, hitBoxWidth, hitBoxHeight, player, isFollowing);
        loadSlime();
        this.tileset = tileset;
        pathFinder = new APathfinding(tileset);
        this.onPath = true;
    }

    void loadSlime() {
        slimes = ImageHandler.loadImage("src/Assets/Entities/Enemies/Bog Dwellers/Slime_move.png");
    }

    /**
     * Moves enemy
     */
    public void move() {

        // If enemy is going to follow player
        if (onPath) {
            int goalRow = (int) (player.entityTop/ Tile.tileSize); //top row of the player
            int goalCol = (int) (player.entityLeft/Tile.tileSize); //left row of the player

            searchPath(goalRow, goalCol);

        } //else: different random actions if the player is not in enemy vision
    }

    /**
     * Searches for a path to the player
     * @param goalRow Player's row
     * @param goalCol Player's column
     */
    public void searchPath(int goalRow, int goalCol) {
        int startRow = (int) (this.entityTop/Tile.tileSize); //top row of the enemy
        int startCol = (int) (this.entityLeft/Tile.tileSize); //left row of the enemy

        pathFinder.setNodes(tileset[startRow][startCol], tileset[goalRow][goalCol]);

        // If a path is found, move towards the player
        if (pathFinder.findPath()) {
            ArrayList<Node> path = pathFinder.shortestPath; // List of tiles to go to

            // Next tile to go to
            double nextCol = path.get(0).col;
            double nextRow = path.get(0).row;

            // Next x and y position to go to (calculated with tile size)
            double nextWorldX = nextCol * Tile.tileSize;
            double nextWorldY = nextRow * Tile.tileSize;

            if (Math.abs(nextWorldX - worldX) > getSpeed()) { // Moving will not put the enemy into the middle of the wrong tile
                if (worldX > nextWorldX) {
                    direction = 'l'; // left
                    worldX -= getSpeed();
                }
                else if (worldX < nextWorldX) {
                    direction = 'r'; // right
                    worldX += getSpeed();
                }
            } else {
                worldX = nextWorldX; // Snap directly to next tile
            }

            if (Math.abs(nextWorldY - worldY) > getSpeed()) {
                if (worldY > nextWorldY) {
                    direction = 'u'; // up
                    worldY -= getSpeed();
                }
                else if (worldY < nextWorldY) {
                    direction = 'd'; // down
                    worldY += getSpeed();
                }
            } else {
                worldY = nextWorldY; // Snap to tile
            }
        }
    }

    public void updateFrames() {
        if (updateFrames <= 0) {
            if (animationState >= 4) {
                animationState = 1;
            } else {
                animationState++;
            }
            updateFrames = 7;
        } else {
            updateFrames--;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        drawHealth(g2);


        if (direction == 'd') {
            switch (animationState) {
                case 1:
                    g2.drawImage(slimes, (int) screenX, (int) screenY, (int) (screenX + getWidth()), (int) (screenY + getHeight()), column1, row1, column1 + width, row1 + height, null);
                    break;
                case 2:
                    g2.drawImage(slimes, (int) screenX, (int) screenY, (int) (screenX + getWidth()), (int) (screenY + getHeight()), column2, row1, column2 + width, row1 + height, null);
                    break;
                case 3:
                    g2.drawImage(slimes, (int) screenX, (int) screenY, (int) (screenX + getWidth()), (int) (screenY + getHeight()), column3, row1, column3 + width, row1 + height, null);
                    break;
                case 4:
                    g2.drawImage(slimes, (int) screenX, (int) screenY, (int) (screenX + getWidth()), (int) (screenY + getHeight()), column4, row1, column4 + width, row1 + height, null);
                    break;
            }
        } else if (direction == 'l') {
            switch (animationState) {
                case 1:
                    g2.drawImage(slimes, (int) screenX, (int) screenY, (int) (screenX + getWidth()), (int) (screenY + getHeight()), column1, row2, column1 + width, row2 + height, null);
                    break;
                case 2:
                    g2.drawImage(slimes, (int) screenX, (int) screenY, (int) (screenX + getWidth()), (int) (screenY + getHeight()), column2, row2, column2 + width, row2 + height, null);
                    break;
                case 3:
                    g2.drawImage(slimes, (int) screenX, (int) screenY, (int) (screenX + getWidth()), (int) (screenY + getHeight()), column3, row2, column3 + width, row2 + height, null);
                    break;
                case 4:
                    g2.drawImage(slimes, (int) screenX, (int) screenY, (int) (screenX + getWidth()), (int) (screenY + getHeight()), column4, row2, column4 + width, row2 + height, null);
                    break;
            }
        } else if (direction == 'r') {
            switch (animationState) {
                case 1:
                    g2.drawImage(slimes, (int) screenX, (int) screenY, (int) (screenX + getWidth()), (int) (screenY + getHeight()), column1, row3, column1 + width, row3 + height, null);
                    break;
                case 2:
                    g2.drawImage(slimes, (int) screenX, (int) screenY, (int) (screenX + getWidth()), (int) (screenY + getHeight()), column2, row3, column2 + width, row3 + height, null);
                    break;
                case 3:
                    g2.drawImage(slimes, (int) screenX, (int) screenY, (int) (screenX + getWidth()), (int) (screenY + getHeight()), column3, row3, column3 + width, row3 + height, null);
                    break;
                case 4:
                    g2.drawImage(slimes, (int) screenX, (int) screenY, (int) (screenX + getWidth()), (int) (screenY + getHeight()), column4, row3, column4 + width, row3 + height, null);
                    break;
            }
        } else if (direction == 'u') {
            switch (animationState) {
                case 1:
                    g2.drawImage(slimes, (int) screenX, (int) screenY, (int) (screenX + getWidth()), (int) (screenY + getHeight()), column1, row4, column1 + width, row4 + height, null);
                    break;
                case 2:
                    g2.drawImage(slimes, (int) screenX, (int) screenY, (int) (screenX + getWidth()), (int) (screenY + getHeight()), column2, row4, column2 + width, row4 + height, null);
                    break;
                case 3:
                    g2.drawImage(slimes, (int) screenX, (int) screenY, (int) (screenX + getWidth()), (int) (screenY + getHeight()), column3, row4, column3 + width, row4 + height, null);
                    break;
                case 4:
                    g2.drawImage(slimes, (int) screenX, (int) screenY, (int) (screenX + getWidth()), (int) (screenY + getHeight()), column4, row4, column4 + width, row4 + height, null);
                    break;
            }
        }



        updateFrames();
    }
}
