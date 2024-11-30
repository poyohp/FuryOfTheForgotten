package System;

import Handlers.KeyHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class MenuFrame extends JFrame {
    // Loads menu image
    BufferedImage menu = loadImage();

    KeyHandler keyHandler;

    Timer timer;
    final int TIMERSPEED = 10;

    int cooldownCounter = 0;
    int cooldownTime = 200;
    boolean keyProcessed = false;

    boolean proceed;

    // Get screen width and height
    public static final int screenWidth = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    public static final int screenHeight = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();

    // Sets buttons and arraylist to keep them in
    MenuButton start = new MenuButton("start.png", screenWidth/3, screenHeight/2 + screenHeight/16, screenWidth/3, screenHeight/6);
    MenuButton quit = new MenuButton("quit.png", screenWidth/3, screenHeight/2 + screenHeight/4, screenWidth/3, screenHeight/6);
    MenuButton help = new MenuButton("help.png", screenWidth/12, screenHeight/2 + screenHeight/4, screenHeight/6, screenHeight/6);
    ArrayList<MenuButton> buttons = new ArrayList<>();

    // Keyhandler to handle user options
    MenuButton selectedButton;

    MenuFrame(JFrame window, GamePanel gamePanel) {
        keyHandler = new KeyHandler();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setUndecorated(true);

        DrawingPanel panel = new DrawingPanel();

        panel.setDoubleBuffered(true);
        panel.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());

        panel.addKeyListener(keyHandler);
        panel.setFocusable(true);
        panel.requestFocusInWindow();

        start.isSelected = true; // Begins with one button pre-selected
        selectedButton = start;

        addButtonsToArrayList();

        this.add(panel);
        this.pack();
        this.setVisible(true);

        timer = new Timer(TIMERSPEED, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSelection();
                panel.repaint();
                handleChoice(window, gamePanel);
            }
        });

        timer.start();
    }

    public void handleChoice(JFrame window, GamePanel panel) {
        if (keyHandler.choicePress) {
            if (selectedButton == start) {
                window.setVisible(true);
                panel.initiateGamePanel();
                timer.stop();
                MenuFrame.this.dispose();
            } else { // Update for quit and help later
                timer.stop();
                MenuFrame.this.dispose();
            }
        }
    }

    public void addButtonsToArrayList() {
        buttons.add(start);
        buttons.add(quit);
        buttons.add(help);
    }

    private class DrawingPanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Draws the fullscreen menu image
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(menu, 0, 0, screenWidth, screenHeight, null);

            // Draw buttons. If one is selected, then draw it as selected.
            for (MenuButton button : buttons) {
                button.drawButton(g2);
                if (button.isSelected) button.renderCurrentChoice(g2);
            }
        }
    }

    public void handleSelection() {
        if (cooldownCounter > 0) {
            cooldownCounter -= TIMERSPEED; // Keep counting down cooldown time
            return; // No key processing while cooling down!
        }

        // If key is ready to be processed, process it
        if (!keyProcessed && (keyHandler.upPress || keyHandler.downPress)) {
            setSelected();
            keyProcessed = true;
            cooldownCounter = cooldownTime;
        }

        // Means that key was released, and new key press can be processed
        if (!keyHandler.upPress && !keyHandler.downPress) {
            keyProcessed = false;
        }
    }

    public void setSelected() {
        // Get index of selected button
        int oldIndex = buttons.indexOf(selectedButton);

        int newIndex = 0;
        if (keyHandler.upPress) {
            newIndex = oldIndex - 1; // Get the button above the old button
            if (newIndex < 0) newIndex = buttons.size() - 1; // Makes sure that array access does not go out of bounds
        } else if (keyHandler.downPress) {
            newIndex = oldIndex + 1;
            if (newIndex > buttons.size() - 1) newIndex = 0;
        }

        // Reset all selected values
        for (MenuButton button : buttons) {
            button.isSelected = false;
        }

        // Select the new button
        selectedButton = buttons.get(newIndex);
        buttons.get(newIndex).isSelected = true;
    }

    BufferedImage loadImage() {
        BufferedImage image = null;
        java.net.URL url = this.getClass().getResource("/MenuImages/menu.png");
        try {
            image = ImageIO.read(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return image;
    }
}
