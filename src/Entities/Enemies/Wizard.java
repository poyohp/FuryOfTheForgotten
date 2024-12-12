package Entities.Enemies;

import Entities.Players.Player;
import Handlers.ImageHandler;
import Pathfinding.APathfinding;
import Pathfinding.Node;
import World.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Wizard extends Enemy{

    public boolean onPath, inRange;
    APathfinding pathFinder;
    Tile[][] tileset;

    // Variables for drawing
    private int animationState = 1, updateFrames = 7;
    private int columnWidth = 15, rowHeight = 21, column1 = 0, column2 = 16, column3 = 32, column4 = 48, row1 = 2, row2 = 26, row3 = 50, row4 = 74;
    BufferedImage wizard, wizardMove, wizardAttackFire, wizardAttack;

    public boolean attacking;

    void loadWizard() {
        wizard = ImageHandler.loadImage("src/Entities/Enemies/Loyalists/Wizard_idle.png");
        wizardMove = ImageHandler.loadImage("src/Entities/Enemies/Loyalists/Wizard_walk.png");
        wizardAttack = ImageHandler.loadImage("src/Entities/Enemies/Loyalists/Wizard_attack_NOhitbox.png");
        wizardAttackFire = ImageHandler.loadImage("src/Entities/Enemies/Loyalists/Wizard_attack.png");
    }

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
    public Wizard(int health, int speed, int width, int height, String name, int worldX, int worldY, int xOffset, int yOffset, int hitBoxWidth, int hitBoxHeight, Player player, Tile[][] tileset, boolean isFollowing) {
        super(health, speed, width, height, name, worldX, worldY, xOffset, yOffset, hitBoxWidth, hitBoxHeight, player, isFollowing);
        this.tileset = tileset;
        pathFinder = new APathfinding(tileset);
        this.onPath = true;
        this.inRange = false;
        this.vision = 700;
    }

    public void updateFrames() {
        if (updateFrames <= 0) {
            if (animationState >= 12) {
                animationState = 1;
            } else {
                animationState++;
            }
            updateFrames = 7;
        } else {
            updateFrames--;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        if (!attacking) {
            if (direction == 'd') {
                switch (animationState) {
                    case 1:
                        g2.drawImage(wizardMove, (int)this.screenX, (int)this.screenY, (int)(this.screenX + this.getWidth()), (int)(this.screenY + this.getHeight()), column1, row1, column1 + columnWidth, row1 + rowHeight, null);
                }
            }
        }
    }

    @Override
    public void move() {
        // If enemy is going to follow player
        if (onPath && !attacking) {
            int goalRow = (int) (entityToFollow.entityTop/ Tile.tileSize); //top row of the player
            int goalCol = (int) (entityToFollow.entityLeft/Tile.tileSize); //left row of the player

            searchPath(goalRow, goalCol);

        } //else: different random actions if the player is not in enemy vision
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

            // Move towards the right direction
            if (worldX > nextWorldX) {
                direction = 'l'; // left
                worldX -= getSpeed();
            }
            else if (worldX < nextWorldX) {
                direction = 'r'; // right
                worldX += getSpeed();
            }
            else if (worldY > nextWorldY) {
                direction = 'u'; // up
                worldY -= getSpeed();
            }
            else if (worldY < nextWorldY) {
                direction = 'd'; // down
                worldY += getSpeed();
            }
        }
    }

    @Override
    public void update() {
        super.update();
        if (playerInVision()) {
            attacking = true;
        } else {
            attacking = false;
        }
        updateFrames();
    }
}
