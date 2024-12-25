package Handlers.Attacks;

import Attacks.Attack;
import Entities.Enemies.Enemy;
import Entities.Players.Player;
import Handlers.CollisionHandler;
import World.Level;

public class DamageDealer {

    private final CollisionHandler collisionHandler;

    public DamageDealer(CollisionHandler collisionHandler) {
        this.collisionHandler = collisionHandler;
    }

    /**
     * Deals damage to the player if the enemy is colliding
     * @param enemy enemy to deal damage to player
     * @param player player to deal damage to
     */
    public void dealDamageToPlayer(Enemy enemy, Player player) {
        if (collisionHandler.enemyPlayerCollision(enemy, player) && !enemy.hitPlayer && !player.isHit) {
            // WHAT HAPPENS WHEN PLAYER IS HIT! (COMPLETED)
            enemy.hitPlayer();
            player.isHit(enemy.damage);
        }
    }

    /**
     * Deals damage to enemies if the player's attacks are colliding
     * @param attackHandler The attackhandles which contains the
     * @param level the current level
     */
    public void dealDamageToEnemies(AttackHandler attackHandler, Level level) {
        int indexMarked = -1;
        for(int i = 0; i < attackHandler.playerAttacks.size(); i++) {
            Attack playerAttack = attackHandler.playerAttacks.get(i);
            for (Enemy enemy: level.enemies) {
                // Lowers enemy health by damage
                if (collisionHandler.enemyWithAttackCollision(enemy, playerAttack)) {
                    indexMarked = i;
                    enemy.isHit(playerAttack.damage);
                }
            }

            //REMOVES ANY MARKED INDEX FROM ATTACKS
            if(indexMarked != -1) {
                attackHandler.playerAttacks.remove(indexMarked);
                i--;
            }
        }
    }
}
