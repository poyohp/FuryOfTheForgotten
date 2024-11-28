package System;


import Attacks.Ranged;
import Entities.Enemy;
import Entities.Player;
import Handlers.AttackHandler;
import Handlers.CollisionHandler;
import Handlers.KeyHandler;
import Handlers.LevelHandler;
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
    Tile[][] currentTileset;

    //Create objects for GAME LOGIC
    public Player player;
    public Enemy enemy;
    LevelHandler levelHandler;
    KeyHandler keyHandler;
    AttackHandler attackHandler;
    CollisionHandler collisionHandler;



    public GamePanel() {
        this.setDoubleBuffered(true);
        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        setFocusable(true);


        hideCursor();


        //Starting game
        gameThread = new Thread(this);


        //Handling ALL LOADING
        keyHandler = new KeyHandler();
        player = new Player(100, 4, Tile.tileSize, Tile.tileSize, "Player", 128, 128, 4*Tile.tileSize/Tile.normalTileSize, 3*Tile.tileSize/Tile.normalTileSize, 8*Tile.tileSize/Tile.normalTileSize, 10*Tile.tileSize/Tile.normalTileSize, keyHandler);
        levelHandler = new LevelHandler(1);
        attackHandler = new AttackHandler(keyHandler);
        collisionHandler = new CollisionHandler();
        currentTileset = levelHandler.getCurrentLevel().getMap().baseLayerTiles;
        player.setTileSet(currentTileset);
        pathfinding = new APathfinding(currentTileset);
        enemy = new Enemy(100, 4, Tile.tileSize, Tile.tileSize, "Enemy", 5*Tile.tileSize, 3*Tile.tileSize, 0, 0, 8*Tile.tileSize/Tile.normalTileSize, 10*Tile.tileSize/Tile.normalTileSize, player, currentTileset);
        levelHandler.getCurrentLevel().enemies.add(enemy);

        this.addKeyListener(keyHandler);


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
        player.update();
        levelHandler.update(collisionHandler, player);
        attackHandler.update(player);

        //PLEASE MOVE THIS CODE! (ideally into player class)

        enemy.update();
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


        levelHandler.getCurrentLevel().getMap().drawMap(g2, player);
        player.draw(g2);
        player.hitbox.drawHitbox(g2, player);
        enemy.draw(g2);
        attackHandler.draw(g2);

        debugWalkableTiles(g2);

        g2.dispose();


    }
    public void debugWalkableTiles(Graphics2D g2) {
        for (int row = 0; row < currentTileset.length; row++) {
            for (int col = 0; col < currentTileset[0].length; col++) {
                Tile tile = currentTileset[row][col];
                if (tile.walkable) {
                    g2.setColor(Color.GREEN);
                } else {
                    g2.setColor(Color.RED);
                }
                g2.drawRect((int)((col * Tile.tileSize) - player.worldX + player.screenX), (int)((row * Tile.tileSize) - player.worldY + player.screenY), Tile.tileSize, Tile.tileSize);
            }
        }
    }
}
