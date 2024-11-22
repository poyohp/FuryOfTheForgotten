package Handlers;

import Entities.Hitbox;

public class AttackHandler {

    /**
     * Calculates attack angle from right horizontal
     * @param attack hitbox of attack
     * @param entity hitbox of entity
     * @return integer value angle of attack in relation to right horizontal
     */
    double calculateAttackAngle(Hitbox attack, Hitbox entity) {
        double attackX = attack.getWorldXPos() + (double) attack.getWidth()/2;
        double attackY = attack.getWorldYPos() + (double) attack.getHeight()/2;
        double entityX = entity.getWorldXPos() + (double) entity.getWidth()/2;
        double entityY = entity.getWorldYPos() + (double) entity.getHeight()/2;

        if (entityX > attackX && entityY > attackY) {
            return Math.atan(360 - ((entityY - attackY) / (entityX - attackX)));
        } else if (entityX > attackX && entityY < attackY) {
            return Math.atan((attackY - entityY) / (entityX - attackX));
        } else if (entityX < attackX && entityY > attackY) {
            return Math.atan(180 + ((entityY - attackY) / (attackX - entityX)));
        } else {
            return Math.atan(180 - ((attackY - entityY) / (attackX - entityX)));
        }
    }

    double determineVelocities(double angle) {
        return angle;
    }

}
