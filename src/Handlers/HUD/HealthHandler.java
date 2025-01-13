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

    private int heartsDamaged;
    private int lastHeartIndex;
    private boolean isTransitioningForHearts;
    private double transitionTimerForHearts;

    private int shieldsDamaged;
    private int lastShieldIndex;
    private boolean isTransitionForShields;
    private double transitionTimerForShields;

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
            if(poisonedHealth) {
                isHit(0.5, true);
            }
        }
    });

    public HealthHandler(double hearts) {
        this.maxHearts = hearts;
        this.currentHearts = hearts;

        maxShields = 4.0;

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

        heartsDamaged = 0;
        shieldsDamaged = 0;
        lastHeartIndex = (int) Math.floor(currentHearts);
        lastShieldIndex = (int) Math.floor(shieldHearts);

        if (enhancedHealth && !hasShields ) {
            damage /= 2.0;
        }

        double remainingDamage = damage;

        // Heart loss based on damage
        while (remainingDamage >= 1.0) {
            if (hasShields && !onlyHearts) {
                shieldsDamaged += 1;
                shieldHearts -= 1.0;
                lastShieldIndex --;
            } else {
                currentHearts -= 1.0;
                heartsDamaged += 1;
                lastHeartIndex--;
            }
            remainingDamage -= 1.0;
        }

        // Partial damage
        if (remainingDamage > 0) {
            if (hasShields && !onlyHearts) {
                shieldsDamaged += 1;
                shieldHearts -= remainingDamage;
                lastShieldIndex --;
            } else {
                currentHearts -= remainingDamage;
                heartsDamaged += 1;
                lastHeartIndex--;
            }
        }

        // RESETTING HEART TRANSITION
        if(!isTransitioningForHearts && heartsDamaged > 0) {
            isTransitioningForHearts = true;
            transitionTimerForHearts = transitionDrawFrames;
        }

        //RESETTING SHIELD TRANSITION
        if(!isTransitionForShields) {
            isTransitionForShields = true;
            transitionTimerForShields = transitionDrawFrames;
        }

        // RESET LOST VALUE IF SHIELDS GO BYEBYE
        if (hasShields && shieldHearts <= 0) {
            hasShields = false;
        }

        // CHECK DEAD
        if (currentHearts <= 0) {
            Main.updateGameState(4); // Game over or dead state
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

                numDrawn++;
                x += heartFinalDrawSize - innerGap;
            }

            // IF LOST HEART, DRAW TRANSITION
            if(heartsDamaged > 0 && isTransitioningForHearts) {
                int drawX = ((lastHeartIndex)*(heartFinalDrawSize - innerGap)) + outerGap;
                for(int j = 0; j < heartsDamaged; j++) {
                    drawTransition(g2, drawX, heartsY, transitionDrawFrames/2, transitionTimerForHearts);
                    drawX += heartFinalDrawSize - innerGap;
                }

                transitionTimerForHearts--;
                if(transitionTimerForHearts < 0) {
                    isTransitioningForHearts = false;
                }
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

                numDrawn++;
                x += heartFinalDrawSize - innerGap;
            }

            // IF LOST HEART, DRAW TRANSITION
            if(shieldsDamaged > 0 && isTransitionForShields) {
                int drawX = (lastShieldIndex*(heartFinalDrawSize - innerGap)) + outerGap;
                for(int j = 0; j < shieldsDamaged; j++) {
                    drawTransition(g2, drawX, shieldY, transitionDrawFrames/2, transitionTimerForShields);
                    drawX += heartFinalDrawSize - innerGap;
                }

                transitionTimerForShields--;
                if(transitionTimerForShields < 0) {
                    isTransitionForShields = false;
                }
            }

            hasShields = shieldHearts > 0;
        }
    }

    private void drawTransition(Graphics2D g2, int x, int y, double transitionDrawHalfway, double timer) {
        // BLINKING HEART
        double scaleFactor = 1.5;
        if (timer < transitionDrawHalfway) {
            scaleFactor = 1.2;
        }

        int scaledDrawSize = (int) (heartFinalDrawSize * scaleFactor);
        int offset = (scaledDrawSize - heartFinalDrawSize) / 2;

        g2.drawImage(
                heartSprite,
                x - offset, y - offset,
                x + scaledDrawSize - offset, y + scaledDrawSize - offset,
                spriteSize, 0, spriteSize * 2, spriteSize,
                null
        );
    }

    public void drawHealth(Graphics2D g2) {
        drawHearts(g2);
        drawShields(g2);

    }
}
