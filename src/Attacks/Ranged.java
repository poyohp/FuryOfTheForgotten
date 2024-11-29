package Attacks;
import Entities.Entity;
import Entities.Hitbox;
import java.awt.*;
import java.awt.image.BufferedImage;

import Handlers.ImageHandler;
import System.GamePanel;
import System.Main;

public class Ranged extends Attack{
    static BufferedImage arrow = ImageHandler.loadImage("src/Assets/Projectiles/Arrow.png");

    public Ranged(int damage, int range, int width, char direction, Entity entity, int xOffset, int yOffset, int duration, int speed) {
        super(damage, range, width, direction, entity, xOffset, yOffset, duration);
        setInitialHitbox(getDirection());
        setSpeed(speed);
    }

    @Override
    public void setInitialHitbox(char[] direction) {
        if (direction[0] == 'u') {
            if (direction[1] == 'r') {
                setX(entity.worldX + entity.getWidth());
                setY((int)(entity.worldY - getRange()*Math.sin(Math.PI/4)));
            } else if (direction[1] == 'l') {
                setX((int)(entity.worldX - getRange()*Math.cos(Math.PI/4)));
                setY((int)(entity.worldY - getRange()*Math.sin(Math.PI/4)));
            } else {
                setX(entity.worldX + (double) entity.getWidth() / 2 - (double) getWidth() / 2);
                setY(entity.worldY - getRange());
            }
        } else if (direction[0] == 'r') {
            if (direction[1] == 'u') {
                setX(entity.worldX + entity.getWidth());
                setY((int)(entity.worldY - getRange()*Math.sin(Math.PI/4)));
            } else if (direction[1] == 'd') {
                setX(entity.worldX + entity.getWidth());
                setY(entity.worldY + entity.getHeight());
            } else {
                setX(entity.worldX + entity.getWidth());
                setY(entity.worldY + (double) entity.getHeight() / 2 - (double) getWidth() / 2);
            }
        } else if (direction[0] == 'd') {
            if (direction[1] == 'r') {
                setX(entity.worldX + entity.getWidth());
                setY(entity.worldY + entity.getHeight());
            } else if (direction[1] == 'l') {
                setX((int)(entity.worldX - getRange()*Math.cos(Math.PI/4)));
                setY((int)(entity.worldY + entity.getHeight()));
            } else {
                setX(entity.worldX + (double) entity.getWidth() / 2 - (double) getWidth() / 2);
                setY(entity.worldY + entity.getHeight());
            }
        } else {
            if (direction[1] == 'd') {
                setX((int)(entity.worldX - getRange()*Math.cos(Math.PI/4)));
                setY((int)(entity.worldY + entity.getHeight()));
            } else if (direction[1] == 'u') {
                setX((int)(entity.worldX - getRange()*Math.cos(Math.PI/4)));
                setY((int)(entity.worldY - getRange()*Math.sin(Math.PI/4)));
            } else {
                setX(entity.worldX - getRange());
                setY(entity.worldY + (double) entity.getHeight() / 2 - (double) getWidth() / 2);
            }
        }


        if (direction[0] == 'u' || direction[0] == 'd') {
            if (direction[1] == 'r' || direction[1] == 'l') {
                hitbox = new Hitbox((int) getX(), (int) getY(), getXOffset(), getYOffset(), (int)(getRange()*Math.cos(Math.PI/4)), (int)(getWidth()*Math.cos(Math.PI/4)));
            } else {
                hitbox = new Hitbox((int) getX(), (int) getY(), getXOffset(), getYOffset(), getWidth(), getRange());
            }
        } else {
            if (direction[1] == 'u' || direction[1] == 'd') {
                hitbox = new Hitbox((int) getX(), (int) getY(), getXOffset(), getYOffset(), (int)(getRange()*Math.cos(Math.PI/4)), (int)(getWidth()*Math.cos(Math.PI/4)));
            } else {
                hitbox = new Hitbox((int) getX(), (int) getY(), getXOffset(), getYOffset(), getRange(), getWidth());
            }
        }
    }


    /**
     * Calculates attack angle from right horizontal
     * @param attack hitbox of attack
     * @param entity hitbox of entity
     * @return integer value angle of attack angle in relation to right horizontal
     */
    double calculateAttackAngle(Hitbox attack, Hitbox entity) {
        double attackX = attack.getWorldXPos() + (double) attack.getWidth()/2;
        double attackY = attack.getWorldYPos() + (double) attack.getHeight()/2;
        double entityX = entity.getWorldXPos() + (double) entity.getWidth()/2;
        double entityY = entity.getWorldYPos() + (double) entity.getHeight()/2;


        if (entityX > attackX && entityY > attackY) {
            angle = (2*Math.PI) - Math.atan(((entityY - attackY) / (entityX - attackX)));
        } else if (entityX > attackX && entityY < attackY) {
            angle = Math.atan((attackY - entityY) / (entityX - attackX));
        } else if (entityX < attackX && entityY > attackY) {
            angle = Math.PI + Math.atan(((entityY - attackY) / (attackX - entityX)));
        } else {
            angle = Math.PI - Math.atan(((attackY - entityY) / (attackX - entityX)));
        }

        return angle;
    }

    @Override
    public double determineXVelocity(double angle, int speed) {
        return speed * Math.cos(angle);
    }

    @Override
    public double determineYVelocity(double angle, int speed) {
        return -speed * Math.sin(angle);
    }


    @Override
    public void move(int xSpeed, int ySpeed) {
        setX(getX() + xSpeed);
        setY(getY() + ySpeed);
    }


    @Override
    public void setScreenPosition() {
        int screenTop = (int)(entity.worldY + entity.getHeight()/2 - GamePanel.screenHeight/2);
        int screenRight = (int)(entity.worldX + entity.getWidth()/2 + GamePanel.screenWidth/2);
        int screenBottom = (int)(entity.worldY + entity.getHeight()/2 + GamePanel.screenHeight/2);
        int screenLeft = (int)(entity.worldX + entity.getWidth()/2 - GamePanel.screenWidth/2);
        int top = (int)(getY());
        int left = (int)(getX());
        int bottom = (int)(getY() + getRange());
        int right = (int)(getX() + getWidth());

        if ((bottom > screenTop && right > screenLeft) || (bottom > screenTop && left < screenRight) || (top < screenBottom && right > screenLeft) || (top < screenBottom && left > screenRight)) {
            setScreenX(getX() - screenLeft);
            setScreenY(getY() - screenTop);
        }

    }

    @Override
    public void draw(Graphics2D g2) {
        setScreenPosition();
        g2.setColor(Color.BLACK);

        if (getDirection()[0] == 'u') {
            if (getDirection()[1] == 'u' || getDirection()[1] == 'd') {
                g2.drawImage(arrow, (int) getScreenX() + 20, (int) getScreenY(), (int) getScreenX() + getWidth() + 20, (int) getScreenY() + getRange(), 0, 32, 16, 48, null);
            } else {
                g2.drawImage(arrow, (int) getScreenX() + 15, (int) getScreenY() - 20, (int) getScreenX() + getWidth() + 15, (int) getScreenY() + getRange() - 20, 0, 32, 16, 48, null);
            }
        } else if (getDirection()[0] == 'r') {
            if (getDirection()[1] == 'r' || getDirection()[1] == 'l') {
                g2.drawImage(arrow, (int) getScreenX(), (int) getScreenY() + 26, (int) getScreenX() + getWidth(), (int) getScreenY() + getRange() + 26, 0, 32, 16, 48, null);
            } else {
                g2.drawImage(arrow, (int) getScreenX(), (int) getScreenY() + 22, (int) getScreenX() + getWidth(), (int) getScreenY() + getRange() + 22, 0, 32, 16, 48, null);
            }
        } else if (getDirection()[0] == 'l') {
            if (getDirection()[1] == 'r' || getDirection()[1] == 'l') {
                g2.drawImage(arrow, (int) getScreenX() - 20, (int) getScreenY() - 20, (int) getScreenX() + getWidth() - 20, (int) getScreenY() + getRange() - 20, 0, 32, 16, 48, null);
            } else {
                g2.drawImage(arrow, (int) getScreenX() - 20, (int) getScreenY() - 25, (int) getScreenX() + getWidth() - 20, (int) getScreenY() + getRange() - 25, 0, 32, 16, 48, null);
            }
        } else {
            if (getDirection()[1] == 'u' || getDirection()[1] == 'd') {
                g2.drawImage(arrow, (int) getScreenX() - 25, (int) getScreenY() + 10, (int) getScreenX() + getWidth() - 25, (int) getScreenY() + getRange() + 10, 0, 32, 16, 48, null);
            } else {
                g2.drawImage(arrow, (int) getScreenX() - 25, (int) getScreenY() + 10, (int) getScreenX() + getWidth() - 25, (int) getScreenY() + getRange() + 10, 0, 32, 16, 48, null);
            }
        }

    }

}