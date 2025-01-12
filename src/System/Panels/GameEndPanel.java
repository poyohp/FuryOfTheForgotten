package System.Panels;

import System.Main;

public class GameEndPanel extends AbstractPanel {

    public GameEndPanel(String imgSource) {
        super(imgSource);
    }

    @Override
    public void handleChoice() {
        if (keyHandler.shopExit) {
            timer.stop();
            Main.updateGameState(3); // Go to statistics screen
        }
    }

    @Override
    public void addButtonsToArrayList() {}
}
