package System.Panels;

import Handlers.HUD.InventoryHandler;
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

    GamePanel gamePanel;

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
    int descriptionWidth = (int) (SW*0.3);
    int descriptionHeight = (int) (SH*0.7);

    //POSITIONS
    int descriptionX, descriptionY;
    int inventoryX, inventoryY;
    int itemX, itemY;

    MenuButton item1;
    MenuButton item2;
    MenuButton item3;
    MenuButton item4;
    MenuButton item5;
    MenuButton item6;
    MenuButton selectedButton;

    int currentButtonX;
    int currentButtonY;
    int[][] buttonIndexes = {{0, 1, 2},
                            {3, 4, 5}};

    ArrayList<Object> shopItems = new ArrayList<>();

    public BuyPanel(GamePanel gamePanel) {
        super("shop2.png");

        this.gamePanel = gamePanel;
        keyHandler.allowInventoryToggle = false;

        setButtons();
        addButtonsToArrayList();

        addObjectsToList();

        item1.isSelected = true;
        currentButtonX = currentButtonY = 0;
        selectedButton = item1;

        //"OVERRIDE"
        //TO ADD INVENTORY UPDATE!
        timer = new Timer(TIMERSPEED, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSelection();
                gamePanel.inventory.update(keyHandler);
                repaint();
                handleChoice();
            }
        });

        timer.start();

    }

    public void addObjectsToList() {
        //MAKES EVERY ITEM UNIQUE
        for(int i = 0; i < buttons.size(); i++) {
            Object objectToAdd = ObjectHandler.getRandomObject(itemSize, itemSize, 0, 0,0, 0, 0, true);
            boolean addItem = true;

            for (int j = 0; j < shopItems.size(); j++) {
                if (objectToAdd.equals(shopItems.get(j))) {
                    addItem = false;
                    break;
                }
            }

            if(addItem) {
                shopItems.add(objectToAdd);
            } else {
                i--;
            }
        }
    }

    public void setButtons() {
        int x = (int)HXOuter;
        int y = (int)VXOuter;

        descriptionX = x;
        descriptionY = y;

        itemX = descriptionX + (int) ((descriptionWidth-itemSize)/2.0);
        itemY = descriptionY + (int) (itemSize * (3.0/4.0));

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
        //"BUY BUTTON"

        Object item = shopItems.get(buttonIndexes[currentButtonY][currentButtonX]);

        if(keyHandler.choicePress) {
            if(gamePanel.inventory.indexFree != -1) {
                //FREE SLOT
                if(item.value <= gamePanel.player.coinValue) {
                    gamePanel.inventory.inventory[gamePanel.inventory.indexFree] = item;
                    gamePanel.player.coinValue -= item.value;
                } else {
                    System.out.println("NOT ENOUGH COINS!");
                }
                keyHandler.choicePress = false;
            } else if(keyHandler.toggleInventory)  {
                //REPLACING ITEM
                gamePanel.inventory.inventory[gamePanel.inventory.indexSelected] = item;
                gamePanel.player.coinValue -= item.value;
                keyHandler.toggleInventory = false;
                keyHandler.choicePress = false;
            } else {
                //ALLOW FOR REPLACING
                if(item.value <= gamePanel.player.coinValue) {
                    keyHandler.toggleInventory = true;
                } else {
                    System.out.println("NOT ENOUGH COINS!");
                }
                keyHandler.choicePress = false;
            }
        }

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
        if (keyHandler.upPress) {
            currentButtonY --;
            if (currentButtonY < 0) currentButtonY = buttonIndexes.length-1;
        } else if (keyHandler.downPress) {
            currentButtonY ++;
            if (currentButtonY > buttonIndexes.length-1) currentButtonY = 0;
        } else if(keyHandler.leftPress) {
            currentButtonX --;
            if (currentButtonX < 0) currentButtonX = buttonIndexes[0].length-1;
        } else if(keyHandler.rightPress) {
            currentButtonX ++;
            if (currentButtonX > buttonIndexes[0].length-1) currentButtonX = 0;
        }

        // Reset all selected values
        for (MenuButton button : buttons) {
            button.isSelected = false;
        }

        // Select the new button
        selectedButton = buttons.get(buttonIndexes[currentButtonY][currentButtonX]);
        selectedButton.isSelected = true;
    }

    @Override
    public void handleSelection() {
        if (cooldownCounter > 0) {
            cooldownCounter -= TIMERSPEED; // Keep counting down cooldown time
            return; // No key processing while cooling down!
        }

        // If key is ready to be processed, process it
        // ALSO - PLAYER CANNOT BE IN THEIR INVENTORY AT THE SAME TIME AS BUTTONS
        if (!keyHandler.toggleInventory && !keyProcessed && (keyHandler.upPress || keyHandler.downPress || keyHandler.leftPress || keyHandler.rightPress)) {
            setSelected();
            keyProcessed = true;
            cooldownCounter = cooldownTime;
        }

        // Means that key was released, and new key press can be processed
        if (!keyHandler.upPress && !keyHandler.downPress && !keyHandler.leftPress && !keyHandler.rightPress) {
            keyProcessed = false;
        }

        if(keyHandler.shopExit) {
            //GO BACK TO CONTINUE/SHOP PAGE!
            Main.updateGameState(7);
            keyHandler.shopExit = false;
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draws the fullscreen menu image
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(bgImage, 0, 0, screenWidth, screenHeight, null);

        //DRAW BUTTONS AND CORRESPONDING ITEM AND DESCRIPTION!
        for(int i = 0; i < buttons.size(); i++) {
            MenuButton button = buttons.get(i);
            Object item = shopItems.get(i);

            button.drawButton(g2);
            item.drawHUD(g2, button.x, button.y, itemSize);
            if (button.isSelected) button.renderCurrentChoice(g2);
        }

        //DESCRIPTION
        shopItems.get(buttonIndexes[currentButtonY][currentButtonX]).drawDescription(g2, descriptionX, descriptionY, descriptionWidth, descriptionHeight);

        //ITEM IMAGE
        shopItems.get(buttonIndexes[currentButtonY][currentButtonX]).drawHUD(g2, itemX, itemY, itemSize);

        gamePanel.inventory.drawWithContraints(inventoryX, inventoryY, inventoryWidth, inventoryHeight, g2, keyHandler.toggleInventory);
        gamePanel.objectHandler.drawCoin(g2, gamePanel.player);

        if(keyHandler.toggleInventory) {
            //EXPLAIN HOW TO REPLACE
            String instructions = "SELECT AN ITEM TO REPLACE ('J')";
            g2.drawString(instructions, inventoryX, inventoryY - 50);
        }

        String instructions = "'J' TO PURCHASE | 'L' TO EXIT";
        g2.setColor(Color.WHITE);
        g2.drawString(instructions, inventoryX + 25, (int) (GamePanel.screenHeight - 50));

    }

}
