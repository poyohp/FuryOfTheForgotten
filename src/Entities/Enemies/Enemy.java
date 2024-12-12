package Entities.Enemies;

import Entities.Entity;
import Entities.Players.Player;

import java.awt.*;

public abstract class Enemy extends Entity {

    // Used for attacking
    int vision = 600;
    public double damage;
    public boolean isFollowing = false;
    public boolean attacking = true;

    Entity entityToFollow; // Player to reference

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
    public Enemy(int health, int speed, int width, int height, String name, int worldX, int worldY, int xOffset, int yOffset, int hitBoxWidth, int hitBoxHeight, Player player, boolean isFollowing) {
        super(health, speed, width, height, name, worldX, worldY, xOffset, yOffset, hitBoxWidth, hitBoxHeight);
        this.entityToFollow = player;
        this.isFollowing = isFollowing;

        damage = 0.5; // Damage for all enemies

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

}
