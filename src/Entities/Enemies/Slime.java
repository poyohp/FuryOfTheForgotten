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
    int updateFrames = 7;
    BufferedImage slimes;
    BufferedImage slimesHit;
    BufferedImage slimesDead;

    int spriteW = 16, spriteH = 16;


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
    public Slime(int health, double speed, int width, int height, String name, int worldX, int worldY, int xOffset, int yOffset, int hitBoxWidth, int hitBoxHeight, Player player, Tile[][] tileset, boolean isFollowing) {
        super(health, speed, width, height, name, worldX, worldY, xOffset, yOffset, hitBoxWidth, hitBoxHeight, player, isFollowing);
        loadSlime();
        this.tileset = tileset;
        pathFinder = new APathfinding(tileset);
        this.onPath = true;

        hitPlayer = false;
    }

    void loadSlime() {
        slimes = ImageHandler.loadImage("Assets/Entities/Enemies/Bog Dwellers/Slime_move.png");
        slimesHit = ImageHandler.loadImage("Assets/Entities/Enemies/Bog Dwellers/Slime_hit.png");
        slimesDead = ImageHandler.loadImage("Assets/Entities/Enemies/Bog Dwellers/Slime_die.png");
    }

    /**
     * Moves enemy
     */
    public void move() {

        if(hitPlayer || isHit) {
            if(freezeTimer > 0) {
                freezeTimer--;
            } else {
                hitPlayer = false;
                isHit = false;
            }
        }



        // If enemy is going to follow player
        if (onPath && !hitPlayer) {
            int goalRow = (int) (entityToFollow.entityTop/ Tile.tileSize); //top row of the player
            int goalCol = (int) (entityToFollow.entityLeft/Tile.tileSize); //left row of the player

            searchPath(goalRow, goalCol);

        }
    }

    @Override
    public void hitPlayer() {
        hitPlayer = true;
        freezeTimer = (int) freezeTimerFrames;
    }

    /**
     * Inflict damage if not already hit
     * @param damage amount of damage to inflict
     */
    @Override
    public void isHit(double damage) {
        if(!isHit) {
            this.setHealth(this.getHealth() - damage);
            hitPlayer = !hitPlayer;
            isHit = true;
            freezeTimer = (int) freezeTimerFrames;
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

            if (path.size() > 10) return; // Follows when player is in range

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

    /**
     * Updating frames for animation
     */
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
        drawHealth(g2);

        int currentRow = getRow();
        int currentCol = animationState;

        int maxCol = 4;
        if (currentCol > maxCol) currentCol = 0;


        // Getting the right sprite sheet
        BufferedImage spriteSheet;

        if (isHit) {
            if (getHealth() <= 0) {
                spriteSheet = slimesDead;
                currentCol = 0;
            }
            else {
                if (freezeTimer < freezeTimerFramesHalfway) currentCol = 0;
                else currentCol = 1;
                spriteSheet = slimesHit;
            }
        } else spriteSheet = slimes;

        g2.drawImage(spriteSheet, (int)this.screenX, (int)this.screenY, (int)this.screenX + this.getWidth(), (int)this.screenY + this.getHeight(),
                currentCol * spriteW, currentRow * spriteH, (currentCol + 1) * spriteW, (currentRow + 1) * spriteW,
                null);

        updateFrames();
    }

    private int getRow() {
        int currentRow;
        if (direction == 'd') {
            currentRow = 0;
        } else if (direction == 'l') {
            currentRow = 1;
        } else if (direction == 'r') {
            currentRow = 2;
        } else {
            currentRow = 3;
        }
        return currentRow;
    }
}
