package System.Panels;

import Handlers.ImageHandler;
import Handlers.KeyHandler;
import System.Resources.CharacterButton;
import System.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class CharacterSelectionPanel extends JPanel {

    // Loads menu image
    BufferedImage selection = ImageHandler.loadImage("Panel.Images/characterSelection.png");

    // Loads character images
    BufferedImage vampireImg = ImageHandler.loadImage("Entities/Players/Vampire/Vampire_idle.png");
    BufferedImage goblinImg = ImageHandler.loadImage("Entities/Players/Goblin/Goblin_Rogue.png");
    BufferedImage skeletonImg = ImageHandler.loadImage("Entities/Players/Skeleton/Sprites.png");
    BufferedImage zombieImg = ImageHandler.loadImage("Entities/Players/Zombie/zombies.png");

    KeyHandler keyHandler;

    Timer timer;
    final int TIMERSPEED = 10;

    int cooldownCounter = 0;
    int cooldownTime = 200;
    boolean keyProcessed = false;
    CharacterButton selectedButton;

    static String selectedCharacter;

    // Get screen width and height
    public static final int screenWidth = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    public static final int screenHeight = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();

    // Sets buttons and arraylist to keep them in
    CharacterButton zombie = new CharacterButton("zombie", screenWidth/8, screenHeight/7*6);
    CharacterButton skeleton = new CharacterButton("skeleton", screenWidth/8*3, screenHeight/7*6);
    CharacterButton goblin = new CharacterButton("goblin", screenWidth/8*5, screenHeight/7*6);
    CharacterButton vampire = new CharacterButton("vampire", screenWidth/8*7, screenHeight/7*6);
    ArrayList<CharacterButton> buttons = new ArrayList<>();

    public CharacterSelectionPanel() {
        this.setDoubleBuffered(true);
        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());

        keyHandler = new KeyHandler();

        // Makes sure that panel can listen for key events
        addKeyListener(keyHandler);
        setFocusable(true);

        zombie.isSelected = true; // Begins with one button pre-selected
        selectedButton = zombie;

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
            keyHandler.choicePress = false;
            timer.stop();
            selectedCharacter = selectedButton.characterType;
            Main.updateGameState(2);
        }
    }

    /**
     * Adds all the buttons to an array list
     */
    public void addButtonsToArrayList() {
        buttons.add(zombie);
        buttons.add(skeleton);
        buttons.add(goblin);
        buttons.add(vampire);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draws the fullscreen menu image
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(selection, 0, 0, screenWidth, screenHeight, null);

        //Draws characters
        g2.drawImage(zombieImg,
                screenWidth/8-140, screenHeight/3 - 50, screenWidth/8+150, screenHeight/2 + 100,
                0, 0, 128/4, 480/15, null);

        g2.drawImage(skeletonImg,
                screenWidth/8-140+ screenWidth/4, screenHeight/3 - 50, screenWidth/8+150+ screenWidth/4, screenHeight/2 + 100,
                0, 0, 128/4, 480/15, null);

        g2.drawImage(goblinImg,
                screenWidth/8-140+ screenWidth/4*2, screenHeight/3 - 50, screenWidth/8+150+ screenWidth/4*2, screenHeight/2 + 100,
                0, 0, 128/4, 864/27, null);

        g2.drawImage(vampireImg,
                screenWidth/8-110+ screenWidth/4*3, screenHeight/3 - 70, screenWidth/8+80+ screenWidth/4*3, screenHeight/2 + 30,
                0, 0, 64/4, 96/4, null);


        // Draw arrow if selected
        for (CharacterButton button : buttons) {
            if (button.isSelected) button.drawButton(g2);
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
        if (!keyProcessed && (keyHandler.leftPress || keyHandler.rightPress)) {
            setSelected();
            keyProcessed = true;
            cooldownCounter = cooldownTime;
        }

        // Means that key was released, and new key press can be processed
        if (!keyHandler.leftPress && !keyHandler.rightPress) {
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
        if (keyHandler.leftPress) {
            newIndex = oldIndex - 1; // Get the button to the left of the old button
            if (newIndex < 0) newIndex = buttons.size() - 1; // Makes sure that array access does not go out of bounds
        } else if (keyHandler.rightPress) {
            newIndex = oldIndex + 1; // Get the button to the right of the old button
            if (newIndex > buttons.size() - 1) newIndex = 0; // Makes sure that array access does not go out of bounds
        }

        // Reset all selected values
        for (CharacterButton button : buttons) {
            button.isSelected = false;
        }

        // Select the new button
        selectedButton = buttons.get(newIndex);
        buttons.get(newIndex).isSelected = true;
    }

}
