package Attacks;

import Entities.Entity;
import java.awt.*;

public abstract class Attack {

    int damage, range, width, x, y;
    char direction;
    Entity entity;

    Attack (int damage, int range, int width, char direction, Entity entity) {
        this.damage = damage;
        this.range = range;
        this.width = width;
        this.direction = direction;
        this.entity = entity;
    }

    abstract void draw();

}
