package Handlers.Attacks;

import Attacks.Attack;
import Entities.Enemies.Enemy;
import Entities.Players.Player;
import Handlers.CollisionHandler;
import World.Level;

import java.util.ArrayList;

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
     * @param attackHandler The attack handler containing the player's attacks
     * @param level The current level
     */
    public void dealDamageToEnemies(AttackHandler attackHandler, Level level) {
        ArrayList<Integer> indicesToRemove = new ArrayList<>();

        for (int i = 0; i < attackHandler.playerAttacks.size(); i++) {
            Attack playerAttack = attackHandler.playerAttacks.get(i);

            for (Enemy enemy : level.enemies) {
                if (collisionHandler.enemyWithAttackCollision(enemy, playerAttack) && !enemy.isHit) {
                    enemy.isHit(playerAttack.damage);
                    indicesToRemove.add(i);
                    break;
                }
            }
        }

        //REMOVE IN REVERSE ORDER!!
        for (int i = indicesToRemove.size() - 1; i >= 0; i--) {
            attackHandler.playerAttacks.remove((int) indicesToRemove.get(i));
        }
    }
}
