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
    private final int shieldY = heartsY - heartFinalDrawSize;

    private double lostHeart;
    boolean lostFullHeart;
    boolean lostHalfHeart;

    private double transitionTimer;
    private final double transitionDrawSeconds = 1.0;
    private final double transitionDrawFrames = GamePanel.FPS * transitionDrawSeconds;

    public double maxHearts;
    public double currentHearts;

    //SHIELDS
    public boolean hasShields;
    public final double maxShields;
    public double shieldHearts;

    //DIFFERENT HEALTH
    public boolean poisonedHealth;
    public boolean enhancedHealth;

    public HealthHandler(double hearts) {
        this.maxHearts = hearts;
        this.currentHearts = hearts;

        maxShields = maxHearts/2;

        lostHeart = -1;
        lostFullHeart = false;
        lostHalfHeart = false;

        shieldHearts = 0;
        hasShields = false;

    }

    public void activatedShields(double numShieldHearts) {
        hasShields =  true;
        shieldHearts = numShieldHearts;

    }

    public void activatedEnhanced() {
        enhancedHealth = true;
    }

    public void activatedPoison() {
        poisonedHealth = true;
    }

    public void isHit(double damage) {

        if(enhancedHealth) {
            damage /= 2.0;
        }

        double previousHearts;
        if(hasShields) {
            previousHearts = shieldHearts;
            shieldHearts -= damage;
        } else {
            previousHearts = currentHearts;
            currentHearts -= damage;
        }

        transitionTimer = transitionDrawFrames;
        lostHeart = Math.ceil(previousHearts) - 1;

        //CHECK DEAD
        if(currentHearts <= 0) {
            Main.updateGameState(3);
        }

        //DETERMINE IF FULL-->HALF
        if(hasShields) {
            if (previousHearts % 1 == 0.5 && shieldHearts % 1 == 0) {
                //HALF TO NONE
                lostFullHeart = true;
            } else if (previousHearts % 1 == 0 && shieldHearts % 1 == 0.5) {
                //FULL TO HALF
                lostHalfHeart = true;
            }
        } else {
            if (previousHearts % 1 == 0.5 && currentHearts % 1 == 0) {
                //HALF TO NONE
                lostFullHeart = true;
            } else if (previousHearts % 1 == 0 && currentHearts % 1 == 0.5) {
                //FULL TO HALF
                lostHalfHeart = true;
            }
        }
    }

    public void heal(double hearts) {
        if (currentHearts + hearts <= maxHearts) {
            currentHearts += hearts;
        } else {
            currentHearts = maxHearts;
        }
    }

    public void drawHealth(Graphics2D g2) {
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

            if (i == lostHeart) {
                double transitionDrawHalfway = transitionDrawFrames / 2.0;

                double scaleFactor = 1.5;
                if (transitionTimer < transitionDrawHalfway) {
                    scaleFactor = 1.2;
                }
                int scaledDrawSize = (int) (heartFinalDrawSize * scaleFactor);
                int offset = (scaledDrawSize - heartFinalDrawSize) / 2;

                if (lostFullHeart) {
                    if (transitionTimer < transitionDrawHalfway) {
                        g2.drawImage(
                                heartSprite,
                                x - offset, y - offset,
                                x + scaledDrawSize - offset, y + scaledDrawSize - offset,
                                0, 0, spriteSize, spriteSize,
                                null
                        );
                    } else {
                        g2.drawImage(
                                heartSprite,
                                x - offset, y - offset,
                                x + scaledDrawSize - offset, y + scaledDrawSize - offset,
                                spriteSize, 0, spriteSize * 2, spriteSize,
                                null
                        );
                    }
                } else if (lostHalfHeart) {
                    if (transitionTimer < transitionDrawHalfway) {
                        g2.drawImage(
                                heartSprite,
                                x - offset, y - offset,
                                x + scaledDrawSize - offset, y + scaledDrawSize - offset,
                                spriteSize * 2, spriteSize * 3, spriteSize * 2 + spriteSize, spriteSize * 3 + spriteSize,
                                null
                        );
                    } else {
                        g2.drawImage(
                                heartSprite,
                                x - offset, y - offset,
                                x + scaledDrawSize - offset, y + scaledDrawSize - offset,
                                spriteSize, 0, spriteSize * 2, spriteSize,
                                null
                        );
                    }
                }
                transitionTimer--;
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

        numDrawn = 0.5;
        x = heartsX + outerGap - innerGap;
        y = shieldY;

        for (int i = 0; i < maxShields; i++) {
            if(shieldHearts > numDrawn) {
                //FULL HEART
                g2.drawImage(heartSprite, x, y, x + heartFinalDrawSize, y + heartFinalDrawSize, 0, spriteSize * 3, spriteSize, spriteSize * 3 + spriteSize, null);
            } else if(currentHearts == numDrawn) {
                //HALF HEART
                g2.drawImage(heartSprite, x, y, x + heartFinalDrawSize, y + heartFinalDrawSize, spriteSize * 2, spriteSize * 3,spriteSize * 2 + spriteSize, spriteSize * 3 + spriteSize, null);
            } else {
                //EMPTY HEARTS
                g2.drawImage(heartSprite, x, y, x + heartFinalDrawSize, y + heartFinalDrawSize, 0, 0, spriteSize, spriteSize, null);
            }

            if (i == lostHeart) {
                double transitionDrawHalfway = transitionDrawFrames / 2.0;

                double scaleFactor = 1.5;
                if (transitionTimer < transitionDrawHalfway) {
                    scaleFactor = 1.2;
                }
                int scaledDrawSize = (int) (heartFinalDrawSize * scaleFactor);
                int offset = (scaledDrawSize - heartFinalDrawSize) / 2;

                if (lostFullHeart) {
                    if (transitionTimer < transitionDrawHalfway) {
                        g2.drawImage(
                                heartSprite,
                                x - offset, y - offset,
                                x + scaledDrawSize - offset, y + scaledDrawSize - offset,
                                0, 0, spriteSize, spriteSize,
                                null
                        );
                    } else {
                        g2.drawImage(
                                heartSprite,
                                x - offset, y - offset,
                                x + scaledDrawSize - offset, y + scaledDrawSize - offset,
                                spriteSize, 0, spriteSize * 2, spriteSize,
                                null
                        );
                    }
                } else if (lostHalfHeart) {
                    if (transitionTimer < transitionDrawHalfway) {
                        g2.drawImage(
                                heartSprite,
                                x - offset, y - offset,
                                x + scaledDrawSize - offset, y + scaledDrawSize - offset,
                                spriteSize * 2, spriteSize * 3, spriteSize * 2 + spriteSize, spriteSize * 3 + spriteSize,
                                null
                        );
                    } else {
                        g2.drawImage(
                                heartSprite,
                                x - offset, y - offset,
                                x + scaledDrawSize - offset, y + scaledDrawSize - offset,
                                spriteSize, 0, spriteSize * 2, spriteSize,
                                null
                        );
                    }
                }
                transitionTimer--;
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
        hasShields = shieldHearts > 0;
    }
}
