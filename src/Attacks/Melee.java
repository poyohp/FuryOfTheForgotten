package Attacks;

import Entities.Entity;

public class Melee extends Attack{

    Melee(int damage, int range, int width, char direction, Entity entity) {
        super(damage, range, width, direction, entity);
        if (direction == 'u') {
            x = entity.worldX + entity.getWidth()/2 - width/2;
            y = entity.worldY - range;
        } else if (direction == 'r') {
            x = entity.worldX + entity.getWidth() + range;
            y = entity.worldY + entity.getHeight()/2 - width/2;
        } else if (direction == 'd') {

        }
    }

    @Override
    void draw() {

    }

}
