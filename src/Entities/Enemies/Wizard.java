package Entities.Enemies;

import Entities.Player;
import Handlers.ImageHandler;
import Pathfinding.APathfinding;
import Pathfinding.Node;
import World.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Wizard extends Enemy{

    public boolean onPath;
    APathfinding pathFinder;
    Tile[][] tileset;


    // Variables for drawing
    private int animationState = 1, updateFrames = 7;
    private int columnWidth, columnHeight;
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
    }

    @Override
    public void draw(Graphics2D g2) {

    }

    @Override
    public void move() {
        // If enemy is going to follow player
        if (onPath) {
            int goalRow = (int) (player.entityTop/ Tile.tileSize); //top row of the player
            int goalCol = (int) (player.entityLeft/Tile.tileSize); //left row of the player

            searchPath(goalRow, goalCol);

        } //else: different random actions if the player is not in enemy vision
    }

    void checkAttack() {

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
}
