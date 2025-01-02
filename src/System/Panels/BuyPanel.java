package System.Panels;

import Handlers.ImageHandler;
import Handlers.KeyHandler;
import System.Main;
import System.Resources.MenuButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class BuyPanel extends AbstractPanel {

    // Sets buttons and arraylist to keep them in
    //MenuButton continueButton = new MenuButton("continue.png", screenWidth/4 + screenWidth/17, screenHeight/2 + screenHeight/16, screenWidth/5*2, screenHeight/6);
    //MenuButton shopButton = new MenuButton("shop.png", screenWidth/3, screenHeight/2 + screenHeight/4, screenWidth/3, screenHeight/6);
    // moiz! put any buttons u need here

    MenuButton selectedButton;

    public BuyPanel() {
        super("shop2.png");

        // select a button to start with
//        continueButton.isSelected = true; // Begins with one button pre-selected
//        selectedButton = continueButton;

        addButtonsToArrayList();
    }

    /**
     * Gets the user's choice from the buttons
     */
    public void handleChoice() {
        //use this to handle any button choices
//        if (keyHandler.choicePress) {
//            timer.stop();
//            if (selectedButton == continueButton) {
//                Main.updateGameState(2); // Go to character selection screen
//            } else {
//                System.exit(0);
//            }
//        }
    }

    /**
     * Adds all the buttons to an array list
     */
    public void addButtonsToArrayList() {
        // add your buttons to this array list
//        buttons.add(continueButton);
//        buttons.add(shopButton);
    }

}
