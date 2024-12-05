package Entities;

import java.awt.*;

public abstract class Entity {

    // Various entity attributes
    private double health;
    private double speed;
    private int width, height;
    private int defenseReduction;
    String name;

    // entity position
    public double worldX, worldY;
    public double screenX, screenY;


    public char direction;
    public boolean attacking = false;
    public int attackCooldown = 120;

    public double entityLeft, entityRight, entityTop, entityBottom;

    public Hitbox hitbox;

    /**
     * Entity that is a part of the game
     *
     * @param health       entity health
     * @param speed        entity speed
     * @param width        entity width
     * @param height       entity height
     * @param name         entity name
     * @param worldX       world x position
     * @param worldY       world y position
     * @param xOffset      x offset for hitbox
     * @param yOffset      y offset for hitbox
     * @param hitBoxWidth  hitbox width
     * @param hitBoxHeight hitbox height
     */
    public Entity(int health, double speed, int width, int height, String name, double worldX, double worldY, int xOffset, int yOffset, int hitBoxWidth, int hitBoxHeight) {
        this.health = health;
        this.speed = speed;
        this.width = width;
        this.height = height;

        this.name = name;
        this.direction = 'r';

        this.worldX = worldX;
        this.worldY = worldY;

        hitbox = new Hitbox((int) worldX, (int) worldY, (int) screenX, (int) screenY, hitBoxWidth, hitBoxHeight, xOffset, yOffset); // Create hitbox that corresponds to given values
        hitbox.update(this);

        // Variables that refer to the different sides of the entities
        entityLeft = hitbox.getWorldX();
        entityRight = entityLeft + (double) hitbox.getWidth();
        entityTop = hitbox.getWorldY();
        entityBottom = entityTop + (double) hitbox.getHeight();
    }

    public abstract void setScreenPosition();

    /**
     * Updates the hitbox values and corresponding entity values
     */
    public void updateEntityPosition() {
        hitbox.update(this);
        entityLeft = hitbox.getWorldX();
        entityRight = entityLeft + (double) hitbox.getWidth();
        entityTop = hitbox.getWorldY();
        entityBottom = entityTop + (double) hitbox.getHeight();
    }


    /**
     * Draws the health of an entity
     *
     * @param g2 Graphics2D object to draw health bar on
     */
    public void drawHealth(Graphics2D g2) {
        double fillPercentage = health / 100.0; // Health percentage remaining
        int barLength = (int) (fillPercentage * width); // Draw bar based on percentage remaining

        // Bar fill
        g2.setColor(Color.GREEN);
        g2.fillRect((int) screenX, (int) screenY - 40, barLength, 20);

        // Bar outline
        g2.setColor(Color.BLACK);
        g2.drawRect((int) screenX, (int) screenY - 40, width, 20);

    }

    /**
     * Gets health
     *
     * @return health
     */
    public double getHealth() {
        return health;
    }

    /**
     * Gets speed
     *
     * @return speed
     */
    public double getSpeed() {
        return speed;
    }

    public void setSpeed (double speed) {this.speed = speed;}


    /**
     * Gets width
     *
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets height
     *
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets health, making sure it does not go below zero
     *
     * @param newHealth sets health to newHealth
     */
    public void setHealth(double newHealth) {
        health = newHealth;
        if (newHealth < 0) health = 0;
    }
}