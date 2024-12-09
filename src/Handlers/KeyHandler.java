package Handlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPress, downPress, leftPress, rightPress, attackPress, choicePress, abilityPress;

    @Override
    public void keyTyped(KeyEvent e) {}

    /**
     * Determines which key is pressed, and sets all other key values to released
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
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
        if(e.getKeyCode() == KeyEvent.VK_J) {
            attackPress = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            choicePress = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_Q) {
            abilityPress = true;
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
        if(e.getKeyCode() == KeyEvent.VK_A) {
            leftPress = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_D) {
            rightPress = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_J) {
            attackPress = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_Q) {
            abilityPress = false;
        }
    }
}
