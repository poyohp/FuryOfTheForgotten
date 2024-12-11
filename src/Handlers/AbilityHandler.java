package Handlers;

import Entities.Players.Player;

public class AbilityHandler {

    Player player;
    KeyHandler keyHandler;
    CollisionHandler collisionHandler;
    int cooldown, abilityLength;
    boolean canAbility = true;

    public AbilityHandler (Player player, KeyHandler keyHandler, CollisionHandler collisionHandler) {
        this.player = player;
        this.keyHandler = keyHandler;
        this.collisionHandler = collisionHandler;
        if (player.type == 's') {
            cooldown = 120;
            abilityLength = 35;
        } else if (player.type == 'g') {
            cooldown = 600;
            abilityLength = 300;
        }
    }

    public boolean checkAbility() {
        if (keyHandler.abilityPress && canAbility) {
            canAbility = false;
            player.inAbility = true;
            player.maxAnimationState = 5;
            player.animationState = 0;
            player.updateFrames = 7;
            return true;
        } else {
            return false;
        }
    }

    public void ability() {
        if (player.type == 's') {
            player.setSpeed(player.getSpeed() * 2);
        } else if (player.type == 'g'){

        }
    }

    public void cancelAbility() {
        if (player.type == 's') {
            player.setSpeed(player.getSpeed() / 2);
            player.maxAnimationState = 3;
            player.animationState = 1;
            player.updateFrames = 12;
            player.inAbility = false;
        } else if (player.type == 'g'){

        }
    }

    public void update() {
        System.out.println(player.getSpeed());
        if (checkAbility()) {
            ability();
        }

        if (player.inAbility) {
            if (abilityLength == 0) {
                if (player.type == 's') {
                    abilityLength = 35;
                    cancelAbility();
                } else if (player.type == 'g') {

                }
            } else {
                if (player.direction == 'u') {
                    if (!collisionHandler.playerWithTileCollision(player, player.tiles)) player.worldY -= player.getSpeed();
                } else if (player.direction == 'd') {
                    if (!collisionHandler.playerWithTileCollision(player, player.tiles)) player.worldY += player.getSpeed();
                } else if (player.direction == 'l') {
                    if (!collisionHandler.playerWithTileCollision(player, player.tiles)) player.worldX -= player.getSpeed();
                } else {
                    if (!collisionHandler.playerWithTileCollision(player, player.tiles)) player.worldX += player.getSpeed();
                }
                abilityLength--;
            }
        }

        if (!canAbility) {
            if (cooldown == 0) {
                cooldown = 120;
                canAbility = true;
            } else {
                cooldown--;
            }
        }

    }

}
