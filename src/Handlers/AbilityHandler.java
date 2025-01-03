package Handlers;

import Entities.Enemies.Enemy;
import Entities.Players.Decoy;
import Entities.Players.Player;
import Handlers.Attacks.AttackHandler;
import Handlers.HUD.HealthHandler;
import World.Level;
import World.Tile;

import java.awt.*;
import java.util.ArrayList;

public class AbilityHandler {

    Player player;
    Level level;
    KeyHandler keyHandler;
    CollisionHandler collisionHandler;
    HealthHandler healthHandler;
    AttackHandler attackHandler;
    int cooldown, abilityLength;
    boolean canAbility = true;
    ArrayList<Decoy> decoys = new ArrayList<Decoy>();

    public AbilityHandler (Player player, KeyHandler keyHandler, CollisionHandler collisionHandler, Level level, HealthHandler healthHandler, AttackHandler attackHandler) {
        this.player = player;
        this.keyHandler = keyHandler;
        this.collisionHandler = collisionHandler;
        this.healthHandler = healthHandler;
        this.attackHandler = attackHandler;
        this.level = level;
        if (player.type == 's') {
            cooldown = 120;
            abilityLength = 35;
        } else if (player.type == 'g') {
            cooldown = 600;
            abilityLength = 300;
        } else if (player.type == 'z') {
            cooldown = 600;
            abilityLength = 47;
        } else if (player.type == 'v') {
            cooldown = 300;
            abilityLength = 1;
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
            } else if (player.type == 'z'){
                player.animationState = 0;
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
            for (Enemy e : level.enemies) {
                e.entityToFollow = decoys.getFirst();
            }
        } else if (player.type == 'v') {
            if (player.direction == 'r') {
                attackHandler.createPlayerBloodOrb(8 * Tile.tileRatio, 3 * Tile.tileRatio, player.direction, player, 0, 0, 150, 5, 0, level.enemies);
                attackHandler.createPlayerBloodOrb(8 * Tile.tileRatio, 3 * Tile.tileRatio, player.direction, player, 0, 0, 150, 5, 15 * Math.PI / 8, level.enemies);
                attackHandler.createPlayerBloodOrb(8 * Tile.tileRatio, 3 * Tile.tileRatio, player.direction, player, 0, 0, 150, 5, Math.PI / 8, level.enemies);
            } else if (player.direction == 'u') {
                attackHandler.createPlayerBloodOrb(8 * Tile.tileRatio, 3 * Tile.tileRatio, player.direction, player, 0, 0, 150, 5, Math.PI / 2, level.enemies);
                attackHandler.createPlayerBloodOrb(8 * Tile.tileRatio, 3 * Tile.tileRatio, player.direction, player, 0, 0, 150, 5, 3 * Math.PI / 8, level.enemies);
                attackHandler.createPlayerBloodOrb(8 * Tile.tileRatio, 3 * Tile.tileRatio, player.direction, player, 0, 0, 150, 5, 5 * Math.PI / 8, level.enemies);
            } else if (player.direction == 'l') {
                attackHandler.createPlayerBloodOrb(8 * Tile.tileRatio, 3 * Tile.tileRatio, player.direction, player, 0, 0, 150, 5, Math.PI, level.enemies);
                attackHandler.createPlayerBloodOrb(8 * Tile.tileRatio, 3 * Tile.tileRatio, player.direction, player, 0, 0, 150, 5, 7 * Math.PI / 8, level.enemies);
                attackHandler.createPlayerBloodOrb(8 * Tile.tileRatio, 3 * Tile.tileRatio, player.direction, player, 0, 0, 150, 5, 9 * Math.PI / 8, level.enemies);
            } else {
                attackHandler.createPlayerBloodOrb(8 * Tile.tileRatio, 3 * Tile.tileRatio, player.direction, player, 0, 0, 150, 5, 3 * Math.PI / 2, level.enemies);
                attackHandler.createPlayerBloodOrb(8 * Tile.tileRatio, 3 * Tile.tileRatio, player.direction, player, 0, 0, 150, 5, 11 * Math.PI / 8, level.enemies);
                attackHandler.createPlayerBloodOrb(8 * Tile.tileRatio, 3 * Tile.tileRatio, player.direction, player, 0, 0, 150, 5, 13 * Math.PI / 8, level.enemies);
            }
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
            for (Enemy e : level.enemies) {
                e.entityToFollow = player;
            }
            decoys.clear();
            player.inAbility = false;
        } else if (player.type == 'z') {
            player.inAbility = false;
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
                } else if (player.type == 'z') {
                    abilityLength = 48;
                    healthHandler.heal(4.0);
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
                } else if (player.type == 'g' || player.type == 'z') {
                    cooldown = 600;
                } else if (player.type == 'v') {
                    cooldown = 300;
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
