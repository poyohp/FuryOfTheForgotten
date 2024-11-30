package System;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MenuPanel extends JPanel {
    BufferedImage menu = loadImage();

    MenuPanel() {
        this.setDoubleBuffered(true);
        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(menu, 0, 0, this.getWidth(), this.getHeight(), null);
    }

    BufferedImage loadImage() {
        BufferedImage image = null;
        java.net.URL url = this.getClass().getResource("/MenuImages/menu.png");
        try {
            image = ImageIO.read(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return image;
    }

}
