package Handlers;

import Entities.Players.Decoy;
import Entities.Players.Player;

import java.awt.*;
import java.util.ArrayList;

public class AbilityHandler {

    Player player;
    KeyHandler keyHandler;
    CollisionHandler collisionHandler;
    int cooldown, abilityLength;
    boolean canAbility = true;
    ArrayList<Decoy> decoys = new ArrayList<Decoy>();

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
            if (player.type == 's') {
                player.maxAnimationState = 5;
                player.animationState = 0;
                player.updateFrames = 7;
            } else if (player.type == 'g'){

            }
            return true;
        } else {
            return false;
        }
    }

    public void ability() {
        if (player.type == 's') {
            player.setSpeed(player.getSpeed() * 2);
        } else if (player.type == 'g'){
            decoys.add(new Decoy(player));
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
            decoys.clear();
        }
    }

    public void update() {
        if (checkAbility()) {
            ability();
        }

        if (player.inAbility) {
            if (abilityLength == 0) {
                if (player.type == 's') {
                    abilityLength = 35;
                } else if (player.type == 'g') {
                    abilityLength = 300;
                }
                cancelAbility();
            } else {
                if (player.type == 's') {
                    if (player.direction == 'u') {
                        if (!collisionHandler.playerWithTileCollision(player, player.tiles))
                            player.worldY -= player.getSpeed();
                    } else if (player.direction == 'd') {
                        if (!collisionHandler.playerWithTileCollision(player, player.tiles))
                            player.worldY += player.getSpeed();
                    } else if (player.direction == 'l') {
                        if (!collisionHandler.playerWithTileCollision(player, player.tiles))
                            player.worldX -= player.getSpeed();
                    } else {
                        if (!collisionHandler.playerWithTileCollision(player, player.tiles))
                            player.worldX += player.getSpeed();
                    }
                } else if (player.type == 'g') {
                    if (!decoys.isEmpty()) {
                        decoys.getFirst().update();
                    }
                }
                abilityLength--;
            }
        }

        if (!canAbility) {
            if (cooldown == 0) {
                if (player.type == 's') {
                    cooldown = 120;
                } else if (player.type == 'g') {
                    cooldown = 600;
                }
                canAbility = true;
            } else {
                cooldown--;
            }
        }

    }

    public void drawDecoy(Graphics2D g2) {
        if (!decoys.isEmpty()) {
            decoys.getFirst().draw(g2);
        }
    }

}
