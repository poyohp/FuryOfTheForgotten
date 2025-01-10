package Entities.Players;
import Entities.Entity;
import Handlers.CollisionHandler;
import Handlers.HUD.HealthHandler;
import Handlers.ImageHandler;
import Handlers.KeyHandler;
import System.Panels.GamePanel;
import World.Level;
import World.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Player extends Entity {

    public final KeyHandler keyHandler;

    Boolean canAttack = true;
    public char type;
    public boolean inAbility = false;
    public int bloodBarState, bloodTimer;

    public boolean collisionWithChest;
    public int coinValue;

    public final int initNumHearts = 6;

    public int updateFrames;
    int attackFrames;
    int characterAttackFrames;
    int attackCooldown;
    int characterAttackCooldown;
    public int maxAnimationState;

    //Hit timers
    public boolean isHit;
    public double iFramesCounter;
    public final double iFramesTimerSeconds = 0.5;
    public final double iFramesTimerFrames = GamePanel.FPS*iFramesTimerSeconds;

    //EFFECT TIMERS
    public double origSpeed, boostedSpeed, boostedDamage;
    public boolean isSpeedBoost, isDamageBoost;
    public double speedBoostCounter, damageBoostCounter;
    public final double speedBoostTimerSeconds = 5, damageBoostTimerSeconds = 10;

    public final double speedBoostTimerFrames = GamePanel.FPS*speedBoostTimerSeconds;
    public final double damageBoostTimerFrames = GamePanel.FPS*damageBoostTimerSeconds;

    Color transparent = new Color(0,0,0,0);

    public HealthHandler healthHandler = new HealthHandler(initNumHearts);
    CollisionHandler collisionHandler = new CollisionHandler();
    public Tile[][] tiles;
    public Level currentLevel;

    /**
     * Enemy that follows player
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
    public Player(double health, double speed, int width, int height, String name, double worldX, double worldY, int xOffset, int yOffset, int hitBoxWidth, int hitBoxHeight, KeyHandler keyHandler) {
        super(health, speed, width, height, name, worldX, worldY, xOffset, yOffset, hitBoxWidth, hitBoxHeight);

        origSpeed = speed;
        boostedSpeed = speed+2;
        boostedDamage = 1;

        setScreenPosition();

        this.keyHandler = keyHandler;

        collisionWithChest = false;
        coinValue = 0;

        isHit = false;
        iFramesCounter = 0;

        isSpeedBoost = false;
        speedBoostCounter = 0;

    }

    //SPEED BOOST OBJECT
    public void updateSpeedBoost() {
        if(isSpeedBoost) {
            if(!(getSpeed() > boostedSpeed)) {
                this.setSpeed(boostedSpeed);
            }
            if(speedBoostCounter > 0) {
                speedBoostCounter--;
            } else {
                isSpeedBoost = false;
                this.setSpeed(origSpeed);
            }
        }
    }
    public void speedBoostUsed() {
        isSpeedBoost = true;
        speedBoostCounter = speedBoostTimerFrames;
    }

    //DAMAGE BOOST OBJECT
    public void updateDamageBoost() {
        if(isDamageBoost) {
            if(damageBoostCounter > 0) {
                damageBoostCounter--;
            } else {
                isDamageBoost = false;
            }
        }
    }
    public void damageBoostUsed() {
        isDamageBoost = true;
        damageBoostCounter = damageBoostTimerFrames;
    }

    public void isHit(double damage, boolean onlyHearts) {
        healthHandler.isHit(damage, onlyHearts);
        isHit = true;
        iFramesCounter = iFramesTimerFrames;
    }

    public void checkHit() {
        if(isHit) {
            if(iFramesCounter > 0) {
                iFramesCounter--;
            } else {
                isHit = false;
            }
        }

        setHealth(healthHandler.currentHearts);
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
    public void update(Tile[][] baseLayerTiles, Level level) {
        this.currentLevel = level;

        checkHit();
        updateSpeedBoost();
        updateDamageBoost();

        this.tiles = baseLayerTiles;
        updateEntityPosition();
        if (!attacking && !inAbility) move(); // If player is not attacking, they can move
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
        if(!keyHandler.toggleInventory) {
            if (keyHandler.upPress) {
                direction = 'u';
                if (!collisionHandler.playerWithTileCollision(this, tiles) && !collisionWithChest) worldY -= getSpeed();
            } else if (keyHandler.downPress) {
                direction = 'd';
                if (!collisionHandler.playerWithTileCollision(this, tiles) && !collisionWithChest) worldY += getSpeed();
            }
            if (keyHandler.leftPress) {
                direction = 'l';
                if (!collisionHandler.playerWithTileCollision(this, tiles) && !collisionWithChest) worldX -= getSpeed();

            } else if (keyHandler.rightPress) {
                direction = 'r';
                if (!collisionHandler.playerWithTileCollision(this, tiles) && !collisionWithChest) worldX += getSpeed();
            }
        }
    }

    /**
     * Draws player
     * @param g2 Graphics2D object to draw on
     */
    public abstract void draw(Graphics2D g2);
}
