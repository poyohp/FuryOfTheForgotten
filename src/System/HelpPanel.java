package System;

import Handlers.ImageHandler;
import Handlers.KeyHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class HelpPanel extends JPanel {
    BufferedImage helpScreen = ImageHandler.loadImage("src/Assets/MenuImages/helpOptions.png");

    KeyHandler keyHandler;

    Timer timer;
    final int TIMERSPEED = 5;

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

        keyHandler = new KeyHandler();

        // Makes sure that panel can listen for key events
        addKeyListener(keyHandler);
        setFocusable(true);
        requestFocusInWindow();

        timer = new Timer(TIMERSPEED, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();

                if (keyHandler.choicePress) {
                    Main.updateGameState(2); // Start the game!
                    timer.stop();
                }
            }
        });

        timer.start();
    }

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
