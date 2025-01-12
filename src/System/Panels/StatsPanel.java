package System.Panels;

import Handlers.LevelHandler;

import java.awt.*;

public class StatsPanel extends AbstractPanel {

    // Get screen width and height
    public static final int screenWidth = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    public static final int screenHeight = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();

    Font font = new Font("Courier", Font.PLAIN, 50);

    /**
     * Panel to show game stats
     */
    public StatsPanel() {
        super("gameStats.png");
    }

    @Override
    public void handleChoice() {
        if (keyHandler.shopExit) {
            timer.stop();
            System.exit(0); // Exit game!
        }

    }

    @Override
    public void addButtonsToArrayList() {}

    @Override
    public void paintComponent(Graphics g) {

        // Draws the fullscreen menu image
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(bgImage, 0, 0, screenWidth, screenHeight, null);

        g2.setPaint(Color.WHITE);
        g2.setFont(font);
        g2.drawString(String.valueOf(LevelHandler.enemiesKilled), screenWidth/2, screenHeight/4);
        g2.drawString(String.valueOf(LevelHandler.heartsLost), screenWidth/2, screenHeight/4 + screenHeight/8);
        g2.drawString(String.valueOf(LevelHandler.levelsCompleted), screenWidth/2, screenHeight/4 + screenHeight/8 * 2);
        // TOTAL TIME
        // FAV ITEM
    }
}
