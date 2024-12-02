package Handlers.Attacks;

import Attacks.Attack;
import Entities.Enemy;
import Entities.Player;
import Handlers.CollisionHandler;
import World.Level;

public class DamageDealer {

    /**
     * Deals damage to the player if the enemy is colliding
     * @param enemy enemy to deal damage to player
     * @param player player to deal damage to
     */
    public void dealDamageToPlayer(Enemy enemy, Player player) {
        double damage = enemy.damage; // Will be updated to add defense later
        if (CollisionHandler.enemyPlayerCollision(enemy, player)) player.setHealth(player.getHealth() - damage); // Lowers player health by damage

    }

    /**
     * Deals damage to enemies if the player's attacks are colliding
     * @param attackHandler The attackhandles which contains the
     * @param level the current level
     */
    public void dealDamageToEnemies(AttackHandler attackHandler, Level level) {
        for (Attack playerAttack: attackHandler.playerAttacks) {
            for (Enemy enemy: level.enemies) {
                // Lowers enemy health by damage
                if (CollisionHandler.enemyWithAttackCollision(enemy, playerAttack)) enemy.setHealth(enemy.getHealth() - playerAttack.getDamage());
            }
        }
    }
}
