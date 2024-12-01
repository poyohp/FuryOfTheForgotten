package System;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class WinPanel extends JPanel {
    BufferedImage gameWon = loadImage();

    // Get screen width and height
    public static final int screenWidth = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    public static final int screenHeight = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();

    WinPanel() {
        this.setDoubleBuffered(true);
        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());

        DrawingPanel panel = new DrawingPanel();

        panel.setDoubleBuffered(true);
        panel.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.add(panel);
    }

    private class DrawingPanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Draws the fullscreen menu image
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(gameWon, 0, 0, screenWidth, screenHeight, null);
        }
    }

    BufferedImage loadImage() {
        BufferedImage image = null;
        java.net.URL url = this.getClass().getResource("/MenuImages/gameWon.png");
        try {
            image = ImageIO.read(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return image;
    }
}
