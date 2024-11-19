package System;

import Entities.Player;
import Handlers.LevelHandler;
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

    //Create objects for GAME LOGIC
    public Player player;
    LevelHandler levelHandler;

    public GamePanel() {
        this.setDoubleBuffered(true);
        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());

        hideCursor();

        //Starting game
        gameThread = new Thread(this);

        //Handling ALL LOADING
        player = new Player(100, 5, Tile.tileSize, Tile.tileSize, "Player", 0, 0, 0, 0);
        levelHandler = new LevelHandler(1);

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
    }

    /**
     * Draws the skibidi
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        levelHandler.getCurrentLevel().getMap().drawMap(g2, player);
        player.draw(g2);

    }
}
