package System.Panels;

import Entities.Enemies.EternalSnail;
import Entities.Enemies.RoyalKnight;
import Entities.Players.*;
import Handlers.*;
import Handlers.HUD.InventoryHandler;
import System.Main;

import Entities.Enemies.InstantKill;
import Handlers.Attacks.DamageDealer;
import Handlers.Attacks.AttackHandler;
import Handlers.Spawners.SpawnHandler;
import Pathfinding.APathfinding;
import World.Map;
import World.Tile;

import java.awt.image.BufferedImage;
import java.util.Random;



import javax.swing.JPanel;
import java.awt.*;
import java.util.Random;
import java.util.Timer;


public class GamePanel extends JPanel implements Runnable{

    //FINAL variables
    public static final double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    public static final double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    public static final double FPS = 60;
    BufferedImage background1 = ImageHandler.loadImage("Assets/Backgrounds/Level1Background.png");
    BufferedImage background2 = ImageHandler.loadImage("Assets/Backgrounds/Level2Background.png");
    BufferedImage background3 = ImageHandler.loadImage("Assets/Backgrounds/Level3Background.png");
    BufferedImage background4 = ImageHandler.loadImage("Assets/Backgrounds/Level4Background.png");
    BufferedImage background5 = ImageHandler.loadImage("Assets/Backgrounds/Level5Background.png");
    BufferedImage backgroundBoss = ImageHandler.loadImage("Assets/Backgrounds/BossLevelBackground.png");

    //Create objects for GAME RUNNING
    Thread gameThread;
    public boolean isPaused;

    //Pathfinding object
    APathfinding pathfinding;

    //Create objects for GAME LOGIC
    public Player player;
    LevelHandler levelHandler;
    ObjectHandler objectHandler;
    KeyHandler keyHandler;
    AttackHandler attackHandler;
    AbilityHandler abilityHandler;
    CollisionHandler collisionHandler;
    SpawnHandler spawnHandler;
    DamageDealer damageDealer;
    MusicHandler musicHandler;
    InventoryHandler inventory;


    public InstantKill ghost;
    public EternalSnail snail;
    RoyalKnight royalKnight;

    public static Random random = new Random();

    public Timer timer = new Timer();
    public long startTime;
    public long pauseTime;
    public long totalPauseTime;
    public static double totalTimePlayed;

    /**
     * Constructor for the GamePanel - initializes all objects and starts the game
     */
    public GamePanel() {
        isPaused = false;



        this.setDoubleBuffered(true);
        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        setFocusable(true);
        Main.hideCursor(this);

        //Handling ALL LOADING
        keyHandler = new KeyHandler();
        spawnHandler = new SpawnHandler();

        initiatePlayerType();

        objectHandler = new ObjectHandler();
        musicHandler = new MusicHandler();
        levelHandler = new LevelHandler(6, spawnHandler, player, musicHandler);
        collisionHandler = new CollisionHandler();
        damageDealer = new DamageDealer(collisionHandler);
        attackHandler = new AttackHandler(keyHandler, levelHandler.getCurrentLevel().getMap().baseLayerTiles, levelHandler.getCurrentLevel());
        abilityHandler = new AbilityHandler(player, keyHandler, collisionHandler, levelHandler.getCurrentLevel(), player.healthHandler, attackHandler);

        inventory = new InventoryHandler(keyHandler, player);

        pathfinding = new APathfinding(levelHandler.getCurrentLevel().getMap().baseLayerTiles);

        this.addKeyListener(keyHandler);

        player.updateWorldValues(spawnHandler.playerSpawnX, spawnHandler.playerSpawnY);

        Map currentMap = levelHandler.getCurrentLevel().getMap();
        ghost = new InstantKill(100, 6, Tile.tileSize, Tile.tileSize, "Invincible!", Tile.tileSize * 2, Tile.tileSize * 4, Tile.tileRatio,5*Tile.tileRatio, 70, 50, player, false);
        ghost.setBounds(1, 23, 1, 23, currentMap.getMapWidth(), currentMap.getMapHeight());

        snail = new EternalSnail(100, 0.4, Tile.tileSize, Tile.tileSize, "Snail", (int) (player.worldX * Tile.tileSize) - 2 * Tile.tileSize, (int) (player.worldY * Tile.tileSize) + Tile.tileSize, Tile.tileRatio,5*Tile.tileRatio, 70, 50, player, levelHandler.getCurrentLevel().getMap().baseLayerTiles, true);

        //Start game after loading all objects
        gameThread = new Thread(this);
        spawnHandler.startSpawning();
    }

    void initiatePlayerType() {
        if (CharacterSelectionPanel.selectedCharacter.equals("zombie")) player = new Zombie(6, 5, Tile.tileSize, Tile.tileSize, "Zombie", 0, 0, 4*Tile.tileSize/Tile.normalTileSize, 6*Tile.tileSize/Tile.normalTileSize-1, 8*Tile.tileSize/Tile.normalTileSize, 10*Tile.tileSize/Tile.normalTileSize, keyHandler);
        else if (CharacterSelectionPanel.selectedCharacter.equals("skeleton")) player = new Skeleton(6, 6, Tile.tileSize, Tile.tileSize, "Skeleton", 0, 0, 4*Tile.tileSize/Tile.normalTileSize, 6*Tile.tileSize/Tile.normalTileSize-1, 8*Tile.tileSize/Tile.normalTileSize, 10*Tile.tileSize/Tile.normalTileSize, keyHandler);
        else if (CharacterSelectionPanel.selectedCharacter.equals("goblin")) player = new Goblin(6, 5, Tile.tileSize, Tile.tileSize, "Goblin", 0, 0, 4*Tile.tileSize/Tile.normalTileSize, 6*Tile.tileSize/Tile.normalTileSize-1, 8*Tile.tileSize/Tile.normalTileSize, 10*Tile.tileSize/Tile.normalTileSize, keyHandler);
        else if (CharacterSelectionPanel.selectedCharacter.equals("vampire")) player = new Vampire(6, 5, Tile.tileSize, Tile.tileSize, "Vampire", 0, 0, 4*Tile.tileSize/Tile.normalTileSize, 6*Tile.tileSize/Tile.normalTileSize-1, 8*Tile.tileSize/Tile.normalTileSize, 9*Tile.tileSize/Tile.normalTileSize, keyHandler);
        else System.out.println("Invalid character");
    }

    void drawBackground(Graphics2D g2) {
        if (levelHandler.getCurrentLevel() == levelHandler.levels[0]) {
            g2.drawImage(background1, 0, 0, (int)screenWidth, (int)screenHeight, null);
        } else if (levelHandler.getCurrentLevel() == levelHandler.levels[1]) {
            g2.drawImage(background2, 0, 0, (int)screenWidth, (int)screenHeight, null);
        } else if (levelHandler.getCurrentLevel() == levelHandler.levels[2]) {
            g2.drawImage(background3, 0, 0, (int)screenWidth, (int)screenHeight, null);
        } else if (levelHandler.getCurrentLevel() == levelHandler.levels[3]) {
            g2.drawImage(background4, 0, 0, (int)screenWidth, (int)screenHeight, null);
        } else if (levelHandler.getCurrentLevel() == levelHandler.levels[4]) {
            g2.drawImage(background5, 0, 0, (int)screenWidth, (int)screenHeight, null);
        } else if (levelHandler.getCurrentLevel() == levelHandler.levels[5]) {
            g2.drawImage(backgroundBoss, 0, 0, (int)screenWidth, (int)screenHeight, null);
        }
    }

    /**
     * Initiates the game panel and starts the game thread
     */
    public void initiateGamePanel() {
        this.setFocusable(true);
        this.requestFocusInWindow();
        musicHandler.getClip(0);
        musicHandler.play();
        gameThread.start();
    }

    public void pauseGame() {
        //REMOVES KEY FROM INVENTORY!!
        for(int i = 0; i < inventory.inventory.length; i++) {
            if(inventory.inventory[i] != null && inventory.inventory[i].name.equalsIgnoreCase("Key")) {
                inventory.inventory[i] = null;
            }
        }
        isPaused = true;
        pauseTime = System.nanoTime();
    }

    public void resumeGame() {
        this.requestFocusInWindow();
        totalPauseTime += System.nanoTime() - pauseTime;
        isPaused = false;
    }


    /**
     * Run method for the game - handles the game loop
     */
    @Override
    public void run() {
        //FPS Handling
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        try {
            while(gameThread.isAlive()) {
                currentTime = System.nanoTime();
                delta += (currentTime - lastTime) / drawInterval;
                lastTime = currentTime;
                if(delta > 1) {
                    //Updates, then draws
                    update();
                    repaint();
                    delta--;
                }
            }
        } catch(Exception e) {
            System.exit(0);
        }
    }

    /**
     * Updates all the objects in the game
     */
    void update() {
        if(!isPaused) {
            player.update(levelHandler.getCurrentLevel().getMap().baseLayerTiles, levelHandler.getCurrentLevel());
            objectHandler.update(collisionHandler, player, inventory, levelHandler.getCurrentLevel());
            levelHandler.update(player, spawnHandler, damageDealer, collisionHandler, attackHandler, ghost, snail);
            attackHandler.update(player, levelHandler.getCurrentLevel());
            abilityHandler.update();
            damageDealer.dealDamageToEnemies(attackHandler, levelHandler.getCurrentLevel(), player);
            damageDealer.dealMeleeDamageToPlayer(attackHandler, player);
            inventory.update(keyHandler);
            ghost.update(levelHandler);
            snail.update(levelHandler);
        } else {
            player.healthHandler.poisonedHealth = false;
        }
    }


    /**
     * Draws all the graphics
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawBackground(g2);

        levelHandler.draw(g2, player, spawnHandler, keyHandler);
        levelHandler.getCurrentLevel().drawItems(g2);

        attackHandler.draw(g2);
        ghost.draw(g2);
        snail.draw(g2);

        spawnHandler.drawSpawns(g2, player);

        player.draw(g2);

        //HUD DRAWING
        inventory.draw(g2, inventory.inventoryFinalDrawSize);
        player.healthHandler.drawHealth(g2);
        objectHandler.draw(g2, player, keyHandler);
        abilityHandler.draw(g2);
    }
}
