package System.Panels;

import System.Main;
import System.Resources.MenuButton;

public class ShopPanel extends AbstractPanel {

    // Sets buttons and arraylist to keep them in
    MenuButton continueButton = new MenuButton("continue.png", screenWidth/4 + screenWidth/17, screenHeight/2 + screenHeight/16, screenWidth/5*2, screenHeight/6);
    MenuButton shopButton = new MenuButton("shop.png", screenWidth/3, screenHeight/2 + screenHeight/4, screenWidth/3, screenHeight/6);


    public ShopPanel() {
        super("shop1.png");

        continueButton.isSelected = true; // Begins with one button pre-selected
        selectedButton = continueButton;

        addButtonsToArrayList();
    }

    /**
     * Gets the user's choice from the buttons
     */
    public void handleChoice() {
        if (keyHandler.choicePress) {
            timer.stop();
            if (selectedButton == continueButton) {
                // Return to game, go to next level
                GamePanel currentGamePanel = Main.gamePanel;
                Main.updateGameState(2);
                currentGamePanel.resumeGame();
                System.out.println("GOT HERE!");
                currentGamePanel.levelHandler.goToNextLevel(currentGamePanel.spawnHandler, currentGamePanel.player, currentGamePanel.attackHandler, currentGamePanel.inventory);
                currentGamePanel.levelHandler.levelComplete = false;
            } else {
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
