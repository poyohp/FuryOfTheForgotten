package System;

import Handlers.ImageHandler;
import Handlers.KeyHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class HelpPanel extends JPanel implements KeyListener {
    BufferedImage helpScreen = ImageHandler.loadImage("src/Assets/MenuImages/helpOptions.png");

    MenuButton continueButton = new MenuButton("continue.png", screenWidth/3 + screenHeight/5, screenHeight/2 + screenHeight/4, screenWidth/2, screenHeight/6);

    // Get screen width and height
    public static final int screenWidth = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    public static final int screenHeight = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();

    /**
     * Panel for when the game is over
     */
    HelpPanel() {
        // Ensures full screen and reduces rendering time!
        this.setDoubleBuffered(true);
        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());

        // Makes sure that panel can listen for key events
        this.addKeyListener(this);
        this.setFocusable(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            System.out.println("Pressed");
            Main.updateGameState(2); // Start the game immediately
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draws the fullscreen menu image
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(helpScreen, 0, 0, screenWidth, screenHeight, null);

        continueButton.drawButton(g2);
        continueButton.renderCurrentChoice(g2);
    }
}
