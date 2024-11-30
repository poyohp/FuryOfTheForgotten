package System;


import Attacks.Ranged;
import Entities.Enemy;
import Entities.Player;
import Handlers.AttackHandler;
import Handlers.CollisionHandler;
import Handlers.KeyHandler;
import Handlers.LevelHandler;
import Handlers.Spawners.SpawnHandler;
import Pathfinding.APathfinding;
import World.Tile;


import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;


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
    CollisionHandler collisionHandler;
    SpawnHandler spawnHandler;



    public GamePanel() {
        this.setDoubleBuffered(true);
        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        setFocusable(true);
        hideCursor();

        //Handling ALL LOADING
        keyHandler = new KeyHandler();
        spawnHandler = new SpawnHandler();

        player = new Player(100, 4, Tile.tileSize, Tile.tileSize, "Player", 0, 0, 4*Tile.tileSize/Tile.normalTileSize, 3*Tile.tileSize/Tile.normalTileSize, 8*Tile.tileSize/Tile.normalTileSize, 10*Tile.tileSize/Tile.normalTileSize, keyHandler);

        levelHandler = new LevelHandler(1, spawnHandler, player);
        attackHandler = new AttackHandler(keyHandler);
        collisionHandler = new CollisionHandler();

        pathfinding = new APathfinding(levelHandler.getCurrentLevel().getMap().baseLayerTiles);

        this.addKeyListener(keyHandler);

        player.updateWorldValues(spawnHandler.playerSpawnX, spawnHandler.playerSpawnY);

        //Start game after loading all objects
        gameThread = new Thread(this);
    }


    public void initiateGamePanel() {
        gameThread.start();
    }


    private void hideCursor() {
        this.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),
                "null"));
    }


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
                update();
                repaint();
                delta--;
            }
        }
    }


    void update() {
        player.update(levelHandler.getCurrentLevel().getMap().baseLayerTiles);
        levelHandler.update(player, spawnHandler);
        attackHandler.update(player);
    }


    /**
     * Draws the skibidi
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        levelHandler.draw(g2, player);
        player.draw(g2);
        attackHandler.draw(g2);

        g2.dispose();


    }
}
