package Entities.Players;

import Handlers.ImageHandler;
import Handlers.KeyHandler;
import World.Level;
import World.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Zombie extends Player{

    boolean attack;
    BufferedImage sprites = ImageHandler.loadImage("Assets/Entities/Players/Zombie/zombies.png");
    BufferedImage ability_1 = ImageHandler.loadImage("Assets/Entities/Players/Zombie/ability_1.png");
    BufferedImage ability_2 = ImageHandler.loadImage("Assets/Entities/Players/Zombie/ability_2.png");
    BufferedImage ability_3 = ImageHandler.loadImage("Assets/Entities/Players/Zombie/ability_3.png");
    BufferedImage ability_4 = ImageHandler.loadImage("Assets/Entities/Players/Zombie/ability_4.png");
    int column1 = 7, column2 = 39, column3 = 71, column4 = 103, row1 = 7, row2 = 39, row3 = 71, row4 = 103, row5 = 135, row6 = 167, row7 = 199, row8 = 231, row9 = 263, row10 = 295, row11 = 327, row12 = 359, row13 = 391, row14 = 423, row15 = 455;


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
    public Zombie(double health, double speed, int width, int height, String name, double worldX, double worldY, int xOffset, int yOffset, int hitBoxWidth, int hitBoxHeight, KeyHandler keyHandler) {
        super(health, speed, width, height, name, worldX, worldY, xOffset, yOffset, hitBoxWidth, hitBoxHeight, keyHandler);
        setCharacterState();
    }

    void setCharacterState(){
        type = 'z';
        characterAttackFrames = 36;
        characterAttackCooldown = 50;
        attackFrames = characterAttackFrames;
        attackCooldown = characterAttackCooldown;
        maxAnimationState = 3;
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

    @Override
    public void draw(Graphics2D g2) {
        if (!attacking && !inAbility) {
            if (direction == 'd') {
                if (!checkMoving()) {
                    switch (animationState) {
                        case 0:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column1, row1, column1 + Tile.normalTileSize, row1 + Tile.normalTileSize, transparent, null);
                            break;
                        case 1:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column2, row1, column2 + Tile.normalTileSize, row1 + Tile.normalTileSize, transparent, null);
                            break;
                        case 2:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column3, row1, column3 + Tile.normalTileSize, row1 + Tile.normalTileSize, transparent, null);
                            break;
                        case 3:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column4, row1, column4 + Tile.normalTileSize, row1 + Tile.normalTileSize, transparent, null);
                            break;
                    }
                } else {
                    switch (animationState) {
                        case 0:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column1, row4, column1 + Tile.normalTileSize, row4 + Tile.normalTileSize, transparent, null);
                            break;
                        case 1:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column2, row4, column2 + Tile.normalTileSize, row4 + Tile.normalTileSize, transparent, null);
                            break;
                        case 2:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column3, row4, column3 + Tile.normalTileSize, row4 + Tile.normalTileSize, transparent, null);
                            break;
                        case 3:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column4, row4, column4 + Tile.normalTileSize, row4 + Tile.normalTileSize, transparent, null);
                            break;
                    }
                }
            } else if (direction == 'r') {
                if (!checkMoving()) {
                    switch (animationState) {
                        case 0:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column1, row2, column1 + Tile.normalTileSize, row2 + Tile.normalTileSize, transparent, null);
                            break;
                        case 1:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column2, row2, column2 + Tile.normalTileSize, row2 + Tile.normalTileSize, transparent, null);
                            break;
                        case 2:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column3, row2, column3 + Tile.normalTileSize, row2 + Tile.normalTileSize, transparent, null);
                            break;
                        case 3:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column4, row2, column4 + Tile.normalTileSize, row2 + Tile.normalTileSize, transparent, null);
                            break;
                    }
                } else {
                    switch (animationState) {
                        case 0:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column1, row5, column1 + Tile.normalTileSize, row5 + Tile.normalTileSize, transparent, null);
                            break;
                        case 1:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column2, row5, column2 + Tile.normalTileSize, row5 + Tile.normalTileSize, transparent, null);
                            break;
                        case 2:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column3, row5, column3 + Tile.normalTileSize, row5 + Tile.normalTileSize, transparent, null);
                            break;
                        case 3:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column4, row5, column4 + Tile.normalTileSize, row5 + Tile.normalTileSize, transparent, null);
                            break;
                    }
                }
            } else if (direction == 'u') {
                if (!checkMoving()) {
                    switch (animationState) {
                        case 0:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column1, row3, column1 + Tile.normalTileSize, row3 + Tile.normalTileSize, transparent, null);
                            break;
                        case 1:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column2, row3, column2 + Tile.normalTileSize, row3 + Tile.normalTileSize, transparent, null);
                            break;
                        case 2:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column3, row3, column3 + Tile.normalTileSize, row3 + Tile.normalTileSize, transparent, null);
                            break;
                        case 3:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column4, row3, column4 + Tile.normalTileSize, row3 + Tile.normalTileSize, transparent, null);
                            break;
                    }
                } else {
                    switch (animationState) {
                        case 0:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column1, row6, column1 + Tile.normalTileSize, row6 + Tile.normalTileSize, transparent, null);
                            break;
                        case 1:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column2, row6, column2 + Tile.normalTileSize, row6 + Tile.normalTileSize, transparent, null);
                            break;
                        case 2:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column3, row6, column3 + Tile.normalTileSize, row6 + Tile.normalTileSize, transparent, null);
                            break;
                        case 3:
                            g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column4, row6, column4 + Tile.normalTileSize, row6 + Tile.normalTileSize, transparent, null);
                            break;
                    }
                }
            } else {
                if (!checkMoving()) {
                    switch (animationState) {
                        case 0:
                            g2.drawImage(sprites, (int) screenX + getWidth(), (int) screenY, (int) screenX, (int) screenY + this.getHeight(), column1, row2, column1 + Tile.normalTileSize, row2 + Tile.normalTileSize, transparent, null);
                            break;
                        case 1:
                            g2.drawImage(sprites, (int) screenX + getWidth(), (int) screenY, (int) screenX, (int) screenY + this.getHeight(), column2, row2, column2 + Tile.normalTileSize, row2 + Tile.normalTileSize, transparent, null);
                            break;
                        case 2:
                            g2.drawImage(sprites, (int) screenX + getWidth(), (int) screenY, (int) screenX, (int) screenY + this.getHeight(), column3, row2, column3 + Tile.normalTileSize, row2 + Tile.normalTileSize, transparent, null);
                            break;
                        case 3:
                            g2.drawImage(sprites, (int) screenX + getWidth(), (int) screenY, (int) screenX, (int) screenY + this.getHeight(), column4, row2, column4 + Tile.normalTileSize, row2 + Tile.normalTileSize, transparent, null);
                            break;
                    }
                } else {
                    switch (animationState) {
                        case 0:
                            g2.drawImage(sprites, (int) screenX + getWidth(), (int) screenY, (int) screenX, (int) screenY + this.getHeight(), column1, row5, column1 + Tile.normalTileSize, row5 + Tile.normalTileSize, transparent, null);
                            break;
                        case 1:
                            g2.drawImage(sprites, (int) screenX + getWidth(), (int) screenY, (int) screenX, (int) screenY + this.getHeight(), column2, row5, column2 + Tile.normalTileSize, row5 + Tile.normalTileSize, transparent, null);
                            break;
                        case 2:
                            g2.drawImage(sprites, (int) screenX + getWidth(), (int) screenY, (int) screenX, (int) screenY + this.getHeight(), column3, row5, column3 + Tile.normalTileSize, row5 + Tile.normalTileSize, transparent, null);
                            break;
                        case 3:
                            g2.drawImage(sprites, (int) screenX + getWidth(), (int) screenY, (int) screenX, (int) screenY + this.getHeight(), column4, row5, column4 + Tile.normalTileSize, row5 + Tile.normalTileSize, transparent, null);
                            break;
                    }
                }
            }
        } else if (attacking && !inAbility) {
            if (direction == 'd') {
                switch (animationState) {
                    case 0:
                        g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column1, row13, column1 + Tile.normalTileSize, row13 + Tile.normalTileSize, transparent, null);
                        break;
                    case 1, 2:
                        g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column2, row13, column2 + Tile.normalTileSize, row13 + Tile.normalTileSize, transparent, null);
                        break;
                    case 3:
                        g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column3, row13, column3 + Tile.normalTileSize, row13 + Tile.normalTileSize, transparent, null);
                        break;
                }
            } else if (direction == 'r') {
                switch (animationState) {
                    case 0:
                        g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column1, row14, column1 + Tile.normalTileSize, row14 + Tile.normalTileSize, transparent, null);
                        break;
                    case 1, 2:
                        g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column2, row14, column2 + Tile.normalTileSize, row14 + Tile.normalTileSize, transparent, null);
                        break;
                    case 3:
                        g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column3, row14, column3 + Tile.normalTileSize, row14 + Tile.normalTileSize, transparent, null);
                        break;
                }
            } else if (direction == 'u') {
                switch (animationState) {
                    case 0:
                        g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column1, row15, column1 + Tile.normalTileSize, row15 + Tile.normalTileSize, transparent, null);
                        break;
                    case 1, 2:
                        g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column2, row15, column2 + Tile.normalTileSize, row15 + Tile.normalTileSize, transparent, null);
                        break;
                    case 3:
                        g2.drawImage(sprites, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), column3, row15, column3 + Tile.normalTileSize, row15 + Tile.normalTileSize, transparent, null);
                        break;
                }
            } else {
                switch (animationState) {
                    case 0:
                        g2.drawImage(sprites, (int) screenX + getWidth(), (int) screenY, (int) screenX, (int) screenY + this.getHeight(), column1, row14, column1 + Tile.normalTileSize, row14 + Tile.normalTileSize, transparent, null);
                        break;
                    case 1, 2:
                        g2.drawImage(sprites, (int) screenX + getWidth(), (int) screenY, (int) screenX, (int) screenY + this.getHeight(), column2, row14, column2 + Tile.normalTileSize, row14 + Tile.normalTileSize, transparent, null);
                        break;
                    case 3:
                        g2.drawImage(sprites, (int) screenX + getWidth(), (int) screenY, (int) screenX, (int) screenY + this.getHeight(), column3, row14, column3 + Tile.normalTileSize, row14 + Tile.normalTileSize, transparent, null);
                        break;
                }
            }
        } else {
            switch (animationState) {
                case 0:
                    g2.drawImage(ability_1, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), 0, 0, 16, 16, transparent, null);
                    break;
                case 1:
                    g2.drawImage(ability_2, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), 0, 0, 16, 16, transparent, null);
                    break;
                case 2:
                    g2.drawImage(ability_3, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), 0, 0, 16, 16, transparent, null);
                    break;
                case 3:
                    g2.drawImage(ability_4, (int) screenX, (int) screenY, (int) screenX + getWidth(), (int) screenY + this.getHeight(), 0, 0, 16, 16, transparent, null);
                    break;
            }
        }
        //hitbox.draw(g2);
    }


}
