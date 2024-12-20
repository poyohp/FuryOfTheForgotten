package System.Resources;

import java.awt.*;
import java.awt.image.BufferedImage;

import static Handlers.ImageHandler.loadImage;

public class CharacterButton {
    int x, y;
    int width, height;
    public String characterType;
    public boolean isSelected;
    BufferedImage image;

    public CharacterButton(String characterType, int x, int y) {
        this.isSelected = false;
        this.x = x;
        this.y = y;
        this.width = 30;
        this.height = 50;
        this.characterType = characterType;

        image = loadImage("Assets/MenuImages/arrow.png");
    }

    /**
     * Draws the arrow
     * @param g2
     */
    public void drawButton(Graphics2D g2) {
        g2.drawImage(image, x, y, width, height, null);
    }
}
