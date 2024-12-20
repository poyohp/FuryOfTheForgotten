package Entities.Players;
import Entities.Entity;
import Handlers.CollisionHandler;
import Handlers.HUD.HealthHandler;
import Handlers.ImageHandler;
import Handlers.KeyHandler;
import System.Panels.GamePanel;
import World.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Player extends Entity {

    public final KeyHandler keyHandler;

    Boolean canAttack = true;
    public char type;
    public boolean inAbility = false;


    public int updateFrames;
    int attackFrames;
    int characterAttackFrames;
    int attackCooldown;
    int characterAttackCooldown;
    public int maxAnimationState;

    Color transparent = new Color(0,0,0,0);

    public HealthHandler healthHandler = new HealthHandler((int)this.getHealth());
    CollisionHandler collisionHandler = new CollisionHandler();
    public Tile[][] tiles;

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
     * @param keyHandler keyhandler to handle key presses
     */
    public Player(int health, double speed, int width, int height, String name, double worldX, double worldY, int xOffset, int yOffset, int hitBoxWidth, int hitBoxHeight, KeyHandler keyHandler) {
        super(health, speed, width, height, name, worldX, worldY, xOffset, yOffset, hitBoxWidth, hitBoxHeight);

        setScreenPosition();

        this.keyHandler = keyHandler;

    }


    /**
     * Sets player in the middle of the screen
     */
    @Override
    public void setScreenPosition() {
        this.screenX = (int) (GamePanel.screenWidth / 2) - (double) this.getWidth() / 2;
        this.screenY = (int) (GamePanel.screenHeight / 2) - (double) this.getHeight() / 2;
    }

    /**
     * Check for player movement
     * @return true if movement key is pressed, false if not
     */
    public boolean checkMoving() {
        if (keyHandler.leftPress || keyHandler.downPress || keyHandler.upPress || keyHandler.rightPress) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Updates world values
     * @param worldX new world x
     * @param worldY new world y
     */
    public void updateWorldValues(int worldX, int worldY) {
        this.worldX = worldX;
        this.worldY = worldY;
    }

    /**
     * Set cooldown of player attacks
     */
    public void setAttackCooldown() {
        if (!canAttack) {
            if (attackCooldown == 0) {
                canAttack = true;
                attackCooldown = characterAttackCooldown;
            } else {
                attackCooldown -= 1;
            }
        }
    }

    /**
     * Check for player attack
     */
    public void checkAttack() {

        // Checking for keypress
        if (keyHandler.attackPress && canAttack) {
            attacking = true;
            animationState = 0;
            canAttack = false;
        }

        // Setting attack to true
        if (attacking) {
            if (attackFrames == 0) {
                attackFrames = characterAttackFrames;
                attacking = false;
            } else {
                attackFrames--;
            }
        }
    }

    public boolean toCreateAttack() {
        if (attackFrames == 0){
            return true;
        } else {
            return false;
        }
    }


    /**
     * Updates the position of the player
     * @param baseLayerTiles
     */
    public void update(Tile[][] baseLayerTiles) {
        this.tiles = baseLayerTiles;
        updateEntityPosition();
        if (!attacking) move(); // If player is not attacking, they can move
        hitbox.update(this); // Update hitbox
        updateFrames();
    }

    /**
     * Used for updating animation
     */
    public void updateFrames() {
        if (updateFrames == 0) {
            if (animationState < maxAnimationState) {
                animationState++;
            } else {
                animationState = 0;
            }
            updateFrames = 12;
        } else {
            updateFrames--;
        }
    }

    /**
     * Moves player (unless collision with tiles is detected)
     */
    public void move() {
        if (keyHandler.upPress) {
            direction = 'u';
            if (!collisionHandler.playerWithTileCollision(this, tiles)) this.worldY -= getSpeed();

        } else if (keyHandler.downPress) {
            direction = 'd';
            if (!collisionHandler.playerWithTileCollision(this, tiles)) worldY += getSpeed();
        }
        if (keyHandler.leftPress) {
            direction = 'l';
            if (!collisionHandler.playerWithTileCollision(this, tiles)) worldX -= getSpeed();

        } else if (keyHandler.rightPress) {
            direction = 'r';
            if (!collisionHandler.playerWithTileCollision(this, tiles)) worldX += getSpeed();
        }
    }

    /**
     * Draws player
     * @param g2 Graphics2D object to draw on
     */
    public abstract void draw(Graphics2D g2);
}
