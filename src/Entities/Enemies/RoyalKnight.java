package Entities.Enemies;

import Entities.Players.Player;
import Handlers.Attacks.AttackHandler;
import Handlers.ImageHandler;
import Pathfinding.APathfinding;
import Pathfinding.Node;
import System.Panels.GamePanel;
import World.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class RoyalKnight extends Enemy{

    public boolean onPath;
    APathfinding pathFinder;
    private int updateFrames = 7, attackFrames = 87;
    Tile[][] tileset;
    boolean inRange;
    AttackHandler a;
    final int columnWidth = 96, rowHeight = 97;
    BufferedImage knight = ImageHandler.loadImage("Assets/Entities/Enemies/Boss/Elite Knight Sprite Sheet.png");
    BufferedImage swing = ImageHandler.loadImage("Assets/Entities/Enemies/Boss/Attack Sprite Sheet.png");


    /**
     * Enemy that follows player
     *
     * @param health       enemy health
     * @param speed        enemy speed
     * @param width        enemy width
     * @param height       enemy height
     * @param name         enemy name
     * @param worldX       world x position
     * @param worldY       world y position
     * @param xOffset      x offset for hitbox
     * @param yOffset      y offset for hitbox
     * @param hitBoxWidth  hitbox width
     * @param hitBoxHeight hitbox height
     * @param player       player to follow
     * @param isFollowing  whether enemy is following player or not
     */
    public RoyalKnight(double health, double speed, int width, int height, String name, int worldX, int worldY, int xOffset, int yOffset, int hitBoxWidth, int hitBoxHeight, Player player, Tile[][] tileset, boolean isFollowing, AttackHandler a) {
        super(health, speed, width, height, name, worldX, worldY, xOffset, yOffset, hitBoxWidth, hitBoxHeight, player, isFollowing);
        this.tileset = tileset;
        pathFinder = new APathfinding(tileset);
        this.onPath = true;
        inRange = false;
        this.a = a;
        hitbox.worldX += Tile.tileRatio;
        hitbox.worldY += 3 * Tile.tileRatio;
        hitbox.height += 10 * Tile.tileRatio;

        this.worldX = entityToFollow.worldX + 5*Tile.tileSize;
        this.worldY = entityToFollow.worldY;
        setScreenPosition();
    }

    @Override
    public void update() {
        updateEntityPosition();
        setScreenPosition();
        hitbox.update(this);

        if (!attacking) {
            move();
        }
    }

    public void updateFrames() {
        if (!attacking) {
            if (updateFrames <= 0) {
                if (animationState >= 7) {
                    animationState = 0;
                } else {
                    animationState++;
                }
                updateFrames = 7;
            } else {
                updateFrames--;
            }
        } else {
            if (attackFrames == 0) {
                attacking = false;
                //System.out.println("hi");
                attackFrames = 87;
            }

            if (attackFrames == 87) {
                animationState = 0;
                attackFrames--;
            } else {
                if (attackFrames % 4 == 0) {
                    animationState++;
                }
                attackFrames--;
            }
        }

    }

    public void attack(AttackHandler a) {
       if (attackFrames == 66) {
           if (entityToFollow.worldX >= worldX) {
               a.createBossAttack(4, 60 * Tile.tileRatio, 2 * Tile.tileRatio, 'r', this, 0, 0, 88);
           } else {
               a.createBossAttack(4, 60 * Tile.tileRatio, 2 * Tile.tileRatio, 'l', this, 0, 0, 88);
           }
       }
       attacking = true;
    }

    @Override
    public void draw(Graphics2D g2) {
        drawHealth(g2);
        /*
        g2.setColor(Color.BLACK);
        g2.fillRect((int)screenX, (int)screenY, getWidth(), getHeight());

         */

        if (!attacking) {
            if(entityToFollow.worldX >= worldX) {
                g2.drawImage(knight, (int) screenX - Tile.tileSize, (int) screenY - Tile.tileSize, (int) (screenX + getWidth() + Tile.tileSize), (int) (screenY + getHeight() + Tile.tileSize), animationState * columnWidth, rowHeight, animationState * columnWidth + columnWidth, rowHeight * 2, new Color(0, 0, 0, 0), null);
            } else {
                g2.drawImage(knight, (int) screenX - Tile.tileSize, (int) screenY - Tile.tileSize, (int) (screenX + getWidth() + Tile.tileSize), (int) (screenY + getHeight() + Tile.tileSize), animationState * columnWidth + columnWidth, rowHeight, animationState * columnWidth, rowHeight * 2, new Color(0, 0, 0, 0), null);
            }
        } else {
            if(entityToFollow.worldX >= worldX) {
                g2.drawImage(swing, (int) screenX - 20*Tile.tileRatio, (int) screenY - Tile.tileSize - Tile.tileRatio, (int) (screenX + getWidth() + 69*Tile.tileRatio), (int) (screenY + getHeight() + Tile.tileSize - Tile.tileRatio), 95, animationState * 96, 265, animationState * 96 + 96, new Color(0, 0, 0, 0), null);
            } else {
                g2.drawImage(swing, (int) screenX - 69*Tile.tileRatio, (int) screenY - Tile.tileSize - Tile.tileRatio, (int) (screenX + getWidth() + 28*Tile.tileRatio - Tile.tileSize), (int) (screenY + getHeight() + Tile.tileSize - Tile.tileRatio), 265, animationState * 96, 95, animationState * 96 + 96, new Color(0, 0, 0, 0), null);
            }
        }

        hitbox.draw(g2);

        updateFrames();
    }

    @Override
    public void move() {
        double centerX = worldX + (double) getWidth() /2;
        double centerY = worldY + (double) getHeight() /2;
        double entityX = entityToFollow.worldX + (double) entityToFollow.getWidth() /2;
        double entityY = entityToFollow.worldY + (double) entityToFollow.getHeight() /2;
        double angle;

        if (entityX > centerX && entityY > centerY) {
            angle = (2*Math.PI) - Math.atan(((entityY - centerY) / (entityX - centerX)));
        } else if (entityX > centerX && entityY < centerY) {
            angle = Math.atan((centerY - entityY) / (entityX - centerX));
        } else if (entityX < centerX && entityY > centerY) {
            angle = Math.PI + Math.atan(((entityY - centerY) / (centerX - entityX)));
        } else {
            angle = Math.PI - Math.atan(((centerY - entityY) / (centerX - entityX)));
        }

        System.out.println(angle);

        worldX += (int)(getSpeed() * Math.cos(angle));
        worldY += (int)(-getSpeed() * Math.sin(angle));
    }

    @Override
    public void hitPlayer() {
        hitPlayer = true;
        //freezeTimer = freezeTimerFrames;
    }

    @Override
    public void isHit(double damage) {
        if(!isHit) {
            this.setHealth(this.getHealth() - damage);
            hitPlayer = !hitPlayer;
            isHit = true;
            freezeTimer = (int)freezeTimerFrames;
        }
    }

    @Override
    public void drawHealth(Graphics2D g2) {
        double fillPercentage = getHealth() / originalHealth; // Health percentage remaining
        int width = (int) (GamePanel.screenWidth - Tile.tileRatio*30);
        int barLength = (int) (fillPercentage * width); // Draw bar based on percentage remaining

        // Bar fill
        g2.setColor(Color.GREEN);
        g2.fillRect(Tile.tileRatio*15, (int) Tile.tileRatio*5, barLength, 10 * Tile.tileRatio);

        // Bar outline
        if(getHealth() > 0) {
            g2.setColor(Color.BLACK);
            g2.drawRect(Tile.tileRatio*15, (int) Tile.tileRatio*5, width, 10 * Tile.tileRatio);
        }

    }


    
}
