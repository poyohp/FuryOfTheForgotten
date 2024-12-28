package System.Panels;

import Handlers.ImageHandler;
import Handlers.KeyHandler;
import System.Main;
import System.Resources.MenuButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class AbstractPanel extends JPanel {

    // Loads menu image
    BufferedImage bgImage;

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
    ArrayList<MenuButton> buttons = new ArrayList<>();

    // Keyhandler to handle user options
    MenuButton selectedButton;

    public AbstractPanel(String imageSource) {
        this.setDoubleBuffered(true);
        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());

        bgImage = ImageHandler.loadImage("Panel.Images/" + imageSource);

        keyHandler = new KeyHandler();

        // Makes sure that panel can listen for key events
        addKeyListener(keyHandler);
        setFocusable(true);

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
    public abstract void handleChoice();

    /**
     * Adds all the buttons to an array list
     */
    public abstract void addButtonsToArrayList();

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draws the fullscreen menu image
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(bgImage, 0, 0, screenWidth, screenHeight, null);

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
