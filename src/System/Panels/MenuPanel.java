package System.Panels;

import System.Main;

import Handlers.ImageHandler;
import Handlers.KeyHandler;
import System.Resources.MenuButton;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MenuPanel extends JPanel {

    // Loads menu image
    BufferedImage menu = ImageHandler.loadImage("Panel.Images/menu.png");

    KeyHandler keyHandler;

    Timer timer;
    final int TIMERSPEED = 10;

    int cooldownCounter = 0;
    int cooldownTime = 200;
    boolean keyProcessed = false;

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

    public MenuPanel() {
        this.setDoubleBuffered(true);
        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());

        keyHandler = new KeyHandler();

        // Makes sure that panel can listen for key events
        addKeyListener(keyHandler);
        setFocusable(true);

        start.isSelected = true; // Begins with one button pre-selected
        selectedButton = start;

        addButtonsToArrayList();

        Main.hideCursor(this);

        timer = new Timer(TIMERSPEED, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSelection();
                repaint();
                handleChoice();
            }
        });

        timer.start();
    }

    /**
     * Gets the user's choice from the buttons
     */
    public void handleChoice() {
        if (keyHandler.choicePress) {
            timer.stop();
            if (selectedButton == start) {
                Main.updateGameState(6); // Go to character selection screen
            } else if (selectedButton == help) { // Update for help later
                Main.updateGameState(5); // Show ending screen!
            } else if (selectedButton == quit) {
                System.exit(0);
            }
        }
    }

    /**
     * Adds all the buttons to an array list
     */
    public void addButtonsToArrayList() {
        buttons.add(start);
        buttons.add(quit);
        buttons.add(help);
    }

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

    /**
     * Changes user's button selection if cooldown is not in effect and if the key has not been processed
     */
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

    /**
     * Sets the selected button to a new one
     */
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

}
