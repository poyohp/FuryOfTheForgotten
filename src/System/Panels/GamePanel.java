package System.Panels;

import Entities.Enemies.EternalSnail;
import Entities.Players.*;
import Handlers.AbilityHandler;
import Handlers.HUD.InventoryHandler;
import System.Main;

import Entities.Enemies.InstantKill;
import Handlers.Attacks.DamageDealer;
import Handlers.Attacks.AttackHandler;
import Handlers.CollisionHandler;
import Handlers.KeyHandler;
import Handlers.LevelHandler;
import Handlers.Spawners.SpawnHandler;
import Handlers.Spawners.SpawnPoint;
import Pathfinding.APathfinding;
import World.Map;
import World.Tile;


import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;


public class GamePanel extends JPanel implements Runnable{

    //FINAL variables
    public static final double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    public static final double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    public static final double FPS = 60;

    //Create objects for GAME RUNNING
    Thread gameThread;

    //Pathfinding object
    APathfinding pathfinding;

    //Create objects for GAME LOGIC
    public Player player;
    LevelHandler levelHandler;
    KeyHandler keyHandler;
    AttackHandler attackHandler;
    AbilityHandler abilityHandler;
    CollisionHandler collisionHandler;
    SpawnHandler spawnHandler;
    DamageDealer damageDealer;
    InventoryHandler inventory;
    InstantKill ghost;
    EternalSnail snail;

    public static Random random = new Random();

    Font spawnsRemaining = new Font("Arial", Font.PLAIN, 20);
    Font coinFont = new Font("Arial", Font.PLAIN, 50);


    /**
     * Constructor for the GamePanel - initializes all objects and starts the game
     */
    public GamePanel() {
        this.setDoubleBuffered(true);
        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        setFocusable(true);
        hideCursor();

        //Handling ALL LOADING
        keyHandler = new KeyHandler();
        spawnHandler = new SpawnHandler();
        damageDealer = new DamageDealer();

        initiatePlayerType();

        levelHandler = new LevelHandler(2, spawnHandler, player);
        collisionHandler = new CollisionHandler();
        attackHandler = new AttackHandler(keyHandler, levelHandler.getCurrentLevel().getMap().baseLayerTiles);
        abilityHandler = new AbilityHandler(player, keyHandler, collisionHandler);

        inventory = new InventoryHandler(keyHandler);

        pathfinding = new APathfinding(levelHandler.getCurrentLevel().getMap().baseLayerTiles);

        this.addKeyListener(keyHandler);

        player.updateWorldValues(spawnHandler.playerSpawnX, spawnHandler.playerSpawnY);

        Map currentMap = levelHandler.getCurrentLevel().getMap();
        ghost = new InstantKill(100, 6, Tile.tileSize, Tile.tileSize, "Invincible!", Tile.tileSize * 2, Tile.tileSize * 4, Tile.tileRatio,5*Tile.tileRatio, 70, 50, player, false);
        ghost.setBounds(1, 23, 1, 23, currentMap.getMapWidth(), currentMap.getMapHeight());

        snail = new EternalSnail(100, 0.4, Tile.tileSize, Tile.tileSize, "Snail", Tile.tileSize * 2, Tile.tileSize * 23, Tile.tileRatio,5*Tile.tileRatio, 70, 50, player, levelHandler.getCurrentLevel().getMap().baseLayerTiles, true);

        //Start game after loading all objects
        gameThread = new Thread(this);
        spawnHandler.startSpawning();
    }

    void initiatePlayerType() {
        if (CharacterSelectionPanel.selectedCharacter.equals("zombie")) player = new Zombie(100, 4, Tile.tileSize, Tile.tileSize, "Player", 0, 0, 4*Tile.tileSize/Tile.normalTileSize, 6*Tile.tileSize/Tile.normalTileSize-1, 8*Tile.tileSize/Tile.normalTileSize, 10*Tile.tileSize/Tile.normalTileSize, keyHandler);
        else if (CharacterSelectionPanel.selectedCharacter.equals("skeleton")) player = new Skeleton(100, 4, Tile.tileSize, Tile.tileSize, "Player", 0, 0, 4*Tile.tileSize/Tile.normalTileSize, 6*Tile.tileSize/Tile.normalTileSize-1, 8*Tile.tileSize/Tile.normalTileSize, 10*Tile.tileSize/Tile.normalTileSize, keyHandler);
        else if (CharacterSelectionPanel.selectedCharacter.equals("goblin")) player = new Goblin(100, 4, Tile.tileSize, Tile.tileSize, "Player", 0, 0, 4*Tile.tileSize/Tile.normalTileSize, 6*Tile.tileSize/Tile.normalTileSize-1, 8*Tile.tileSize/Tile.normalTileSize, 10*Tile.tileSize/Tile.normalTileSize, keyHandler);
        else if (CharacterSelectionPanel.selectedCharacter.equals("vampire")) player = new Vampire(100, 4, Tile.tileSize, Tile.tileSize, "Player", 0, 0, 4*Tile.tileSize/Tile.normalTileSize, 6*Tile.tileSize/Tile.normalTileSize-1, 8*Tile.tileSize/Tile.normalTileSize, 10*Tile.tileSize/Tile.normalTileSize, keyHandler);
        else System.out.println("Invalid character");
    }

    /**
     * Initiates the game panel and starts the game thread
     */
    public void initiateGamePanel() {
        this.setFocusable(true);
        this.requestFocusInWindow();
        gameThread.start();
    }

    /**
     * Hides the cursor (since it's not used in the game)
     */
    private void hideCursor() {
        this.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),
                "null"));
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
    }

    /**
     * Updates all the objects in the game
     */
    void update() {
        player.update(levelHandler.getCurrentLevel().getMap().baseLayerTiles);
        levelHandler.update(player, spawnHandler, damageDealer, collisionHandler, inventory);
        attackHandler.update(player, levelHandler.getCurrentLevel().enemies);
        abilityHandler.update();
        damageDealer.dealDamageToEnemies(attackHandler, levelHandler.getCurrentLevel());
        inventory.update();

        ghost.update();
        snail.update(levelHandler.getCurrentLevel().getMap().baseLayerTiles);
        if (ghost.hitbox.intersects(player.hitbox) || player.getHealth() <= 0 || snail.hitbox.intersects(player.hitbox)) {
            Main.updateGameState(3);
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

        g2.setFont(coinFont);
        levelHandler.draw(g2, player);
        player.draw(g2);
        abilityHandler.drawDecoy(g2);
        attackHandler.draw(g2, player);
        ghost.draw(g2);
        snail.draw(g2);

        for(SpawnPoint spawnPoint : spawnHandler.enemySpawnPoints) {
            spawnPoint.draw(g2, player);
        }

        g2.setFont(spawnsRemaining);
        g2.drawString("Active Spawns Remaining: " + spawnHandler.numActiveSpawns, (int)(screenWidth - 300), (int)(100));

        //HUD DRAWING
        inventory.draw(g2);
        player.healthHandler.drawHearts(g2);
    }
}
