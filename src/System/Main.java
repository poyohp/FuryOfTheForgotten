package System;

import javax.swing.*;

public class Main {

    /**
     * All the game panel (for game states)
     */
    public static final GamePanel gamePanel = new GamePanel();
    public static MenuPanel menuPanel = new MenuPanel();
    public static GameOverPanel gameOverPanel = new GameOverPanel();
    public static WinPanel winPanel = new WinPanel();

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
                window.add(menuPanel);
                break;
            case 2:
                window.remove(menuPanel);
                window.add(gamePanel);
                gamePanel.initiateGamePanel();
                break;
            case 3:
                window.remove(gamePanel);
                window.add(gameOverPanel);
                break;
            case 4:
                window.remove(gamePanel);
                window.add(winPanel);
        }

        window.pack();
        window.setVisible(true);
    }
}
