package Attacks.Ranged;

import Entities.Enemies.Enemy;
import Entities.Entity;
import Entities.Hitbox;
import Entities.Players.Player;
import Handlers.ImageHandler;
import World.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class BloodOrb extends Ranged {

    int animationState = 0;
    BufferedImage bloodOrb_1 = ImageHandler.loadImage("Assets/Projectiles/bloodOrb_1.png");
    BufferedImage bloodOrb_2 = ImageHandler.loadImage("Assets/Projectiles/bloodOrb_2.png");
    BufferedImage bloodOrb_3 = ImageHandler.loadImage("Assets/Projectiles/bloodOrb_3.png");
    BufferedImage bloodOrb_4 = ImageHandler.loadImage("Assets/Projectiles/bloodOrb_4.png");
    Color transparent = new Color(0, 0, 0, 0);
    ArrayList<Enemy> enemies;
    double angle;
    int updateFrames = 12;
    int maxAnimationState = 3;

    /**
     * Create ranged attack
     *
     * @param range     attack range
     * @param width     attack width, perpendicular to range
     * @param direction second attack direction, first one is the direction of entity
     * @param entity    Entity attack corresponds to
     * @param xOffset   attack x offset
     * @param yOffset   attack y offset
     * @param duration  attack duration
     * @param speed     attack speed
     */
    public BloodOrb(int range, int width, char direction, Entity entity, int xOffset, int yOffset, int duration, int speed, double angle, ArrayList<Enemy> enemies) {
        super(2, range, width, direction, entity, xOffset, yOffset, duration, speed);
        spawnDistance = 80;
        this.enemies = enemies;
        this.angle = angle;
        setInitialHitbox();
    }

    @Override
    public void setInitialHitbox() {
        int centerWorldX = (int)(entity.worldX + ((double) entity.getWidth() /2));
        int centerWorldY = (int)(entity.worldY + ((double) entity.getHeight() /2));
        int centerScreenX = (int)(entity.screenX + ((double) entity.getWidth() /2));
        int centerScreenY = (int)(entity.screenY + ((double) entity.getHeight() /2));
        int hitBoxWidth, hitBoxHeight, hitBoxScreenX, hitBoxScreenY, hitBoxWorldX, hitBoxWorldY;

        hitBoxWidth = getWidth();
        hitBoxHeight = getRange();

        hitBoxScreenX = (int)(centerScreenX + spawnDistance*Math.cos(angle) - (double) hitBoxWidth / 2);
        hitBoxScreenY = (int)(centerScreenY +  (spawnDistance*Math.sin(angle))*-1 - (double) hitBoxHeight / 2);
        hitBoxWorldX = (int)(centerWorldX + spawnDistance*Math.cos(angle) - (double) hitBoxWidth / 2);
        hitBoxWorldY = (int)(centerWorldY +  (spawnDistance*Math.sin(angle))*-1 - (double) hitBoxHeight / 2);


        hitbox = new Hitbox(hitBoxWorldX, hitBoxWorldY, hitBoxScreenX, hitBoxScreenY, hitBoxWidth, hitBoxHeight, 0, 0);
        setWorldX(hitBoxWorldX);
        setWorldY(hitBoxWorldY);
    }

    public void home(Entity e) {
        double projX = getWorldX() + (double) getWidth() /2;
        double projY = getWorldY() + (double) getRange() /2;
        double entityX = e.worldX + (double) e.getWidth() /2;
        double entityY = e.worldY + (double) e.getHeight() /2;

        if (entityX > projX && entityY > projY) {
            angle = (2*Math.PI) - Math.atan(((entityY - projY) / (entityX - projX)));
        } else if (entityX > projX && entityY < projY) {
            angle = Math.atan((projY - entityY) / (entityX - projX));
        } else if (entityX < projX && entityY > projY) {
            angle = Math.PI + Math.atan(((entityY - projY) / (projX - entityX)));
        } else {
            angle = Math.PI - Math.atan(((projY - entityY) / (projX - entityX)));
        }

        move((int)(getSpeed() * Math.cos(angle)), (int)(-getSpeed() * Math.sin(angle)));
    }

    @Override
    public void draw(Graphics2D g2) {
        switch (animationState) {
            case 0:
                g2.drawImage(bloodOrb_1, (int) getScreenX(), (int) getScreenY(), (int) getScreenX() + getWidth(), (int) getScreenY() + this.getRange(), 0, 0, 16, 28, transparent, null);
                break;
            case 1:
                g2.drawImage(bloodOrb_2, (int) getScreenX(), (int) getScreenY(), (int) getScreenX() + getWidth(), (int) getScreenY() + this.getRange(), 0, 0, 16, 28, transparent, null);
                break;
            case 2:
                g2.drawImage(bloodOrb_3, (int) getScreenX(), (int) getScreenY(), (int) getScreenX() + getWidth(), (int) getScreenY() + this.getRange(), 0, 0, 16, 28, transparent, null);
                break;
            case 3:
                g2.drawImage(bloodOrb_4, (int) getScreenX(), (int) getScreenY(), (int) getScreenX() + getWidth(), (int) getScreenY() + this.getRange(), 0, 0, 16, 28, transparent, null);
                break;
        }
    }

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

    @Override
    public void update(Player player) {
        updateFrames();

        double projX = getWorldX() + (double) getWidth() / 2;
        double projY = getWorldY() + (double) getRange() / 2;
        double entityX;
        double entityY;
        double shortestDistance = 1.7976931348623157 * (10^308);
        Enemy enemyToFollow;

        if (enemies.isEmpty()) {
            move((int) determineXVelocity(angle, getSpeed()), (int) determineYVelocity(angle, getSpeed()));
        } else {
            enemyToFollow = enemies.getFirst();

            for (Enemy e: enemies) {
                entityX = e.worldX + (double) e.getWidth() / 2;
                entityY = e.worldY + (double) e.getHeight() / 2;
                if (Math.sqrt(Math.abs((projX-entityX)*(projX-entityX) + (projY-entityY)*(projY-entityY))) < shortestDistance) {
                    shortestDistance = Math.sqrt(Math.abs((projX-entityX)*(projX-entityX) + (projY-entityY)*(projY-entityY)));
                    enemyToFollow = e;
                }
            }

            home(enemyToFollow);
        }

        setScreenPosition(player);
        hitbox.update(this);
    }

}
