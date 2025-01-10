package Entities.Enemies;

import Entities.Players.Player;

import java.awt.*;

public class RoyalKnight extends Enemy{

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
     * @param isFollowing  whether enemy is following player or not
     */
    public RoyalKnight(double health, double speed, int width, int height, String name, int worldX, int worldY, int xOffset, int yOffset, int hitBoxWidth, int hitBoxHeight, Player player, boolean isFollowing) {
        super(health, speed, width, height, name, worldX, worldY, xOffset, yOffset, hitBoxWidth, hitBoxHeight, player, isFollowing);
    }

    @Override
    public void draw(Graphics2D g2) {

    }

    @Override
    public void move() {

    }

    @Override
    public void hitPlayer() {

    }

    @Override
    public void isHit(double damage) {

    }
    
}
