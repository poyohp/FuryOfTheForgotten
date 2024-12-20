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
    int spriteW = 64/4, spriteH = 96/4;
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
    public Vampire(int health, double speed, int width, int height, String name, double worldX, double worldY, int xOffset, int yOffset, int hitBoxWidth, int hitBoxHeight, KeyHandler keyHandler) {
        super(health, speed, width, height, name, worldX, worldY, xOffset, yOffset, hitBoxWidth, hitBoxHeight, keyHandler);
        setCharacterState();
    }

    /**
     * Updates the position of the player
     * @param baseLayerTiles
     */
    public void update(Tile[][] baseLayerTiles) {
        healthHandler.updateHealth(this.getHealth());
        this.tiles = baseLayerTiles;
        updateEntityPosition();
        if (!attacking) move(); // If player is not attacking, they can move
        hitbox.update(this); // Update hitbox
        updateFrames();
    }

    @Override
    public void draw(Graphics2D g2) {
        drawHealth(g2);

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

        if (checkMoving()) {
            g2.drawImage(movingSprites,
                    (int)this.screenX, (int)this.screenY - Tile.tileRatio*8, (int)(this.screenX + this.getWidth()), (int)(this.screenY) + this.getHeight(),
                    currentCol * spriteW, currentRow * spriteH, (currentCol+1) * spriteW, (currentRow + 1) * spriteH,
                    null);

        } else {
            g2.drawImage(idleSprites,
                    (int)this.screenX, (int)this.screenY - Tile.tileRatio*8, (int)(this.screenX + this.getWidth()), (int)(this.screenY) + this.getHeight(),
                    currentCol * spriteW, currentRow * spriteH, (currentCol+1) * spriteW, (currentRow + 1) * spriteH,
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
