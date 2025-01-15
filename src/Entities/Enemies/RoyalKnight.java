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
    private int updateFrames = 7, attackFrames = 87, attackAnimationState = 0;
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
        hitbox.height += 10 * Tile.tileRatio;


        this.worldX = entityToFollow.worldX + 16*Tile.tileSize;
        this.worldY = entityToFollow.worldY;
        setScreenPosition();
    }


    @Override
    public void update() {
        if (getHealth() <= originalHealth/2) {
            phase2 = true;
            setSpeed(4);
        }
        updateEntityPosition();
        setScreenPosition();
        hitbox.update(this);
        unHitPlayer();
        if (!attacking) move();
        if (!a.enemyAttacks.isEmpty()) {
            for (int i = 0; i < a.enemyAttacks.size(); i++) {
                if (a.enemyAttacks.get(i).type == 's') {
                    if (attackFrames == 66) {
                        a.enemyAttacks.get(i).isActive = true;
                    }
                }
            }
        }
        //else System.out.println(attackFrames);
    }

    private void unHitPlayer() {
        if(hitPlayer) {
            if(freezeTimer > 0) {
                freezeTimer--;
            } else {
                hitPlayer = false;
                isHit = false;
            }
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
                attackAnimationState = 0;
            }


            if (attackFrames == 87) {
                animationState = 0;
                attackFrames--;
            } else {
                if (attackFrames % 4 == 0) {
                    attackAnimationState++;
                }
                attackFrames--;
            }
        }


    }


    public void attack(AttackHandler a) {
        if (entityToFollow.worldX >= worldX) {
            a.createBossAttack(1.5, 70 * Tile.tileRatio, 20 * Tile.tileRatio, 'r', this, 0, 0, 66);
        } else {
            a.createBossAttack(1.5, 70 * Tile.tileRatio, 20 * Tile.tileRatio, 'l', this, 0, 0, 66);
        }

        for (int i = 0; i < a.enemyAttacks.size(); i++) {
            if (a.enemyAttacks.get(i).type == 's') {
                a.enemyAttacks.get(i).isActive = false;
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
                g2.drawImage(swing, (int) screenX - 20*Tile.tileRatio, (int) screenY - Tile.tileSize - Tile.tileRatio, (int) (screenX + getWidth() + 69*Tile.tileRatio), (int) (screenY + getHeight() + Tile.tileSize - Tile.tileRatio), 95, attackAnimationState * 96, 265, attackAnimationState * 96 + 96, new Color(0, 0, 0, 0), null);
            } else {
                g2.drawImage(swing, (int) screenX - 64*Tile.tileRatio, (int) screenY - Tile.tileSize - Tile.tileRatio, (int) (screenX + getWidth() + 32*Tile.tileRatio - Tile.tileSize), (int) (screenY + getHeight() + Tile.tileSize - Tile.tileRatio), 265, attackAnimationState * 96, 95, attackAnimationState * 96 + 96, new Color(0, 0, 0, 0), null);
            }
        }


        hitbox.draw(g2);


        updateFrames();
    }


    @Override
    public void move() {


        // If enemy is going to follow player
        if (onPath) {


            // Checks whether the knight is closer to the right side or left side of the player
            double xLeftDistance = Math.abs(this.screenX - entityToFollow.entityLeft);
            double xRightDistance = Math.abs(this.screenX - entityToFollow.entityRight);


            int goalRow;
            int goalCol;


            if (xLeftDistance > xRightDistance) { // Go to right side of player
                goalRow = (int) (entityToFollow.entityTop/ Tile.tileSize); //top row of the player
                goalCol = (int) (entityToFollow.entityRight/Tile.tileSize); //right side of the player
            } else { // Go to the left side of player
                goalRow = (int) (entityToFollow.entityTop/ Tile.tileSize); //top row of the player
                goalCol = (int) (entityToFollow.entityLeft/Tile.tileSize); //left side of the player
            }


            searchPath(goalRow, goalCol);
        }
    }


    /**
     * Searches for a path to the player
     * @param goalRow Player's row
     * @param goalCol Player's column
     */
    public void searchPath(int goalRow, int goalCol) {


        int startRow = (int) (this.entityTop/Tile.tileSize); //top row of the enemy
        int startCol = (int) (this.entityLeft/Tile.tileSize); //left row of the enemy


        pathFinder.setNodes(tileset[startRow][startCol], tileset[goalRow][goalCol]);


        // If a path is found, move towards the player
        if (pathFinder.findPath()) {
            ArrayList<Node> path = pathFinder.shortestPath; // List of tiles to go to


            // Next tile to go to
            double nextCol = path.get(0).col;
            double nextRow = path.get(0).row;


            // Next x and y position to go to (calculated with tile size)
            double nextWorldX = nextCol * Tile.tileSize;
            double nextWorldY = nextRow * Tile.tileSize;


            // Ensures that enemy does not move when it is too close or too far from player
            if (path.size() < 4 && startRow == goalRow) {




                // Turn in direction of player, even if not moving toward them
                if (worldX > nextWorldX) direction = 'l'; // left
                else if (worldX < nextWorldX) direction = 'r'; // right


                attack(a);
                return; // Does not move to player
            }


            getNewPosition(nextWorldX, nextWorldY);


        } else {
            moveTowardPlayer();
        }
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
            freezeTimer = (int) freezeTimerFrames;
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
