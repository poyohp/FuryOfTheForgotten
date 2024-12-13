package System;

import System.Panels.*;

import javax.swing.*;

public class Main {

    /**
     * All the game panel (for game states)
     */
    public static GamePanel gamePanel;
    public static MenuPanel menuPanel;
    public static GameOverPanel gameOverPanel = new GameOverPanel();
    public static WinPanel winPanel = new WinPanel();
    public static HelpPanel helpPanel;
    public static CharacterSelectionPanel characterSelectionPanel;


    public static JFrame window = new JFrame();


    public static void main(String[] args) {

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setUndecorated(true);
        window.setResizable(false);

        window.setTitle("Fury Of The Forgotten");
        updateGameState(1);
    }

    /**
     * Adds panel to frame based on game state
     * 1: Menu
     * 2: Game
     * 3: Game over
     * 4: Game won
     * @param gameState num representing state of the game
     */
    static public void updateGameState(int gameState) {
        window.getContentPane().removeAll(); // Remove current panel from JFrame

        // Add panel based on game state
        switch (gameState) {
            case 1:
                menuPanel = new MenuPanel();
                window.add(menuPanel);
                menuPanel.requestFocusInWindow();
                break;
            case 2:
                gamePanel = new GamePanel();
                window.add(gamePanel);
                gamePanel.initiateGamePanel();
                break;
            case 3:
                window.add(gameOverPanel);
                break;
            case 4:
                window.add(winPanel);
                break;
            case 5:
                helpPanel = new HelpPanel();
                window.add(helpPanel);
                helpPanel.requestFocusInWindow();
                break;
            case 6:
                characterSelectionPanel = new CharacterSelectionPanel();
                window.add(characterSelectionPanel);
                characterSelectionPanel.requestFocusInWindow();
                break;

        }

        window.pack();
        window.setVisible(true);
    }
}
