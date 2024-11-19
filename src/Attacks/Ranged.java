package Attacks;

import Entities.Entity;

public class Ranged extends Attack{

    Ranged(int damage, int range, int width, char direction, Entity entity, int xOffset, int yOffset, int speed, int duration) {
        super(damage, range, width, direction, entity, xOffset, yOffset, duration);
    }

    @Override
    void setInitialHitbox(char[] direction) {

    }

    @Override
    void draw() {

    }

}
