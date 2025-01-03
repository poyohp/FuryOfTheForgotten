package System.Panels;

import Handlers.ImageHandler;
import System.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameOverPanel extends JPanel {
    BufferedImage gameOverLose = ImageHandler.loadImage("Panel.Images/gameOver.png");

    // Get screen width and height
    public static final int screenWidth = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    public static final int screenHeight = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();

    /**
     * Panel for when the game is over
     */
    public GameOverPanel() {
        // Ensures full screen and reduces rendering time!
        this.setDoubleBuffered(true);
        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());

        Main.hideCursor(this);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draws the fullscreen menu image
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(gameOverLose, 0, 0, screenWidth, screenHeight, null);
    }
}
