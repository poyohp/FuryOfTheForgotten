package Attacks;
import Entities.Entity;
import Entities.Hitbox;
import java.awt.*;

public class Ranged extends Attack{
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
                setX(entity.worldX + entity.getWidth() + getRange());
                setY(entity.worldY + (double) entity.getHeight() / 2 - (double) getWidth() / 2);
            }
        } else if (direction[0] == 'd') {
            if (direction[1] == 'r') {
                setX(entity.worldX + entity.getWidth());
                setY(entity.worldY + entity.getHeight());
            } else if (direction[1] == 'l') {
                setX((int)(entity.worldX - getRange()*Math.cos(Math.PI/4)));
                setY((int)(entity.worldY + entity.getHeight() + getRange()*Math.sin(45)));
            } else {
                setX(entity.worldX + (double) entity.getWidth() / 2 - (double) getWidth() / 2);
                setY(entity.worldY + getRange());
            }
        } else {
            if (direction[1] == 'd') {
                setX((int)(entity.worldX - getRange()*Math.cos(Math.PI/4)));
                setY((int)(entity.worldY + entity.getHeight() + getRange()*Math.sin(Math.PI/4)));
            } else if (direction[1] == 'u') {
                setX((int)(entity.worldX - getRange()*Math.cos(Math.PI/4)));
                setY((int)(entity.worldY - getRange()*Math.sin(Math.PI/4)));
            } else {
                setX(entity.worldX - getRange());
                setY(entity.worldY + (double) entity.getHeight() / 2 - (double) getRange() / 2);
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
            return (2*Math.PI) - Math.atan(((entityY - attackY) / (entityX - attackX)));
        } else if (entityX > attackX && entityY < attackY) {
            return Math.atan((attackY - entityY) / (entityX - attackX));
        } else if (entityX < attackX && entityY > attackY) {
            return Math.PI + Math.atan(((entityY - attackY) / (attackX - entityX)));
        } else {
            return Math.PI - Math.atan(((attackY - entityY) / (attackX - entityX)));
        }
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


    }

    @Override
    public void draw(Graphics2D g2) {

    }
}
