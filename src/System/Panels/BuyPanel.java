package System.Panels;

import Handlers.ImageHandler;
import Handlers.KeyHandler;
import Handlers.ObjectHandler;
import System.Main;
import System.Resources.MenuButton;
import World.Tile;
import Objects.Object;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class BuyPanel extends AbstractPanel {

    final double SW = GamePanel.screenWidth;
    final double SH = GamePanel.screenHeight;

    //SPACING
    double HXOuter = GamePanel.screenWidth*0.1;
    double HXInner = HXOuter/2.0;

    double VXOuter = GamePanel.screenWidth*0.1;
    double VXInner = VXOuter/2.0;

    //SIZES
    int itemSize = (int) HXOuter;
    int inventoryWidth = (int) (itemSize*3 + HXInner*2);
    int inventoryHeight = itemSize;

    //INVENTORY POSITIONS
    int inventoryX, inventoryY;

    MenuButton itemDescription;
    MenuButton item1;
    MenuButton item2;
    MenuButton item3;
    MenuButton item4;
    MenuButton item5;
    MenuButton item6;

    MenuButton selectedButton;
    GamePanel gamePanel;

    ArrayList<Object> shopItems = new ArrayList<>();

    public void addObjectsToList() {
        for(int i = 0; i < buttons.size(); i++) {
            shopItems.add(ObjectHandler.getRandomObject(itemSize, itemSize, 0, 0,0, 0, 0, true));
        }
    }

    public BuyPanel(GamePanel gamePanel) {
        super("shop2.png");

        this.gamePanel = gamePanel;

        setButtons();
        addButtonsToArrayList();

        addObjectsToList();

        // select a button to start with
        item1.isSelected = true; // Begins with one button pre-selected
        selectedButton = item1;
    }

    public void setButtons() {
        int x = (int)HXOuter;
        int y = (int)VXOuter;

        itemDescription = new MenuButton("outline.png", x, y, (int) (SW*0.3), (int)(SH*0.7));

        x += (int) ((SW*0.3) + HXOuter);

        item1 = new MenuButton("outline.png", x, y, itemSize, itemSize);
        x += (int) (itemSize + HXInner);
        item2 = new MenuButton("outline.png", x, y, itemSize, itemSize);
        x += (int) (itemSize + HXInner);
        item3 = new MenuButton("outline.png", x, y, itemSize, itemSize);

        x = (int) ((SW*0.3) + HXOuter*2);
        y += (int) (itemSize + VXInner);

        item4 = new MenuButton("outline.png", x, y, itemSize, itemSize);
        x += (int) (itemSize + HXInner);
        item5 = new MenuButton("outline.png", x, y, itemSize, itemSize);
        x += (int) (itemSize + HXInner);
        item6 = new MenuButton("outline.png", x, y, itemSize, itemSize);

        inventoryX = (int) ((SW*0.3) + HXOuter*2);
        inventoryY = y + (int) (itemSize + VXInner);

    }

    /**
     * Gets the user's choice from the buttons
     */
    public void handleChoice() {
        //use this to handle any button choices
//        if (keyHandler.choicePress) {
//            timer.stop();
//            if (selectedButton == continueButton) {
//                Main.updateGameState(2); // Go to character selection screen
//            } else {
//                System.exit(0);
//            }
//        }
    }

    /**
     * Adds all the buttons to an array list
     */
    public void addButtonsToArrayList() {
        // add your buttons to this array list
        buttons.add(item1);
        buttons.add(item2);
        buttons.add(item3);
        buttons.add(item4);
        buttons.add(item5);
        buttons.add(item6);
    }

    @Override
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

    @Override
    public void handleSelection() {
        if (cooldownCounter > 0) {
            cooldownCounter -= TIMERSPEED; // Keep counting down cooldown time
            return; // No key processing while cooling down!
        }

        // If key is ready to be processed, process it
        // ALSO - PLAYER CANNOT BE IN THEIR INVENTORY AT THE SAME TIME AS BUTTONS
        if (!gamePanel.keyHandler.toggleInventory && !keyProcessed && (keyHandler.upPress || keyHandler.downPress)) {
            setSelected();
            keyProcessed = true;
            cooldownCounter = cooldownTime;
        }

        // Means that key was released, and new key press can be processed
        if (!keyHandler.upPress && !keyHandler.downPress) {
            keyProcessed = false;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draws the fullscreen menu image
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(bgImage, 0, 0, screenWidth, screenHeight, null);

        //DRAW BUTTONS AND CORRESPONDING ITEM!
        itemDescription.drawButton(g2);

        for(int i = 0; i < buttons.size(); i++) {
            MenuButton button = buttons.get(i);
            Object item = shopItems.get(i);

            button.drawButton(g2);
            item.drawHUD(g2, button.x, button.y, itemSize);
            if (button.isSelected) button.renderCurrentChoice(g2);
        }

        gamePanel.inventory.drawWithContraints(inventoryX, inventoryY, inventoryWidth, inventoryHeight, g2);

    }

}
