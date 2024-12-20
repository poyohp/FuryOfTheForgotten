package System.Resources;

import java.awt.*;
import java.awt.image.BufferedImage;

import static Handlers.ImageHandler.loadImage;

public class MenuButton {
    int x, y;
    int width, height;
    String buttonFilename;
    public boolean isSelected;
    BufferedImage image;

    public MenuButton(String buttonFilename, int x, int y, int width, int height) {
        this.isSelected = false;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.buttonFilename = buttonFilename;

        image = loadImage("Assets/MenuImages/" + buttonFilename);
    }

    /**
     * Draws a button based on its file name
     * @param g2
     */
    public void drawButton(Graphics2D g2) {
        g2.drawImage(image, x, y, width, height, null);
    }

    /**
     * Draws a red rectangle around choice
     * @param g2 Graphics2D object to draw on
     */
    public void renderCurrentChoice(Graphics2D g2) {
        g2.setColor(Color.RED);
        g2.setStroke(new BasicStroke(9));
        g2.drawRect(this.x, this.y, this.width, this.height);
    }

}
