package System.Panels;

import System.Main;
import System.Resources.MenuButton;

public class MenuPanel extends AbstractPanel {

    // Sets buttons and arraylist to keep them in
    MenuButton start = new MenuButton("start.png", screenWidth/3, screenHeight/2 + screenHeight/16, screenWidth/3, screenHeight/6);
    MenuButton quit = new MenuButton("quit.png", screenWidth/3, screenHeight/2 + screenHeight/4, screenWidth/3, screenHeight/6);
    MenuButton help = new MenuButton("help.png", screenWidth/12, screenHeight/2 + screenHeight/4, screenHeight/6, screenHeight/6);

    public MenuPanel() {
        super("menu.png");
        start.isSelected = true; // Begins with one button pre-selected
        selectedButton = start;
        addButtonsToArrayList();
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
}
