package Entities.Enemies;

import Entities.Players.Player;
import System.Main;
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

    public boolean active = true;
    // Variables for drawing
    private int updateFrames = 7;

    BufferedImage idleSprites = ImageHandler.loadImage("Assets/Entities/Enemies/Medieval Manuscripts/ElderSnail_idle.png");
    int spriteW = 64/4, spriteH = 64/4; // Sizes for the vampire sprites
    int maxCol;
    int currentCol = 0, currentRow = 0;


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
        this.tileset = tileset;
        pathFinder = new APathfinding(tileset);
        this.onPath = true;

        this.worldX = entityToFollow.worldX - Tile.tileSize;
        this.worldY = entityToFollow.worldY - Tile.tileSize;
    }

    public void update(Tile[][] tileset) {
        if(active) {
            pathFinder = new APathfinding(tileset);
            this.tileset = tileset;
            updateEntityPosition();
            setScreenPosition();
            hitbox.update(this);
            move();
        }
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

            getNewPosition(nextWorldX, nextWorldY);

        } else {
            moveTowardPlayer();
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
        if(active) {
            if (direction == 'd') {
                currentRow = 0;
            } else if (direction == 'l') {
                currentRow = 1;
            } else if (direction == 'r') {
                currentRow = 2;
            } else {
                currentRow = 3;
            }

            currentCol = animationState;
            if (currentCol > maxCol) currentCol = 0;

            g2.drawImage(idleSprites, (int)this.screenX, (int)this.screenY, (int)this.screenX + this.getWidth(), (int)this.screenY + this.getHeight(),
                    currentCol * spriteW, currentRow * spriteH, (currentCol + 1) * spriteW, (currentRow + 1) * spriteW,
                    null);

            updateFrames();
        }
    }

}
