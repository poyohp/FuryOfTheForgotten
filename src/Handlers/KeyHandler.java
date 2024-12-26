package Handlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPress, downPress, leftPress, rightPress, attackPress, choicePress, abilityPress;
    public boolean toggleInventory, inventoryHandled, inventoryIndexMoved, choiceTriggered;

    @Override
    public void keyTyped(KeyEvent e) {}

    /**
     * Determines which key is pressed, and sets all other key values to released
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_K) {
            if (!inventoryHandled) {
                toggleInventory = !toggleInventory;
                inventoryHandled = true;
            }
        }

        if(!toggleInventory) {
            if(e.getKeyCode() == KeyEvent.VK_W) {
                upPress = true;
                downPress = false;
                rightPress = false;
                leftPress = false;
            }
            if(e.getKeyCode() == KeyEvent.VK_S) {
                downPress = true;
                upPress = false;
                rightPress = false;
                leftPress = false;
            }
            if(e.getKeyCode() == KeyEvent.VK_A) {
                leftPress = true;
                upPress = false;
                downPress = false;
                rightPress = false;
            }
            if(e.getKeyCode() == KeyEvent.VK_D) {
                rightPress = true;
                upPress = false;
                downPress = false;
                leftPress = false;
            }

            if(e.getKeyCode() == KeyEvent.VK_U) {
                attackPress = true;
            }
            if(e.getKeyCode() == KeyEvent.VK_J && !choiceTriggered) {
                choiceTriggered = true;
                choicePress = true;
            }
            if(e.getKeyCode() == KeyEvent.VK_I) {
                abilityPress = true;
            }
        } else {
            if (e.getKeyCode() == KeyEvent.VK_A && !inventoryIndexMoved) {
                leftPress = true;
                inventoryIndexMoved = true;
            } else if (e.getKeyCode() == KeyEvent.VK_D && !inventoryIndexMoved) {
                rightPress = true;
                inventoryIndexMoved = true;
            }
        }

    }

    /**
     * Determines which key is released, and sets the corresponding key value to false
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W) {
            upPress = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_S) {
            downPress = false;
        }

        if(!toggleInventory) {
            if(e.getKeyCode() == KeyEvent.VK_A) {
                leftPress = false;
                inventoryIndexMoved = false;
            }
            if(e.getKeyCode() == KeyEvent.VK_D) {
                rightPress = false;
                inventoryIndexMoved = false;
            }
        } else {
            if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_D) {
                inventoryIndexMoved = false;
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_J) {
            choiceTriggered = false;
            choicePress = false;
        }

        if(e.getKeyCode() == KeyEvent.VK_U) {
            attackPress = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_I) {
            abilityPress = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_K) {
            inventoryHandled = false;
        }
    }
}
