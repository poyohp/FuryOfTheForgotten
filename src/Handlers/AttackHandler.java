package Handlers;


import Attacks.Attack;
import Attacks.Melee;
import Attacks.Ranged;
import Entities.Enemy;
import Entities.Entity;
import Entities.Player;
import World.Tile;


import java.awt.*;
import java.util.ArrayList;


public class AttackHandler {


    public ArrayList<Attack> playerAttacks = new ArrayList<Attack>();
    ArrayList<Attack> playerToRemove = new ArrayList<Attack>();
    public ArrayList<Ranged> enemyAttacks = new ArrayList<Ranged>();
    ArrayList<Ranged> enemyToRemove = new ArrayList<Ranged>();
    KeyHandler keyHandler;
    public boolean playerCanAttack = true;
    public int playerCooldown = 30, attackFrames = 36;


    public AttackHandler(KeyHandler keyHandler) {
        this.keyHandler = keyHandler;
    }


    void createMelee(int damage, int range, int width, char direction, Entity entity, int xOffset, int yOffset, int duration) {
        playerAttacks.add(new Melee(damage, range, width, direction, entity, xOffset, yOffset, duration));
    }


    void createPlayerRanged(int damage, int range, int width, char direction, Entity entity, int xOffset, int yOffset, int duration, int speed) {
        playerAttacks.add(new Ranged(damage, range, width, direction, entity, xOffset, yOffset, duration, speed));
    }

    void createEnemyRanged(int damage, int range, int width, char direction, Entity entity, int xOffset, int yOffset, int duration, int speed, Player player) {
        Ranged enemyAttack = new Ranged(damage, range, width, direction, entity, xOffset, yOffset, duration, speed);
        enemyAttack.angle = enemyAttack.calculateAttackAngle(enemyAttack.hitbox, player.hitbox);
        enemyAttacks.add(new Ranged(damage, range, width, direction, entity, xOffset, yOffset, duration, speed));
    }


    void checkForPlayerAttack(KeyHandler keyHandler, Player player) {
        char dir1;
        char dir2;
        if (player.direction == 'u' || player.direction == 'd') {
            dir1 = 'r';
            dir2 = 'l';
        } else {
            dir1 = 'u';
            dir2 = 'd';
        }
        if (keyHandler.attackPress && playerCanAttack) {
            player.attacking = true;
            player.animationState = 0;
            playerCanAttack = false;
        }
        if (player.attacking) {
            if (attackFrames == 0) {
                createPlayerRanged(5, Tile.tileSize, Tile.tileSize, player.direction, (Entity) player, 0, 0, 150, (int)((Tile.tileSize/Tile.tileMultipler) * 0.625));
                createPlayerRanged(5, Tile.tileSize, Tile.tileSize, dir1, player, 0, 0, 150, (int)((Tile.tileSize/Tile.tileMultipler) * 0.625));
                createPlayerRanged(5, Tile.tileSize, Tile.tileSize, dir2, player, 0, 0, 150, (int)((Tile.tileSize/Tile.tileMultipler) * 0.625));
                attackFrames = 36;
                player.attacking = false;
            } else {
                attackFrames--;
            }
        }


    }

    void determinePlayerRangedAttackVelocity() {
        for (Attack a : playerAttacks) {
            if (a.getDirection()[0] == 'u') {
                if (a.getDirection()[1] == 'u' || a.getDirection()[1] == 'd') {
                    a.angle = Math.PI/2;
                    a.move((int) a.determineXVelocity(Math.PI / 2, a.getSpeed()), (int) a.determineYVelocity(Math.PI / 2, a.getSpeed()));
                } else if (a.getDirection()[1] == 'r') {
                    a.angle = 3*Math.PI/8;
                    a.move((int) a.determineXVelocity(3*Math.PI / 8, a.getSpeed()), (int) a.determineYVelocity(3*Math.PI / 8, a.getSpeed()));
                } else {
                    a.angle = 5*Math.PI/8;
                    a.move((int) a.determineXVelocity(5*Math.PI / 8, a.getSpeed()), (int) a.determineYVelocity(5*Math.PI / 8, a.getSpeed()));
                }
            } else if (a.getDirection()[0] == 'r') {
                if (a.getDirection()[1] == 'r' || a.getDirection()[1] == 'l') {
                    a.angle = 0;
                    a.move((int) a.determineXVelocity(0, a.getSpeed()), (int) a.determineYVelocity(0, a.getSpeed()));
                } else if (a.getDirection()[1] == 'u') {
                    a.angle = Math.PI/8;
                    a.move((int) a.determineXVelocity(Math.PI / 8, a.getSpeed()), (int) a.determineYVelocity(Math.PI / 8, a.getSpeed()));
                } else {
                    a.angle = 15*Math.PI/8;
                    a.move((int) a.determineXVelocity(15*Math.PI / 8, a.getSpeed()), (int) a.determineYVelocity(15*Math.PI / 8, a.getSpeed()));
                }
            } else if (a.getDirection()[0] == 'd') {
                if (a.getDirection()[1] == 'd' || a.getDirection()[1] == 'u') {
                    a.angle = 3*Math.PI/2;
                    a.move((int) a.determineXVelocity(3*Math.PI/2, a.getSpeed()), (int) a.determineYVelocity(3*Math.PI/2, a.getSpeed()));
                } else if (a.getDirection()[1] == 'l') {
                    a.angle = 11*Math.PI/8;
                    a.move((int) a.determineXVelocity(11*Math.PI / 8, a.getSpeed()), (int) a.determineYVelocity(11*Math.PI / 8, a.getSpeed()));
                } else {
                    a.angle = 13*Math.PI/8;
                    a.move((int) a.determineXVelocity(13*Math.PI / 8, a.getSpeed()), (int) a.determineYVelocity(13*Math.PI / 8, a.getSpeed()));
                }
            } else {
                if (a.getDirection()[1] == 'l' || a.getDirection()[1] == 'r') {
                    a.angle = Math.PI;
                    a.move((int) a.determineXVelocity(Math.PI, a.getSpeed()), (int) a.determineYVelocity(Math.PI, a.getSpeed()));
                } else if (a.getDirection()[1] == 'd') {
                    a.angle = 9*Math.PI/8;
                    a.move((int) a.determineXVelocity(9*Math.PI / 8, a.getSpeed()), (int) a.determineYVelocity(9*Math.PI / 8, a.getSpeed()));
                } else {
                    a.angle = 7*Math.PI/8;
                    a.move((int) a.determineXVelocity(7*Math.PI / 8, a.getSpeed()), (int) a.determineYVelocity(7*Math.PI / 8, a.getSpeed()));
                }
            }
           // System.out.println("Attack angle: " + a.angle);
        }
    }

    void checkForEnemyAttacks(Enemy enemy, Player player) {
        if (enemy.attacking) {
            if (enemy.attackCooldown == 0) {
                createEnemyRanged(5, Tile.tileSize, Tile.tileSize, enemy.direction, enemy, 0, 0, 150, (int) ((Tile.tileSize / Tile.tileMultipler) * 0.625), player);
                enemy.attackCooldown = 30;
            } else {
                enemy.attackCooldown--;
            }
        }
    }

    void moveEnemyAttacks() {
        for (Ranged attack : enemyAttacks) {
            attack.move((int)attack.determineXVelocity(attack.angle, attack.getSpeed()), (int)attack.determineYVelocity(attack.angle, attack.getSpeed()));
        }
    }

    void removeEnemyAttacks() {
        if (!enemyAttacks.isEmpty()) {
            for (Ranged attack : enemyAttacks) {
                if (attack.getDuration() <= 0) {
                    enemyToRemove.add(attack);
                }
                attack.setDuration(attack.getDuration() - 1);
            }
            enemyAttacks.removeAll(enemyToRemove);
        }
    }

    // Must be updated when other entities are included to take an arraylist of all entities as a parameter, not just a player)
    public void update(Player player, ArrayList<Enemy> enemy) {
        checkForPlayerAttack(keyHandler, player);
        for (Enemy e : enemy) {
            checkForEnemyAttacks(e, player);
        }
        determinePlayerRangedAttackVelocity();
        moveEnemyAttacks();
        setCooldown();
        removeAttackAfterCooldown();
        removeEnemyAttacks();
    }

    private void setCooldown() {
        if (!playerCanAttack) {
            if (playerCooldown == 0) {
                playerCanAttack = true;
                playerCooldown = 30;
            } else {
                playerCooldown -= 1;
            }
        }
    }

    private void removeAttackAfterCooldown() {
        if (!playerAttacks.isEmpty()) {
            for (int i = 0; i < playerAttacks.size(); i++) {
                if (playerAttacks.get(i).getDuration() <= 0) {
                    playerToRemove.add(playerAttacks.get(i));
                }
                playerAttacks.get(i).setDuration((playerAttacks.get(i).getDuration() - 1));
            }
            playerAttacks.removeAll(playerToRemove);
        }
    }


    public void draw(Graphics2D g2) {
        for (Attack attack : playerAttacks) {
            attack.draw(g2);
        }
        for (Attack attack : enemyAttacks) {
            attack.draw(g2);
        }
    }


}
