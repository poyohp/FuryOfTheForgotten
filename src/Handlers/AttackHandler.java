package Handlers;


import Attacks.Attack;
import Attacks.Melee;
import Attacks.Ranged;
import Entities.Enemy;
import Entities.Entity;
import Entities.Hitbox;
import Entities.Player;
import World.Tile;


import java.awt.*;
import java.util.ArrayList;


public class AttackHandler {


    ArrayList<Attack> attacks = new ArrayList<Attack>();
    ArrayList<Attack> toRemove = new ArrayList<Attack>();
    KeyHandler keyHandler;
    public boolean canAttack = true;
    public int cooldown = 30, attackFrames = 36;


    public AttackHandler(KeyHandler keyHandler) {
        this.keyHandler = keyHandler;
    }


    void createMelee(int damage, int range, int width, char direction, Entity entity, int xOffset, int yOffset, int duration) {
        attacks.add(new Melee(damage, range, width, direction, entity, xOffset, yOffset, duration));
    }


    void createRanged(int damage, int range, int width, char direction, Entity entity, int xOffset, int yOffset, int duration, int speed) {
        attacks.add(new Ranged(damage, range, width, direction, entity, xOffset, yOffset, duration, speed));
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
        if (keyHandler.attackPress && canAttack) {
            player.attacking = true;
            player.animationState = 0;
            canAttack = false;
        }
        if (player.attacking) {
            System.out.println(player.animationState);
            if (attackFrames == 0) {
                createRanged(5, Tile.tileSize, Tile.tileSize, player.direction, (Entity) player, 0, 0, 150, (int)((Tile.tileSize/Tile.tileMultipler) * 0.625));
                createRanged(5, Tile.tileSize, Tile.tileSize, dir1, player, 0, 0, 150, (int)((Tile.tileSize/Tile.tileMultipler) * 0.625));
                createRanged(5, Tile.tileSize, Tile.tileSize, dir2, player, 0, 0, 150, (int)((Tile.tileSize/Tile.tileMultipler) * 0.625));
                attackFrames = 36;
                player.attacking = false;
            } else {
                attackFrames--;
            }
        }


    }

    void determinePlayerRangedAttackVelocity() {
        for (Attack a : attacks) {
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
            System.out.println("Attack angle: " + a.angle);
        }
    }

    // Must be updated when other entities are included to take an arraylist of all entities as a parameter, not just a player)
    public void update(Player player) {
        checkForPlayerAttack(keyHandler, player);
        determinePlayerRangedAttackVelocity();
        setCooldown();
        removeAttackAfterCooldown();
    }

    private void setCooldown() {
        if (!canAttack) {
            if (cooldown == 0) {
                canAttack = true;
                cooldown = 30;
            } else {
                cooldown -= 1;
            }
        }
    }

    private void removeAttackAfterCooldown() {
        if (!attacks.isEmpty()) {
            for (int i = 0; i < attacks.size(); i++) {
                if (attacks.get(i).getDuration() <= 0) {
                    toRemove.add(attacks.get(i));
                }
                attacks.get(i).setDuration((attacks.get(i).getDuration() - 1));
            }
            attacks.removeAll(toRemove);
        }
    }


    public void draw(Graphics2D g2) {
        for (Attack attack : attacks) {
            attack.draw(g2);
        }
    }


}
