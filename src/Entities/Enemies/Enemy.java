package Entities.Enemies;

import Entities.Entity;
import Entities.Players.Player;
import System.Panels.GamePanel;
import World.Tile;

import java.awt.*;

public abstract class Enemy extends Entity {

    // Used for attacking
    int vision = 600;
    public double damage;
    public double damageTaken;
    public boolean phase2 = false;

    //Used for if enemy is attacked
    public boolean isHit;

    //Used for player attack pause...
    public boolean hitPlayer;
    public int freezeTimer;
    private final double freezeTimerSeconds = 0.5;
    public final double freezeTimerFrames = (int) GamePanel.FPS*freezeTimerSeconds;
    public final double freezeTimerFramesHalfway = freezeTimerFrames/2.0;

    public Entity entityToFollow; // Player to reference

    /**
     * Enemy that follows player
     * @param health enemy health
     * @param speed enemy speed
     * @param width enemy width
     * @param height enemy height
     * @param name enemy name
     * @param worldX world x position
     * @param worldY world y position
     * @param xOffset x offset for hitbox
     * @param yOffset y offset for hitbox
     * @param hitBoxWidth hitbox width
     * @param hitBoxHeight hitbox height
     * @param player player to follow
     * @param isFollowing whether enemy is following player or not
     */
    public Enemy(double health, double speed, int width, int height, String name, int worldX, int worldY, int xOffset, int yOffset, int hitBoxWidth, int hitBoxHeight, Player player, boolean isFollowing) {
        super(health, speed, width, height, name, worldX, worldY, xOffset, yOffset, hitBoxWidth, hitBoxHeight);
        this.entityToFollow = player;

        freezeTimer = 0;
        hitPlayer = false;
        damage = 0.5; // Damage for all enemies

        isHit = false;

        setScreenPosition();
    }

    /**
     * Updates enemy position and position on screen, and updates hitbox as well.
     * Then, moves the enemy
     */
    public void update() {
        updateEntityPosition();
        setScreenPosition();
        hitbox.update(this);
        move();
    }

    /**
     * This method will be used later, to check whether player is in vision of the enemy
     * @return True if player is in vision, false if not
     */
    public boolean playerInVision() {
        if (Math.abs(entityToFollow.entityLeft - this.entityLeft) < vision && Math.abs(entityToFollow.entityTop - this.entityTop) < vision) {
            return true;
        }
        else return false;
    }

    /**
     * Determine the next tile position for the enemy to move to
     * @param nextWorldX the X position
     * @param nextWorldY the Y position
     */
    public void getNewPosition(double nextWorldX, double nextWorldY) {
        if (Math.abs(nextWorldX - worldX) > getSpeed()) { // Moving will not put the enemy into the middle of the wrong tile
            if (worldX > nextWorldX) {
                direction = 'l'; // left
                worldX -= getSpeed();
            } else if (worldX < nextWorldX) {
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
            } else if (worldY < nextWorldY) {
                direction = 'd'; // down
                worldY += getSpeed();
            }
        } else {
            worldY = nextWorldY; // Snap to tile
        }
    }

    /**
     * Move enemy based on player next position value
     */
    public void moveTowardPlayer () {
        if (worldX > entityToFollow.worldX) {
            direction = 'l'; // left
            worldX -= getSpeed();
        } else if (worldX < entityToFollow.worldX) {
            direction = 'r'; // right
            worldX += getSpeed();
        }
        if (worldY > entityToFollow.worldY) {
            direction = 'u'; // up
            worldY -= getSpeed();
        } else if (worldY < entityToFollow.worldY) {
            direction = 'd'; // down
            worldY += getSpeed();
        }
    }

    /**
     * Sets enemy's position on the screen
     */
    @Override
    public void setScreenPosition() {
        // gets player coordinates and offsets by the player's place on the screen
        screenX = worldX - entityToFollow.worldX + entityToFollow.screenX;
        screenY = worldY - entityToFollow.worldY + entityToFollow.screenY;
    }

    /**
     * Draws the enemy and its health bar
     * @param g2 Graphics2D object to draw on
     */
    public abstract void draw(Graphics2D g2);
    public abstract void move();
    public abstract void hitPlayer();
    public abstract void isHit(double damage);

}
