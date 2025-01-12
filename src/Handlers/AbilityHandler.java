package Handlers;

import Entities.Enemies.Enemy;
import Entities.Players.Decoy;
import Entities.Players.Player;
import Handlers.Attacks.AttackHandler;
import Handlers.HUD.HealthHandler;
import World.Level;
import World.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static System.Panels.HelpPanel.screenHeight;
import static System.Panels.HelpPanel.screenWidth;

public class AbilityHandler {

    Player player;
    Level level;
    KeyHandler keyHandler;
    CollisionHandler collisionHandler;
    HealthHandler healthHandler;
    AttackHandler attackHandler;
    int cooldown, abilityLength, maxCooldown;
    boolean canAbility = true;
    ArrayList<Decoy> decoys = new ArrayList<Decoy>();
    BufferedImage abilityIcon;
    int abilityIconSize = Tile.tileMultipler * Tile.normalTileSize;

    public AbilityHandler (Player player, KeyHandler keyHandler, CollisionHandler collisionHandler, Level level, HealthHandler healthHandler, AttackHandler attackHandler) {
        this.player = player;
        this.keyHandler = keyHandler;
        this.collisionHandler = collisionHandler;
        this.healthHandler = healthHandler;
        this.attackHandler = attackHandler;
        this.level = level;
        if (player.type == 's') {
            maxCooldown = 120;
            abilityLength = 35;
            abilityIcon = ImageHandler.loadImage("Assets/Entities/Players/Skeleton/ability_icon.png");
        } else if (player.type == 'g') {
            maxCooldown = 600;
            abilityLength = 300;
            abilityIcon = ImageHandler.loadImage("Assets/Entities/Players/Goblin/ability_icon.png");
        } else if (player.type == 'z') {
            maxCooldown = 600;
            abilityLength = 46;
            abilityIcon = ImageHandler.loadImage("Assets/Entities/Players/Zombie/ability_icon.png");
        } else if (player.type == 'v') {
            maxCooldown = 20;
            abilityLength = 1;
            abilityIcon = ImageHandler.loadImage("Assets/Entities/Players/Vampire/ability_icon.png");
        }
        cooldown = maxCooldown;
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
            for (Enemy e : level.contactEnemies) {
                e.entityToFollow = decoys.getFirst();
            }
            for (Enemy e : level.archerEnemies) {
                e.entityToFollow = decoys.getFirst();
            }
        } else if (player.type == 'v') {
            if (player.direction == 'r') {
                attackHandler.createPlayerBloodOrb(7 * Tile.tileRatio, 4 * Tile.tileRatio, player.direction, player, 0, 0, 150, 5, 0, level.contactEnemies);
                attackHandler.createPlayerBloodOrb(7 * Tile.tileRatio, 4 * Tile.tileRatio, player.direction, player, 0, 0, 150, 5, 15 * Math.PI / 8, level.contactEnemies);
                attackHandler.createPlayerBloodOrb(7 * Tile.tileRatio, 4 * Tile.tileRatio, player.direction, player, 0, 0, 150, 5, Math.PI / 8, level.contactEnemies);
            } else if (player.direction == 'u') {
                attackHandler.createPlayerBloodOrb(7 * Tile.tileRatio, 4 * Tile.tileRatio, player.direction, player, 0, 0, 150, 5, Math.PI / 2, level.contactEnemies);
                attackHandler.createPlayerBloodOrb(7 * Tile.tileRatio, 4 * Tile.tileRatio, player.direction, player, 0, 0, 150, 5, 3 * Math.PI / 8, level.contactEnemies);
                attackHandler.createPlayerBloodOrb(7 * Tile.tileRatio, 4 * Tile.tileRatio, player.direction, player, 0, 0, 150, 5, 5 * Math.PI / 8, level.contactEnemies);
            } else if (player.direction == 'l') {
                attackHandler.createPlayerBloodOrb(7 * Tile.tileRatio, 4 * Tile.tileRatio, player.direction, player, 0, 0, 150, 5, Math.PI, level.contactEnemies);
                attackHandler.createPlayerBloodOrb(7 * Tile.tileRatio, 4 * Tile.tileRatio, player.direction, player, 0, 0, 150, 5, 7 * Math.PI / 8, level.contactEnemies);
                attackHandler.createPlayerBloodOrb(7 * Tile.tileRatio, 4 * Tile.tileRatio, player.direction, player, 0, 0, 150, 5, 9 * Math.PI / 8, level.contactEnemies);
            } else {
                attackHandler.createPlayerBloodOrb(7 * Tile.tileRatio, 4 * Tile.tileRatio, player.direction, player, 0, 0, 150, 5, 3 * Math.PI / 2, level.contactEnemies);
                attackHandler.createPlayerBloodOrb(7 * Tile.tileRatio, 4 * Tile.tileRatio, player.direction, player, 0, 0, 150, 5, 11 * Math.PI / 8, level.contactEnemies);
                attackHandler.createPlayerBloodOrb(7 * Tile.tileRatio, 4 * Tile.tileRatio, player.direction, player, 0, 0, 150, 5, 13 * Math.PI / 8, level.contactEnemies);
            }
        }
    }

    public void cancelAbility() {
        if (player.type == 's') {
            if(player.isSpeedBoost) {
                player.setSpeed(player.boostedSpeed);
            } else player.setSpeed(player.origSpeed);
            player.maxAnimationState = 3;
            player.animationState = 1;
            player.updateFrames = 12;
            player.inAbility = false;
        } else if (player.type == 'g'){
            for (Enemy e : level.contactEnemies) {
                e.entityToFollow = player;
            }
            for (Enemy e : level.archerEnemies) {
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
            if (player.type != 'v') {
                ability();
            } else {
                if (player.bloodBarState != 0) {
                    ability();
                    player.bloodBarState--;
                }
            }
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
                cooldown = maxCooldown;
                canAbility = true;
            } else {
                cooldown--;
            }
        }

    }

    public void draw(Graphics2D g2) {
        g2.setColor(new Color(0,0,0,0));

        if (!decoys.isEmpty()) {
            decoys.getFirst().draw(g2);
        }

        g2.setColor(Color.WHITE);

        g2.fillRect((int) (screenWidth - abilityIconSize * 1.4), (int) (screenHeight - abilityIconSize * 2.7), abilityIconSize, abilityIconSize);

        if (cooldown != maxCooldown) {
            double cooldownRatio = (double) cooldown / (double) maxCooldown;
            g2.setColor(Color.GRAY);
            g2.fillRect((int) (screenWidth - abilityIconSize * 1.4),  (int) (screenHeight - abilityIconSize * 2.7) + (abilityIconSize - ((int) (cooldownRatio * (abilityIconSize)))), abilityIconSize, (int) (cooldownRatio * abilityIconSize));
        }

        g2.drawImage(abilityIcon, (int) (screenWidth - abilityIconSize * 1.3), (int) (screenHeight - abilityIconSize * 2.6), (int)(abilityIconSize * 0.8), (int)(abilityIconSize * 0.8), new Color(0,0,0,0), null);

    }

}
