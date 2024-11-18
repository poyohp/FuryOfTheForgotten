package Attacks;

import Entities.Entity;

public class Ranged extends Attack{

    Ranged(int damage, int range, int width, char direction, Entity entity) {
        super(damage, range, width, direction, entity);
    }

    @Override
    void draw() {

    }

}
