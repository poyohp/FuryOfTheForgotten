package Entities.Players;

import Handlers.ImageHandler;
import Handlers.KeyHandler;
import World.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Zombie extends Player{

    BufferedImage sprites = ImageHandler.loadImage("Assets/Entities/Players/Zombie/zombies.png");
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
    public Zombie(int health, double speed, int width, int height, String name, double worldX, double worldY, int xOffset, int yOffset, int hitBoxWidth, int hitBoxHeight, KeyHandler keyHandler) {
        super(health, speed, width, height, name, worldX, worldY, xOffset, yOffset, hitBoxWidth, hitBoxHeight, keyHandler);
        setCharacterState();
    }

    void setCharacterState(){
        type = 'z';
        characterAttackFrames = 36;
        characterAttackCooldown = 30;
        attackFrames = characterAttackFrames;
        attackCooldown = characterAttackCooldown;
        maxAnimationState = 3;
    }

    /**
     * Updates the position of the player
     * @param baseLayerTiles
     */
    public void update(Tile[][] baseLayerTiles) {
        healthHandler.updateHealth(this.getHealth());
        this.tiles = baseLayerTiles;
        updateEntityPosition();
        if (!attacking && !inAbility) move(); // If player is not attacking, they can move
        hitbox.update(this); // Update hitbox
        updateFrames();
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
        }
    }

}
