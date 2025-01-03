package Entities.Enemies;

import Entities.Players.Player;
import Handlers.ImageHandler;
import World.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Dragon extends Enemy{

    // Variables for drawing
    private int updateFrames = 7;
    BufferedImage idleSprites;
    BufferedImage attackSprites;
    String idlePngName;
    final int spriteW1 = 128/4, spriteH1 = 128/4; // Sizes for the vampire sprites
    final int spriteW2 = 560/5, spriteH2 = 384/4;
    final int maxCol1 = 4;
    final int maxCol2 = 5;
    int currentRow = 0, currentCol1 = 0, currentCol2 = 0;

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
     * @param idlePngName      name for image
     * @param direction    direction the dragon faces
     */
    public Dragon(int health, double speed, int width, int height, String name, int worldX, int worldY, int xOffset, int yOffset, int hitBoxWidth, int hitBoxHeight, Player player, boolean isFollowing, String idlePngName, String attackPngName, char direction) {
        super(health, speed, width, height, name, worldX, worldY, xOffset, yOffset, hitBoxWidth, hitBoxHeight, player, isFollowing);

        // Loads image
        this.idlePngName = idlePngName;
        idleSprites = ImageHandler.loadImage("Assets/Entities/Enemies/Dragons/" + idlePngName);
        attackSprites = ImageHandler.loadImage("Assets/Entities/Enemies/Dragons/" + attackPngName);
        this.direction = direction;
    }

    public void update() {
        updateEntityPosition();
        setScreenPosition();
        hitbox.update(this);
    }


    /**
     * No movement needed
     */
    public void move() {}

    @Override
    public void hitPlayer() {
        hitPlayer = true;
    }

    @Override
    public void isHit(double damage) {}

    public void updateFrames() {
        if (updateFrames <= 0) {
            if (animationState >= 3) {
                animationState = 0;
            } else {
                animationState++;
            }
            updateFrames = 10;
        } else {
            updateFrames--;
        }
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

        currentCol1 = animationState;
        currentCol2 = animationState;

        if (currentCol1 > maxCol1) currentCol1 = 0;
        if (currentCol2 > maxCol2) currentCol2 = 0;

        g2.drawImage(idleSprites,
                (int) this.screenX, (int) this.screenY - Tile.tileRatio * 8, (int) (this.screenX + this.getWidth()), (int) (this.screenY) + this.getHeight(),
                currentCol1 * spriteW1, currentRow * spriteH1, (currentCol1 + 1) * spriteW1, (currentRow + 1) * spriteH1,
                null);
        updateFrames();

        g2.drawImage(attackSprites,
                xNeeded, yNeeded, xNeeded + this.getWidth(), yNeeded + this.getHeight(),
                currentCol2 * spriteW2, currentRow * spriteH2, (currentCol2 + 1) * spriteW2, (currentRow + 1) * spriteH2,
                null);

    }

}
