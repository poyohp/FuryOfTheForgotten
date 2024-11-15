package Entities;

public class Enemy extends Entity {
    private int vision;

    Enemy(int health, int speed, int width, int height, String name) {
        super(health, speed, width, height, name);
    }

    @Override
    void update() {
        move();
    }

    void move() {
        /*
		IF player is within enemy vision:
			Use A* Algorithm to follow player
         */
    }

    void attack() {
        /*
        IF player is within enemy vision:
			DISPLAY attack image
            Create NEW enemyAttack(worldX, worldY)
         */
    }
}
