package Entities.Enemies;

import Entities.Players.Player;
import Handlers.ImageHandler;
import Pathfinding.APathfinding;
import Pathfinding.Node;
import System.Panels.GamePanel;
import World.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Rabbit extends Enemy{

    // Variables used for pathfinding
    public boolean onPath;
    APathfinding pathFinder;
    Tile[][] tileset;

    // Variables for drawing
    private int updateFrames = 7;
    BufferedImage rabbitMove, rabbitHit, rabbitDead, rabbitIdle;

    boolean moving;

    boolean shotFired;

    int spriteW = 16, spriteH = 16;

    // For attacking and timing
    int counter;
    int numSeconds = 5;
    int numFrames = (int) GamePanel.FPS * numSeconds;


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
    public Rabbit(int health, double speed, int width, int height, String name, int worldX, int worldY, int xOffset, int yOffset, int hitBoxWidth, int hitBoxHeight, Player player, Tile[][] tileset, boolean isFollowing) {
        super(health, speed, width, height, name, worldX, worldY, xOffset, yOffset, hitBoxWidth, hitBoxHeight, player, isFollowing);
        loadRabbit();
        this.tileset = tileset;
        pathFinder = new APathfinding(tileset);
        this.onPath = true;

        attacking = false;
        counter = numFrames;
    }

    @Override
    public void update() {
        updateEntityPosition();
        setScreenPosition();
        hitbox.update(this);

        if (counter < 0) {
            attacking = true;
            counter = numFrames;
        }
        counter--;

        move();
    }

    /**
     * Loading images
     */
    void loadRabbit() {
        rabbitMove = ImageHandler.loadImage("Assets/Entities/Enemies/Medieval Manuscripts/RabbitSoldier_walk.png");
        rabbitHit = ImageHandler.loadImage("Assets/Entities/Enemies/Medieval Manuscripts/RabbitSoldier_hit.png");
        rabbitDead = ImageHandler.loadImage("Assets/Entities/Enemies/Medieval Manuscripts/RabbitSoldier_die.png");
        rabbitIdle = ImageHandler.loadImage("Assets/Entities/Enemies/Medieval Manuscripts/RabbitSoldier_idle.png");

    }

    /**
     * Moves enemy
     */
    public void move() {

        if(isHit) {
            if(freezeTimer > 0) {
                freezeTimer--;
            } else {
                isHit = false;
            }
        }

        // If enemy is going to follow player
        if (onPath) {
            int goalRow = (int) (entityToFollow.entityTop/ Tile.tileSize); //top row of the player
            int goalCol = (int) (entityToFollow.entityLeft/Tile.tileSize); //left row of the player

            searchPath(goalRow, goalCol);
        }
    }

    @Override
    public void hitPlayer() {
        //NO ACTION NEEDED
    }

    /**
     * Takes damage and freezes for a little bit if hit
     * @param damage amount of damage taken
     */
    @Override
    public void isHit(double damage) {
        if(!isHit) {
            this.setHealth(this.getHealth() - damage);
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
        moving = true;

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

            // Ensures that enemy does not move when it is too close or too far from player
            if (path.size() < 5 && startRow == goalRow || path.size() > 40) {
                moving = false;

                // Turn in direction of player, even if not moving toward them
                if (worldX > nextWorldX) direction = 'l'; // left
                else if (worldX < nextWorldX) direction = 'r'; // right
                return; // Does not move to player
            }

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
                spriteSheet = rabbitDead;
                currentCol = 0;
            }
            else {
                if (freezeTimer < freezeTimerFramesHalfway) currentCol = 0;
                else currentCol = 1;
                spriteSheet = rabbitHit;
            }
        } else {
            if (moving) spriteSheet = rabbitMove;
            else spriteSheet = rabbitIdle;
        }

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
