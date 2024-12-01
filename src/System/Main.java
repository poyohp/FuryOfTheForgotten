package System;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static final GamePanel panel = new GamePanel();
    public static MenuFrame menuWindow;

    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setUndecorated(true);
        window.setResizable(false);
        window.setTitle("Fury Of The Forgotten");
        window.add(panel);
        window.pack();

        menuWindow = new MenuFrame(window, panel);
    }
}
