package Handler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPress, downPress, leftPress, rightPress;

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W) {
            upPress = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_S) {
            downPress = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_A) {
            leftPress = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_D) {
            rightPress = true;
        }
    }

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
    }
}
