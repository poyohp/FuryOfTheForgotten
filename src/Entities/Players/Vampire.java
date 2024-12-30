package Entities.Players;

import Handlers.ImageHandler;
import Handlers.KeyHandler;
import World.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Vampire extends Player{

    boolean attack;
    BufferedImage movingSprites = ImageHandler.loadImage("Assets/Entities/Players/Vampire/Vampire_walk.png");
    BufferedImage idleSprites = ImageHandler.loadImage("Assets/Entities/Players/Vampire/Vampire_idle.png");
    BufferedImage attackSprites = ImageHandler.loadImage("Assets/Entities/Players/Vampire/Vampire_attack_hitbox.png");
    int spriteW1 = 64/4, spriteH1 = 96/4; // Sizes for the vampire sprites
    int spriteW2 = 192/4, spriteH2 = 224/4;
    int maxCol = 4;
    int currentRow = 0, currentCol = 0;


    void setCharacterState(){
        type = 'v';
        characterAttackFrames = 36;
        characterAttackCooldown = 30;
        attackFrames = characterAttackFrames;
        attackCooldown = characterAttackCooldown;
        maxAnimationState = 3;
    }

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
     * @param keyHandler   keyhandler to handle key presses
     */
    public Vampire(double health, double speed, int width, int height, String name, double worldX, double worldY, int xOffset, int yOffset, int hitBoxWidth, int hitBoxHeight, KeyHandler keyHandler) {
        super(health, speed, width, height, name, worldX, worldY, xOffset, yOffset, hitBoxWidth, hitBoxHeight, keyHandler);
        setCharacterState();
    }

    /**
     * Updates the position of the player
     * @param baseLayerTiles
     */
    public void update(Tile[][] baseLayerTiles) {
        checkHit();
        this.tiles = baseLayerTiles;
        updateEntityPosition();
        if (!attacking) move(); // If player is not attacking, they can move
        hitbox.update(this); // Update hitbox
        updateFrames();
        setHealth(healthHandler.currentHearts);
    }

    @Override
    public void draw(Graphics2D g2) {
        int xNeeded = (int)this.screenX;
        int yNeeded = (int)this.screenY;
        if (direction == 'd') {
            currentRow = 0;
            yNeeded = (int)(this.screenY + this.getHeight());
        } else if (direction == 'l') {
            currentRow = 1;
            xNeeded = (int)(this.screenX - this.getWidth());
        } else if (direction == 'r') {
            currentRow = 2;
            xNeeded = (int)(this.screenX + this.getWidth());
        } else {
            currentRow = 3;
            yNeeded = (int)(this.screenY - this.getHeight());
        }

        currentCol = animationState;
        if (currentCol > maxCol) currentCol = 0;
        if (checkMoving()) {
            g2.drawImage(movingSprites,
                    (int) this.screenX, (int) this.screenY - Tile.tileRatio * 8, (int) (this.screenX + this.getWidth()), (int) (this.screenY) + this.getHeight(),
                    currentCol * spriteW1, currentRow * spriteH1, (currentCol + 1) * spriteW1, (currentRow + 1) * spriteH1,
                    null);

        } else {
            g2.drawImage(idleSprites,
                    (int) this.screenX, (int) this.screenY - Tile.tileRatio * 8, (int) (this.screenX + this.getWidth()), (int) (this.screenY) + this.getHeight(),
                    currentCol * spriteW1, currentRow * spriteH1, (currentCol + 1) * spriteW1, (currentRow + 1) * spriteH1,
                    null);
        }

        if (attacking) {
            g2.drawImage(attackSprites,
                    xNeeded, yNeeded, xNeeded + Tile.tileSize, yNeeded + Tile.tileSize,
                    currentCol * spriteW2, currentRow * spriteH2, (currentCol + 1) * spriteW2, (currentRow + 1) * spriteH2,
                    null);
        }
    }


    @Override
    public void checkAttack() {

        // Checking for keypress
        if (keyHandler.attackPress && canAttack) {
            attacking = true;
            animationState = 0;
            canAttack = false;
            attack = true;
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

    @Override
    public boolean toCreateAttack() {
        if (keyHandler.attackPress && attack) {
            return true;
        } else {
            return false;
        }
    }



}
