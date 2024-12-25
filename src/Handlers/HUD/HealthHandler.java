package Handlers.HUD;

import Handlers.ImageHandler;
import System.Main;
import System.Panels.GamePanel;
import World.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HealthHandler {

    BufferedImage heartSprite = ImageHandler.loadImage("Assets/HUD/PlayerStatus/hearts.png");
    private final int spriteSize = 16;

    private final int heartDrawSize = Tile.normalTileSize;
    private final int heartFinalDrawSize = Tile.tileMultipler*heartDrawSize;

    private final int innerGap = heartFinalDrawSize/5;
    private final int outerGap = heartFinalDrawSize/4;

    private final int heartsX = outerGap;
    private final int heartsY = (int)(GamePanel.screenHeight - outerGap - heartFinalDrawSize);

    private double lostHeart;
    private double transitionTimer;
    private final double transitionDrawSeconds = 1.0;
    private final double transitionDrawFrames = GamePanel.FPS * transitionDrawSeconds;
    boolean lostFullHeart;
    boolean lostHalfHeart;

    public double maxHearts;
    public double currentHearts;

    public HealthHandler(double hearts) {
        this.maxHearts = hearts;
        this.currentHearts = hearts;

        lostHeart = -1;
        lostFullHeart = false;
        lostHalfHeart = false;
    }

    public void isHit(double damage) {
        double previousHearts = currentHearts;
        currentHearts -= damage;
        transitionTimer = transitionDrawFrames;
        lostHeart = Math.ceil(previousHearts) - 1;

        // Check transitions
        if (previousHearts % 1 == 0.5 && currentHearts % 1 == 0) {
            //HALF TO NONE
            lostFullHeart = true;
        } else if (previousHearts % 1 == 0 && currentHearts % 1 == 0.5) {
            //FULL TO HALF
            lostHalfHeart = true;
        }
    }

    public void drawHearts(Graphics2D g2) {
        double numDrawn = 0.5;
        int x = heartsX + outerGap - innerGap;
        int y = heartsY;
        for (int i = 0; i < maxHearts; i++) {
            if(currentHearts > numDrawn) {
                //FULL HEART
                g2.drawImage(heartSprite, x, y, x + heartFinalDrawSize, y + heartFinalDrawSize, 0, spriteSize * 3, spriteSize, spriteSize * 3 + spriteSize, null);
            } else if(currentHearts == numDrawn) {
                //HALF HEART
                g2.drawImage(heartSprite, x, y, x + heartFinalDrawSize, y + heartFinalDrawSize, spriteSize * 2, spriteSize * 3,spriteSize * 2 + spriteSize, spriteSize * 3 + spriteSize, null);
            } else {
                //EMPTY HEARTS
                g2.drawImage(heartSprite, x, y, x + heartFinalDrawSize, y + heartFinalDrawSize, 0, 0, spriteSize, spriteSize, null);
            }

            if(i == lostHeart) {
                double transitionDrawHalfway = transitionDrawFrames / 2.0;
                if(lostFullHeart) {
                    if(transitionTimer < transitionDrawHalfway) {
                        g2.drawImage(heartSprite, x, y, x + heartFinalDrawSize, y + heartFinalDrawSize, 0, 0, spriteSize, spriteSize, null);
                    } else {
                        g2.drawImage(heartSprite, x, y, x + heartFinalDrawSize, y + heartFinalDrawSize, spriteSize, 0, spriteSize*2, spriteSize, null);
                    }
                    transitionTimer--;
                } else if(lostHalfHeart) {
                    if(transitionTimer < transitionDrawHalfway) {
                        g2.drawImage(heartSprite, x, y, x + heartFinalDrawSize, y + heartFinalDrawSize, spriteSize * 2, spriteSize * 3,spriteSize * 2 + spriteSize, spriteSize * 3 + spriteSize, null);
                    } else {
                        g2.drawImage(heartSprite, x, y, x + heartFinalDrawSize, y + heartFinalDrawSize, spriteSize, 0, spriteSize*2, spriteSize, null);
                    }
                    transitionTimer--;
                }
            }

            if(transitionTimer < 0) {
                transitionTimer = 0;
                lostHeart = -1;
                lostFullHeart = false;
                lostHalfHeart = false;
            }

            numDrawn++;
            x += heartFinalDrawSize - innerGap;
        }
    }
}
