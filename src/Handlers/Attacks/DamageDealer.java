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
            if (player.type == 'z') {
                enemy.isHit(0.5);
            }
            player.isHit(enemy.damage, false);
        }
    }

    public void dealMeleeDamageToPlayer(AttackHandler attackHandler, Player player) {
        ArrayList<Integer> indicesToRemove = new ArrayList<>();
        for (int i = 0; i < attackHandler.enemyAttacks.size(); i++) {
            Attack enemyAttack = attackHandler.enemyAttacks.get(i);
                if (collisionHandler.playerWithAttackCollision(player, enemyAttack) && !player.isHit && enemyAttack.isActive) {
                    player.isHit(enemyAttack.damage, false);
                    indicesToRemove.add(i);
                    break;
                }

        }

        //REMOVE IN REVERSE ORDER!!
        for (int i = indicesToRemove.size() - 1; i >= 0; i--) {
            attackHandler.enemyAttacks.get(indicesToRemove.get(i)).isActive = false;
        }
    }

    /**
     * Deals damage to enemies if the player's attacks are colliding
     * @param attackHandler The attack handler containing the player's attacks
     * @param level The current level
     */
    public void dealDamageToEnemies(AttackHandler attackHandler, Level level, Player player) {
        ArrayList<Integer> indicesToRemove = new ArrayList<>();

        for (int i = 0; i < attackHandler.playerAttacks.size(); i++) {
            Attack playerAttack = attackHandler.playerAttacks.get(i);

            for (Enemy enemy : level.contactEnemies) {
                if (collisionHandler.enemyWithAttackCollision(enemy, playerAttack) && !enemy.isHit && playerAttack.isActive) {
                    if(player.isDamageBoost) {
                        enemy.isHit(playerAttack.damage+player.boostedDamage);
                    } else if (player.type == 'g' && player.direction == enemy.direction) {
                        enemy.isHit(playerAttack.damage*4);
                    } else enemy.isHit(playerAttack.damage);
                    indicesToRemove.add(i);
                    break;
                }
            }

//            for (Enemy enemy : level.archerEnemies) {
//                if (collisionHandler.enemyWithAttackCollision(enemy, playerAttack) && !enemy.isHit && playerAttack.isActive) {
//                    if(player.isDamageBoost) {
//                        enemy.isHit(playerAttack.damage+player.boostedDamage);
//                    } else if (player.type == 'g' && player.direction == enemy.direction) {
//                        enemy.isHit(playerAttack.damage*4);
//                    } else enemy.isHit(playerAttack.damage);
//                    indicesToRemove.add(i);
//                    break;
//                }
//            }
        }

        //REMOVE IN REVERSE ORDER!!
        for (int i = indicesToRemove.size() - 1; i >= 0; i--) {
            attackHandler.playerAttacks.get((int) indicesToRemove.get(i)).isActive = false;
        }

    }
}
