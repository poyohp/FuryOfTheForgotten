package System;

import javax.swing.*;

public class Main {

    public static final GamePanel gamePanel = new GamePanel();
    public static MenuPanel menuPanel = new MenuPanel();
    public static FinishPanel finishPanel = new FinishPanel();

    public static JFrame window = new JFrame();

    public static void main(String[] args) {

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setUndecorated(true);
        window.setResizable(false);

        window.setTitle("Fury Of The Forgotten");
        updateGameState(1);
    }

    static public void updateGameState(int gameState) {
        window.getContentPane().removeAll();

        switch (gameState) {
            case 1:
                window.add(menuPanel);
                break;
            case 2:
                window.add(gamePanel);
                gamePanel.initiateGamePanel();
                break;
            case 3:
                window.add(finishPanel);
        }
        window.pack();
        window.setVisible(true);
    }
}
