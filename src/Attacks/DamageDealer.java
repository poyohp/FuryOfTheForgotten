package Attacks;

import Entities.Enemy;
import Entities.Player;
import Handlers.AttackHandler;
import Handlers.CollisionHandler;
import World.Level;

public class DamageDealer {
    CollisionHandler collisionHandler = new CollisionHandler();

//    public void dealDamageToPlayer(AttackHandler attackHandler, Player player) {
//        for (Attack enemyAttack: attackHandler.enemyAttacks) {
//            int finalDamage = enemyAttack.getDamage(); // Will be updated to add defense later
//            if (finalDamage < 0) finalDamage = 0; // Will be used for the future, when we add defense
//
//            if (collisionHandler.playerWithAttackCollision(player, enemyAttack)) player.setHealth(player.getHealth() - finalDamage); // Lowers player health by damage
//        }
//    }

    public void dealDamageToPlayer(Enemy enemy, Player player) {
        double damage = enemy.damage; // Will be updated to add defense later
        if (CollisionHandler.enemyPlayerCollision(enemy, player)) player.setHealth(player.getHealth() - damage); // Lowers player health by damage

    }

    public void dealDamageToEnemies(AttackHandler attackHandler, Level level) {
        for (Attack playerAttack: attackHandler.playerAttacks) {
            for (Enemy enemy: level.enemies) {
                // Lowers enemy health by damage
                if (collisionHandler.enemyWithAttackCollision(enemy, playerAttack)) enemy.setHealth(enemy.getHealth() - playerAttack.getDamage());
            }
        }
    }
}