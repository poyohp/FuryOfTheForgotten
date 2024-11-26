package Handlers;


import Attacks.Attack;
import Attacks.Melee;
import Attacks.Ranged;
import Entities.Entity;
import Entities.Hitbox;
import Entities.Player;


import java.awt.*;
import java.util.ArrayList;


public class AttackHandler {


    ArrayList<Attack> attacks = new ArrayList<Attack>();
    ArrayList<Attack> toRemove = new ArrayList<Attack>();
    KeyHandler keyHandler;
    public boolean canAttack = true;
    public int cooldown = 30;


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
            createRanged(5, 50, 50, player.direction, player, 0, 0, 150, 10);
            createRanged(5, 50, 50, dir1, player, 0, 0, 150, 10);
            createRanged(5, 50, 50, dir2, player, 0, 0, 150, 10);
            //createMelee(5, 50, 100, dir2, player, 0, 0, 30);
            canAttack = false;
        }
    }

    // Must be updated when other entities are included to take an asrraylist of all entities as a parameter, not just a player)
    public void update(Player player) {
        checkForPlayerAttack(keyHandler, player);
        for (Attack a : attacks) {
            if (a.getDirection()[0] == 'u') {
                if (a.getDirection()[1] == 'u' || a.getDirection()[1] == 'd') {
                    a.move((int) a.determineXVelocity(Math.PI / 2, a.getSpeed()), (int) a.determineYVelocity(Math.PI / 2, a.getSpeed()));
                } else if (a.getDirection()[1] == 'r') {
                    a.move((int) a.determineXVelocity(Math.PI / 4, a.getSpeed()), (int) a.determineYVelocity(Math.PI / 4, a.getSpeed()));
                } else {
                    a.move((int) a.determineXVelocity(3*Math.PI / 4, a.getSpeed()), (int) a.determineYVelocity(3*Math.PI / 4, a.getSpeed()));
                }
            } else if (a.getDirection()[0] == 'r') {
                if (a.getDirection()[1] == 'r' || a.getDirection()[1] == 'l') {
                    a.move((int) a.determineXVelocity(0, a.getSpeed()), (int) a.determineYVelocity(0, a.getSpeed()));
                } else if (a.getDirection()[1] == 'u') {
                    a.move((int) a.determineXVelocity(Math.PI / 4, a.getSpeed()), (int) a.determineYVelocity(Math.PI / 4, a.getSpeed()));
                } else {
                    a.move((int) a.determineXVelocity(7*Math.PI / 4, a.getSpeed()), (int) a.determineYVelocity(7*Math.PI / 4, a.getSpeed()));
                }
            } else if (a.getDirection()[0] == 'd') {
                if (a.getDirection()[1] == 'd' || a.getDirection()[1] == 'u') {
                    a.move((int) a.determineXVelocity(3*Math.PI/2, a.getSpeed()), (int) a.determineYVelocity(3*Math.PI/2, a.getSpeed()));
                } else if (a.getDirection()[1] == 'l') {
                    a.move((int) a.determineXVelocity(5*Math.PI / 4, a.getSpeed()), (int) a.determineYVelocity(5*Math.PI / 4, a.getSpeed()));
                } else {
                    a.move((int) a.determineXVelocity(7*Math.PI / 4, a.getSpeed()), (int) a.determineYVelocity(7*Math.PI / 4, a.getSpeed()));
                }
            } else {
                if (a.getDirection()[1] == 'l' || a.getDirection()[1] == 'r') {
                    a.move((int) a.determineXVelocity(Math.PI, a.getSpeed()), (int) a.determineYVelocity(Math.PI, a.getSpeed()));
                } else if (a.getDirection()[1] == 'd') {
                    a.move((int) a.determineXVelocity(5*Math.PI / 4, a.getSpeed()), (int) a.determineYVelocity(5*Math.PI / 4, a.getSpeed()));
                } else {
                    a.move((int) a.determineXVelocity(3*Math.PI / 4, a.getSpeed()), (int) a.determineYVelocity(3*Math.PI / 4, a.getSpeed()));
                }
            }
        }
        if (!canAttack) {
            if (cooldown == 0) {
                canAttack = true;
                cooldown = 30;
            } else {
                cooldown -= 1;
            }
        }
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
