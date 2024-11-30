package System;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static final GamePanel panel = new GamePanel();
//    public static final MenuPanel menu = new MenuPanel();

    public static void main(String[] args) {
//        JFrame menuWindow = new JFrame();
//        menuWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        menuWindow.setResizable(false);
//        menuWindow.setUndecorated(true);
//        menuWindow.add(menu);
//        menuWindow.pack();
//        menuWindow.setVisible(true);

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setUndecorated(true);
        window.setResizable(false);
        window.setTitle("Fury Of The Forgotten");

        window.add(panel);
        window.pack();
        window.setVisible(true);

        panel.initiateGamePanel();

    }
}
