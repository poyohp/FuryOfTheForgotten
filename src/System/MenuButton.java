package System;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MenuButton {
    int x, y;
    int width, height;
    String buttonFilename;
    boolean isSelected;

    MenuButton(String buttonFilename, int x, int y, int width, int height) {
        this.isSelected = false;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.buttonFilename = buttonFilename;
    }

    public void drawButton(Graphics2D g2) {
        g2.drawImage(loadImage(buttonFilename), x, y, width, height, null);
    }

    public void renderCurrentChoice(Graphics2D g2) {
        g2.setColor(Color.RED);
        g2.setStroke(new BasicStroke(9));
        g2.drawRect(this.x, this.y, this.width, this.height);
    }

    BufferedImage loadImage(String filename) {
        BufferedImage image = null;
        java.net.URL url = this.getClass().getResource("/MenuImages/" + filename);
        try {
            image = ImageIO.read(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return image;
    }

}
