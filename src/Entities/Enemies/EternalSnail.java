package Entities.Enemies;

import Entities.Players.Player;
import Handlers.ImageHandler;
import Pathfinding.APathfinding;
import Pathfinding.Node;
import World.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class EternalSnail extends Enemy{

    // Variables used for pathfinding
    public boolean onPath;
    APathfinding pathFinder;
    Tile[][] tileset;

    // Variables for drawing
    private int updateFrames = 7;
    BufferedImage snail;

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
    public EternalSnail(int health, double speed, int width, int height, String name, int worldX, int worldY, int xOffset, int yOffset, int hitBoxWidth, int hitBoxHeight, Player player, Tile[][] tileset, boolean isFollowing) {
        super(health, speed, width, height, name, worldX, worldY, xOffset, yOffset, hitBoxWidth, hitBoxHeight, player, isFollowing);
        loadSnail();
        this.tileset = tileset;
        pathFinder = new APathfinding(tileset);
        this.onPath = true;

        this.worldX = entityToFollow.worldX - Tile.tileSize;
        this.worldY = entityToFollow.worldY - Tile.tileSize;
    }

    void loadSnail() {
        snail = ImageHandler.loadImage("Assets/Entities/Enemies/Snail/snail1.png");
    }

    public void update(Tile[][] tileset) {
        pathFinder = new APathfinding(tileset);
        this.tileset = tileset;
        updateEntityPosition();
        setScreenPosition();
        hitbox.update(this);
        move();
    }


    /**
     * Moves enemy
     */
    public void move() {

        // If enemy is going to follow player
        if (onPath) {
            int goalRow = (int) (entityToFollow.entityTop/ Tile.tileSize); //top row of the player
            int goalCol = (int) (entityToFollow.entityLeft/Tile.tileSize); //left row of the player

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
            if (animationState >= 3) {
                animationState = 0;
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
        g2.drawImage(snail, (int)this.screenX, (int)this.screenY, this.getWidth(), this.getHeight(), null);
        updateFrames();
    }

}
