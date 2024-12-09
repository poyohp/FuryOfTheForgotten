package Handlers.HUD;

import Handlers.ImageHandler;
import System.Panels.GamePanel;
import World.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HealthHandler {

    BufferedImage heartSprite = ImageHandler.loadImage("src/Assets/HUD//PlayerStatus/hearts.png");
    private final int spriteSize = 16;

    private final int heartDrawSize = 20;
    private final int heartFinalDrawSize = Tile.tileMultipler*heartDrawSize;

    private final int innerGap = heartFinalDrawSize/5;
    private final int outerGap = heartFinalDrawSize/4;

    private final int heartsX = outerGap;
    private final int heartsY = (int)(GamePanel.screenHeight - outerGap - heartFinalDrawSize);

    private final double initialHealth = 100;
    private double health;

    private final int maxHearts = 8;
    private double currentFullHearts;
    private boolean halfHeart;

    public HealthHandler(double health) {
        this.health = health;
    }

    public void updateHealth(double health) {
        this.health = health;
        determineHearts();

    }

    private void determineHearts() {
        double hearts = (health / initialHealth) * maxHearts;
        currentFullHearts = Math.floor(hearts);
        halfHeart = (hearts - currentFullHearts) > 0.5;

    }

    public void drawHearts(Graphics2D g2) {
        int x = heartsX + outerGap;
        int y = heartsY;
        for (int i = 0; i < currentFullHearts; i++) {
            g2.drawImage(heartSprite, x, y, x + heartFinalDrawSize, y + heartFinalDrawSize, 0, spriteSize * 3, spriteSize, spriteSize * 3 + spriteSize, null);
            x += heartFinalDrawSize;
        }
        if (halfHeart || currentFullHearts == 0) {
            g2.drawImage(heartSprite, x, y, x + heartFinalDrawSize, y + heartFinalDrawSize, spriteSize * 2, spriteSize * 3,spriteSize * 2 + spriteSize, spriteSize * 3 + spriteSize, null);
        }

    }
}
