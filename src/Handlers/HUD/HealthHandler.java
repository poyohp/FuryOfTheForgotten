package Handlers.HUD;

import Handlers.ImageHandler;
import System.Main;
import System.Panels.GamePanel;
import World.Tile;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.Timer;

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
    boolean shieldTransition;

    private double transitionTimer;
    private final double transitionDrawSeconds = 0.5;
    private final double transitionDrawFrames = GamePanel.FPS * transitionDrawSeconds;

    public double maxHearts;
    public double currentHearts;

    //ACTIVATING SHIELDS
    private boolean activatingShields;
    private int activatingShieldsTotalFrames = 19;
    private int activatingShieldsFrameCounter;

    private double shieldTransitionCounter;
    private double shieldTransitionIntervalFrames = GamePanel.FPS * 0.1 ;

    int shieldActivationImageX;
    int shieldActivationImageY;

    //SHIELDS
    public boolean hasShields;
    public final double maxShields;
    public double shieldHearts;

    //ACTIVATING ENHANCED
    private boolean activatingEnhanced;
    private int activatingEnhancedTotalFrames = 6;
    private int activatingEnhancedFrameCounter;

    private double enhancedTransitionCounter;
    private double enhancedTransitionIntervalFrames = GamePanel.FPS * 0.2 ;

    int enhancedActivationImageX;
    int enhancedActivationImageY;

    //ACTIVATING POISON
    private boolean activatingPoison;
    private int activatingPoisonTotalFrames = 6;
    private int activatingPoisonFrameCounter;

    private double poisonTransitionCounter;
    private double poisonTransitionIntervalFrames = GamePanel.FPS * 0.2 ;

    int poisonActivationImageX;
    int poisonActivationImageY;

    //DIFFERENT HEALTH
    public final int diffHealthTotalSeconds = 20;
    public final int diffHealthTotalFrames = (int) GamePanel.FPS * diffHealthTotalSeconds;
    public int diffHealthCounter = 0;
    public boolean poisonedHealth;
    public boolean enhancedHealth;

    Timer diffHealthTimer = new Timer((int) (1000 / GamePanel.FPS), new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            diffHealthCounter++;
            if(diffHealthCounter > diffHealthTotalFrames) {
                diffHealthTimer.stop();
                if(poisonedHealth) {
                    poisonedHealth = false;
                    poisonDamageTimer.stop();
                }
                if(enhancedHealth) {
                    enhancedHealth = false;
                }
                diffHealthCounter = 0;
            }
        }
    });

    Timer poisonDamageTimer = new Timer(5000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            isHit(0.5, true);
        }
    });

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

        if(hasShields) {
            shieldHearts += numShieldHearts;
            if(shieldHearts > maxShields) {
                shieldHearts = maxShields;
            }
        } else {
            shieldHearts = numShieldHearts;
            activatingShields = true;
            shieldActivationImageX = 0;
            shieldActivationImageY = spriteSize*2;
            activatingShieldsFrameCounter = 1;
            shieldTransitionCounter = shieldTransitionIntervalFrames;
        }
    }

    public void activatedEnhanced() {
        if(poisonedHealth) {
            poisonedHealth = false;
        }
        if(enhancedHealth) {
            diffHealthTimer.restart();
        } else {
            activatingEnhanced = true;
            enhancedActivationImageX = 0;
            enhancedActivationImageY = spriteSize*10;
            activatingEnhancedFrameCounter = 1;
            enhancedTransitionCounter = enhancedTransitionIntervalFrames;
        }
    }

    public void activatedPoison() {
        if(enhancedHealth) {
            enhancedHealth = false;
        }
        if(poisonedHealth) {
            diffHealthTimer.restart();
        } else {
            poisonDamageTimer.start();
            activatingPoison = true;
            poisonActivationImageX = 0;
            poisonActivationImageY = spriteSize*15;
            activatingPoisonFrameCounter = 1;
            poisonTransitionCounter = poisonTransitionIntervalFrames;
        }
    }

    public void isHit(double damage, boolean onlyHearts) {

        if(enhancedHealth) {
            damage /= 2.0;
        }

        if (hasShields) {
            shieldTransition = true;
        }
        if(onlyHearts) {
            shieldTransition = false;
        }

        double previousHearts;
        if(!onlyHearts && hasShields && shieldHearts > 0) {
            previousHearts = shieldHearts;
            shieldHearts -= damage;
        } else {
            previousHearts = currentHearts;
            currentHearts -= damage;
        }

        transitionTimer = transitionDrawFrames;
        lostHeart = Math.ceil(previousHearts) - 1;

        //RESET LOST VALUE IF SHIELDS GO BYEBYE
        if (hasShields && shieldHearts <= 0) {
            hasShields = false;
            lostHeart = -1;
            lostFullHeart = false;
            lostHalfHeart = false;
        }

        //CHECK DEAD
        if(currentHearts <= 0) {
            Main.updateGameState(3);
        }

        //DETERMINE IF FULL-->HALF
        if(!onlyHearts && hasShields && shieldHearts > 0) {
            if (previousHearts % 1 == 0.5 && shieldHearts % 1 == 0) {
                //HALF TO NONE
                lostFullHeart = true;
            } else if (previousHearts % 1 == 0 && shieldHearts % 1 == 0.5) {
                //FULL TO HALF
                lostHalfHeart = true;
            }
        } else {
            if ((previousHearts % 1 == 0.5 && currentHearts % 1 == 0) || (previousHearts % 1 == 0.5 && currentHearts % 1 == 0.25)) {
                //HALF TO NONE OR QUARTER TO NONE
                lostFullHeart = true;
            } else if ((previousHearts % 1 == 0 && currentHearts % 1 == 0.5) || (previousHearts % 1 == 0 && currentHearts % 1 == 0.75)) {
                //FULL TO HALF OR FULL TO 0.75
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

    private void drawHearts(Graphics2D g2) {

        int x = heartsX + outerGap - innerGap;
        int y = heartsY;

        if(activatingEnhanced) {
            for(int i = 0; i < currentHearts; i++) {
                g2.drawImage(heartSprite, x, y, x + heartFinalDrawSize, y + heartFinalDrawSize, enhancedActivationImageX, enhancedActivationImageY, enhancedActivationImageX + spriteSize, enhancedActivationImageY + spriteSize, null);
                x += heartFinalDrawSize - innerGap;
            }

            enhancedTransitionCounter--;

            if(enhancedTransitionCounter < 0) {
                enhancedTransitionCounter = enhancedTransitionIntervalFrames;

                enhancedActivationImageX += spriteSize;
                activatingEnhancedFrameCounter++;

                if(activatingEnhancedFrameCounter > activatingEnhancedTotalFrames) {
                    activatingEnhanced = false;
                    enhancedHealth = true;
                    diffHealthTimer.restart();
                }
            }

        } else if(activatingPoison) {
            for(int i = 0; i < currentHearts; i++) {
                g2.drawImage(heartSprite, x, y, x + heartFinalDrawSize, y + heartFinalDrawSize, poisonActivationImageX, poisonActivationImageY, poisonActivationImageX + spriteSize, poisonActivationImageY + spriteSize, null);
                x += heartFinalDrawSize - innerGap;
            }

            poisonTransitionCounter--;

            if(poisonTransitionCounter < 0) {
                poisonTransitionCounter = poisonTransitionIntervalFrames;

                poisonActivationImageX += spriteSize;
                activatingPoisonFrameCounter++;

                if(activatingPoisonFrameCounter > activatingPoisonTotalFrames) {
                    activatingPoison = false;
                    poisonedHealth = true;
                    diffHealthTimer.restart();
                }
            }
        } else {
            double numDrawn = 0.5;
            for (int i = 0; i < maxHearts; i++) {
                if(currentHearts > numDrawn) {
                    //FULL HEART
                    if(enhancedHealth) {
                        //FULL GOLD
                        g2.drawImage(heartSprite, x, y, x + heartFinalDrawSize, y + heartFinalDrawSize, 0, spriteSize * 11, spriteSize, spriteSize * 11 + spriteSize, null);
                    } else if(poisonedHealth) {
                        //FULL POISONED
                        g2.drawImage(heartSprite, x, y, x + heartFinalDrawSize, y + heartFinalDrawSize, 0, spriteSize * 16, spriteSize, spriteSize * 16 + spriteSize, null);
                    } else {
                        //FULL NORMAL
                        g2.drawImage(heartSprite, x, y, x + heartFinalDrawSize, y + heartFinalDrawSize, 0, spriteSize * 3, spriteSize, spriteSize * 3 + spriteSize, null);
                    }
                } else if(currentHearts == numDrawn) {
                    //HALF HEART
                    if(enhancedHealth) {
                        //HALF GOLD
                        g2.drawImage(heartSprite, x, y, x + heartFinalDrawSize, y + heartFinalDrawSize, 0, spriteSize * 12, spriteSize, spriteSize * 12 + spriteSize, null);
                    } else if(poisonedHealth) {
                        //HALF POISONED
                        g2.drawImage(heartSprite, x, y, x + heartFinalDrawSize, y + heartFinalDrawSize, 0, spriteSize * 17, spriteSize, spriteSize * 17 + spriteSize, null);
                    } else {
                        //HALF NORMAL
                        g2.drawImage(heartSprite, x, y, x + heartFinalDrawSize, y + heartFinalDrawSize, spriteSize * 2, spriteSize * 3,spriteSize * 2 + spriteSize, spriteSize * 3 + spriteSize, null);
                    }
                } else {
                    //EMPTY HEARTS
                    g2.drawImage(heartSprite, x, y, x + heartFinalDrawSize, y + heartFinalDrawSize, 0, 0, spriteSize, spriteSize, null);
                }

                if (i == lostHeart && !shieldTransition) {
                    double transitionDrawHalfway = transitionDrawFrames / 2.0;

                    double scaleFactor = 1.5;
                    if (transitionTimer < transitionDrawHalfway) {
                        scaleFactor = 1.2;
                    }
                    int scaledDrawSize = (int) (heartFinalDrawSize * scaleFactor);
                    int offset = (scaledDrawSize - heartFinalDrawSize) / 2;

                    if (lostFullHeart) {
                        drawTransition(g2, x, y, transitionDrawHalfway, scaledDrawSize, offset);
                    } else if (lostHalfHeart) {
                        if (transitionTimer < transitionDrawHalfway) {
                            //HALF
                            if(enhancedHealth) {
                                g2.drawImage(heartSprite,
                                        x - offset, y - offset,
                                        x + scaledDrawSize - offset, y + scaledDrawSize - offset, 0, spriteSize * 12, spriteSize, spriteSize * 12 + spriteSize, null);
                            } else if(poisonedHealth) {
                                g2.drawImage(heartSprite,
                                        x - offset, y - offset,
                                        x + scaledDrawSize - offset, y + scaledDrawSize - offset, 0, spriteSize * 17, spriteSize, spriteSize * 17 + spriteSize, null);
                            } else {
                                g2.drawImage(
                                        heartSprite,
                                        x - offset, y - offset,
                                        x + scaledDrawSize - offset, y + scaledDrawSize - offset,
                                        spriteSize * 2, spriteSize * 3, spriteSize * 2 + spriteSize, spriteSize * 3 + spriteSize,
                                        null
                                );
                            }
                        } else {
                            //BLINKING
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
        }
    }

    private void drawShields(Graphics2D g2) {

        int x = heartsX + outerGap - innerGap;
        int y = shieldY;

        if(activatingShields) {
            for(int i = 0; i < shieldHearts; i++) {
                g2.drawImage(heartSprite, x, y, x + heartFinalDrawSize, y + heartFinalDrawSize, shieldActivationImageX, shieldActivationImageY, shieldActivationImageX + spriteSize, shieldActivationImageY + spriteSize, null);
                x += heartFinalDrawSize - innerGap;
            }

            shieldTransitionCounter--;

            if(shieldTransitionCounter < 0) {
                shieldTransitionCounter = shieldTransitionIntervalFrames;
                if(activatingShieldsFrameCounter == 13) {
                    shieldActivationImageX = 0;
                    shieldActivationImageY = spriteSize*5;
                } else {
                    shieldActivationImageX += spriteSize;
                }

                activatingShieldsFrameCounter++;

                if(activatingShieldsFrameCounter > activatingShieldsTotalFrames) {
                    activatingShields = false;
                    hasShields = true;
                }
            }

        } else if(hasShields) {
            double numDrawn = 0.5;

            for (int i = 0; i < maxShields; i++) {
                if(shieldHearts > numDrawn) {
                    //FULL HEART
                    g2.drawImage(heartSprite, x, y, x + heartFinalDrawSize, y + heartFinalDrawSize, 0, spriteSize * 8, spriteSize, spriteSize * 8 + spriteSize, null);
                } else if(currentHearts == numDrawn) {
                    //HALF HEART
                    g2.drawImage(heartSprite, x, y, x + heartFinalDrawSize, y + heartFinalDrawSize, spriteSize * 2, spriteSize * 8,spriteSize * 2 + spriteSize, spriteSize * 8 + spriteSize, null);
                } else {
                    //EMPTY HEARTS
                    g2.drawImage(heartSprite, x, y, x + heartFinalDrawSize, y + heartFinalDrawSize, 0, 0, spriteSize, spriteSize, null);
                }

                if (i == lostHeart && shieldTransition) {
                    double transitionDrawHalfway = transitionDrawFrames / 2.0;

                    double scaleFactor = 1.5;
                    if (transitionTimer < transitionDrawHalfway) {
                        scaleFactor = 1.2;
                    }
                    int scaledDrawSize = (int) (heartFinalDrawSize * scaleFactor);
                    int offset = (scaledDrawSize - heartFinalDrawSize) / 2;

                    if (lostFullHeart) {
                        drawTransition(g2, x, y, transitionDrawHalfway, scaledDrawSize, offset);
                    } else if (lostHalfHeart) {
                        if (transitionTimer < transitionDrawHalfway) {
                            g2.drawImage(heartSprite, x, y, x + heartFinalDrawSize, y + heartFinalDrawSize, spriteSize * 2, spriteSize * 8,spriteSize * 2 + spriteSize, spriteSize * 8 + spriteSize, null);
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

    private void drawTransition(Graphics2D g2, int x, int y, double transitionDrawHalfway, int scaledDrawSize, int offset) {
        if (transitionTimer < transitionDrawHalfway) {
            //EMPTY
            g2.drawImage(
                    heartSprite,
                    x - offset, y - offset,
                    x + scaledDrawSize - offset, y + scaledDrawSize - offset,
                    0, 0, spriteSize, spriteSize,
                    null
            );
        } else {
            // BLINKING HEART
            g2.drawImage(
                    heartSprite,
                    x - offset, y - offset,
                    x + scaledDrawSize - offset, y + scaledDrawSize - offset,
                    spriteSize, 0, spriteSize * 2, spriteSize,
                    null
            );
        }
    }

    public void drawHealth(Graphics2D g2) {
        drawHearts(g2);
        drawShields(g2);

    }
}
