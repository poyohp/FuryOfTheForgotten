package Entities.Players;

import Handlers.ImageHandler;
import Handlers.KeyHandler;
import World.Tile;
import World.Level;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class Vampire extends Player{

    boolean attack;
    BufferedImage movingSprites = ImageHandler.loadImage("Assets/Entities/Players/Vampire/Vampire_walk.png");
    BufferedImage idleSprites = ImageHandler.loadImage("Assets/Entities/Players/Vampire/Vampire_idle.png");
    BufferedImage attackSprites = ImageHandler.loadImage("Assets/Entities/Players/Vampire/Vampire_attack_hitbox.png");
    BufferedImage attacks = ImageHandler.loadImage("Assets/Entities/Players/Vampire/Vampire_attack.png");
    int spriteW1 = 64/4, spriteH1 = 96/4; // Sizes for the vampire sprites
    int spriteW2 = 192/4, spriteH2 = 224/4;
    int maxCol = 4;
    int currentRow = 0, currentCol = 0;


    void setCharacterState(){
        type = 'v';
        characterAttackFrames = 36;
        characterAttackCooldown = 48;
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

    @Override
    public void draw(Graphics2D g2) {

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

        if (checkMoving() && !attacking) {
            g2.drawImage(movingSprites,
                    (int) this.screenX, (int) this.screenY - Tile.tileRatio * 8, (int) (this.screenX + this.getWidth()), (int) (this.screenY) + this.getHeight(),
                    currentCol * spriteW1, currentRow * spriteH1, (currentCol + 1) * spriteW1, (currentRow + 1) * spriteH1,
                    null);

        } else if (!checkMoving() && !attacking) {
            g2.drawImage(idleSprites,
                    (int) this.screenX, (int) this.screenY - Tile.tileRatio * 8, (int) (this.screenX + this.getWidth()), (int) (this.screenY) + this.getHeight(),
                    currentCol * spriteW1, currentRow * spriteH1, (currentCol + 1) * spriteW1, (currentRow + 1) * spriteH1,
                    null);
        }

        if (attacking) {
            if (direction == 'd') {
                switch (animationState) {
                    case 0:
                        g2.drawImage(attacks, (int) screenX, (int) screenY - Tile.tileRatio, (int) screenX + getWidth(), (int) screenY + getHeight(), 16, 14, 32, 32, transparent, null);
                        break;
                    case 1:
                        g2.drawImage(attacks, (int) screenX, (int) screenY, (int) screenX + getWidth() + Tile.tileRatio, (int) screenY + getHeight() + 16 * Tile.tileRatio, 64, 16, 81, 48, transparent, null);
                        break;
                    case 2:
                        g2.drawImage(attacks, (int) screenX - 4 * Tile.tileRatio, (int) screenY, (int) screenX + getWidth() + 4 * Tile.tileRatio, (int) screenY + getHeight() + 16 * Tile.tileRatio, 108, 16, 132, 48, transparent, null);
                        break;
                    case 3:
                        g2.drawImage(attacks, (int) screenX - 6 * Tile.tileRatio, (int) screenY, (int) screenX + getWidth() + 6 * Tile.tileRatio, (int) screenY + getHeight() + 16 * Tile.tileRatio, 154, 16, 182, 48, transparent, null);
                        break;
                    /*
                    case 4:
                        g2.drawImage(attacks, (int) screenX - 6*Tile.tileRatio, (int) screenY, (int) screenX + getWidth() + 6*Tile.tileRatio, (int) screenY + getHeight() + 16 * Tile.tileRatio, 202, 16, 230, 48, transparent, null);
                        break;

                     */
                }
            } else if (direction == 'l') {
                switch (animationState) {
                    case 0:
                        g2.drawImage(attacks, (int) screenX, (int) screenY - 2*Tile.tileRatio, (int) screenX + getWidth(), (int) screenY + getHeight(), 18, 70, 34, 88, transparent, null);
                        break;
                    case 1:
                        g2.drawImage(attacks, (int) screenX - 17*Tile.tileRatio, (int) screenY - Tile.tileRatio, (int) screenX + getWidth() - 3 * Tile.tileRatio, (int) screenY + getHeight() + Tile.tileRatio, 48, 71, 77, 89, transparent, null);
                        break;
                    case 2:
                        g2.drawImage(attacks, (int) screenX - 17*Tile.tileRatio, (int) screenY - Tile.tileRatio, (int) screenX + getWidth() - 3 * Tile.tileRatio, (int) screenY + getHeight() + 4 * Tile.tileRatio, 96, 71, 125, 92, transparent, null);
                        break;
                    case 3:
                        g2.drawImage(attacks, (int) screenX - 14*Tile.tileRatio, (int) screenY - Tile.tileRatio, (int) screenX + getWidth() - 3 * Tile.tileRatio, (int) screenY + getHeight() + 4 * Tile.tileRatio, 147, 71, 173, 92, transparent, null);
                        break;
                }
            } else if (direction == 'r') {
                switch (animationState) {
                    case 0:
                        g2.drawImage(attacks, (int) screenX, (int) screenY - 2*Tile.tileRatio, (int) screenX + getWidth(), (int) screenY + getHeight(), 34, 70, 18, 88, transparent, null);
                        break;
                    case 1:
                        g2.drawImage(attacks, (int) screenX + 3*Tile.tileRatio, (int) screenY - Tile.tileRatio, (int) screenX + getWidth() + 17 * Tile.tileRatio, (int) screenY + getHeight() + Tile.tileRatio, 77, 71, 48, 89, transparent, null);
                        break;
                    case 2:
                        g2.drawImage(attacks, (int) screenX + 3*Tile.tileRatio, (int) screenY - Tile.tileRatio, (int) screenX + getWidth() + 17 * Tile.tileRatio, (int) screenY + getHeight() + 4 * Tile.tileRatio, 125, 71, 96, 92, transparent, null);
                        break;
                    case 3:
                        g2.drawImage(attacks, (int) screenX + 3*Tile.tileRatio, (int) screenY - Tile.tileRatio, (int) screenX + getWidth() + 14 * Tile.tileRatio, (int) screenY + getHeight() + 4 * Tile.tileRatio, 173, 71, 147, 92, transparent, null);
                        break;
                }
            } else {
                switch (animationState) {
                    case 0:
                        g2.drawImage(attacks, (int) screenX, (int) screenY - Tile.tileRatio, (int) screenX + getWidth(), (int) screenY + getHeight(), 16, 182, 32, 200, transparent, null);
                        break;
                    case 1:
                        g2.drawImage(attacks, (int) screenX, (int) screenY - 16 * Tile.tileRatio, (int) screenX + getWidth() + Tile.tileRatio, (int) screenY + getHeight(), 64, 168, 81, 199, transparent, null);
                        break;
                    case 2:
                        g2.drawImage(attacks, (int) screenX - 4 * Tile.tileRatio, (int) screenY - 16 * Tile.tileRatio, (int) screenX + getWidth() + 4 * Tile.tileRatio, (int) screenY + getHeight(), 108, 168, 132, 199, transparent, null);
                        break;
                    case 3:
                        g2.drawImage(attacks, (int) screenX - 6 * Tile.tileRatio, (int) screenY - 16 * Tile.tileRatio, (int) screenX + getWidth() + 6 * Tile.tileRatio, (int) screenY + getHeight(), 154, 168, 182, 199, transparent, null);
                        break;
                }
            }
        }
    }


    @Override
    public void checkAttack() {

        // Checking for keypress
        if (keyHandler.attackPress && canAttack) {
            attacking = true;
            animationState = 0;
            //maxAnimationState = 4;
            canAttack = false;
            attack = true;
        }

        // Setting attack to true
        if (attacking) {
            if (attackFrames == 0) {
                attackFrames = characterAttackFrames;
                //maxAnimationState = 3;
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

    @Override
    public void update(Tile[][] baseLayerTiles, Level level) {
        this.currentLevel = level;
        checkHit();
        updateSpeedBoost();
        updateDamageBoost();

        this.tiles = baseLayerTiles;
        updateEntityPosition();
        if (!attacking) move(); // If player is not attacking, they can move
        hitbox.update(this); // Update hitbox
        updateFrames();
    }



}
