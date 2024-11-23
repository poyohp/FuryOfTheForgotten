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
        if (keyHandler.attackPress && canAttack) {
            createMelee(5, 50, 100, 'u', player, 0, 0, 30);
            canAttack = false;
        }
    }


    public void update(Player player) {
        checkForPlayerAttack(keyHandler, player);
        for (Attack a : attacks) {
            if (player.direction == 'u') {
                a.move((int)a.determineXVelocity(90, a.getSpeed()), (int)a.determineYVelocity(90, a.getSpeed()));
            } else if (player.direction == 'r') {
                a.move((int)a.determineXVelocity(0, a.getSpeed()), (int)a.determineYVelocity(0, a.getSpeed()));
            } else if (player.direction == 'd') {
                a.move((int)a.determineXVelocity(270, a.getSpeed()), (int)a.determineYVelocity(270, a.getSpeed()));
            } else {
                a.move((int)a.determineXVelocity(180, a.getSpeed()), (int)a.determineYVelocity(180, a.getSpeed()));
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
