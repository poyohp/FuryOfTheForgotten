package Handlers;

import Entities.Players.Player;

public class AbilityHandler {

    Player player;
    KeyHandler keyHandler;
    int cooldown, abilityLength;
    boolean canAbility = true, inAbility = false;

    public AbilityHandler (Player player, KeyHandler keyHandler) {
        this.player = player;
        this.keyHandler = keyHandler;
        if (player.type == 's') {
            cooldown = 60;
            abilityLength = 10;
        } else if (player.type == 'g') {
            cooldown = 600;
            abilityLength = 300;
        }
    }

    public boolean checkAbility() {
        if (keyHandler.abilityPress && canAbility) {
            canAbility = false;
            inAbility = true;
            return true;
        } else {
            return false;
        }
    }

    public void ability() {
        if (player.type == 's') {
            player.setSpeed(player.getSpeed() * 5);
        } else if (player.type == 'g'){

        }
    }

    public void cancelAbility() {
        if (player.type == 's') {
            player.setSpeed(player.getSpeed() / 5);
            inAbility = false;
        } else if (player.type == 'g'){

        }
    }

    public void update() {
        System.out.println(player.getSpeed());
        if (checkAbility()) {
            ability();
        }

        if (inAbility) {
            if (abilityLength == 0) {
                if (player.type == 's') {
                    abilityLength = 10;
                    cancelAbility();
                } else if (player.type == 'g') {

                }
            } else {
                abilityLength--;
            }
        }

        if (!canAbility) {
            if (cooldown == 0) {
                cooldown = 60;
                canAbility = true;
            } else {
                cooldown--;
            }
        }

    }

}
