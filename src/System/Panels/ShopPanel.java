package System.Panels;

import System.Main;
import System.Resources.MenuButton;

public class ShopPanel extends AbstractPanel {

    // Sets buttons and arraylist to keep them in
    MenuButton continueButton = new MenuButton("continue.png", screenWidth/4, screenHeight/2 + screenHeight/16, screenWidth/4*2, screenHeight/6);
    MenuButton shopButton = new MenuButton("shop.png", screenWidth/3, screenHeight/2 + screenHeight/4, screenWidth/3, screenHeight/6);


    public ShopPanel() {
        super("shop1.png");

        continueButton.isSelected = true; // Begins with one button pre-selected
        selectedButton = continueButton;

        addButtonsToArrayList();

        keyHandler.allowInventoryToggle = false;

    }

    /**
     * Gets the user's choice from the buttons
     */
    public void handleChoice() {
        if (keyHandler.choicePress) {
            Main.gamePanel.keyHandler.toggleInventory = false; //also sets inventory to not be toggled
            if (selectedButton == continueButton) {
                keyHandler.choicePress = false;
                // Return to game, go to next level
                GamePanel currentGamePanel = Main.gamePanel;
                Main.updateGameState(2);
                currentGamePanel.resumeGame();
                currentGamePanel.levelHandler.goToNextLevel(currentGamePanel.spawnHandler, currentGamePanel.player, currentGamePanel.attackHandler, currentGamePanel, currentGamePanel.abilityHandler);
                currentGamePanel.levelHandler.levelComplete = false;
                currentGamePanel.snail.resetPosition();
            } else {
                keyHandler.choicePress = false;
                Main.updateGameState(8); // continue on to shop
            }
        }
    }

    /**
     * Adds all the buttons to an array list
     */
    public void addButtonsToArrayList() {
        buttons.add(continueButton);
        buttons.add(shopButton);
    }
}
