package System.Panels;

import System.Main;

public class GameEndPanel extends AbstractPanel {

    public GameEndPanel(String imgSource) {
        super(imgSource);
    }

    /**
     * End the game --> Go to statistics page
     */
    @Override
    public void handleChoice() {
        if (keyHandler.shopExit) {
            timer.stop();
            Main.updateGameState(3); // Go to statistics screen
        }
    }

    @Override
    public void setSelected() {}

    @Override
    public void addButtonsToArrayList() {}
}
