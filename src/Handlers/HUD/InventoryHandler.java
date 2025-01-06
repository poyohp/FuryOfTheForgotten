package Handlers.HUD;

import Entities.Players.Player;
import Handlers.KeyHandler;
import Objects.Object;
import System.Panels.GamePanel;
import World.Tile;

import java.awt.*;
import java.util.Arrays;

public class InventoryHandler {

    private KeyHandler keyHandler;
    private Player player;

    private final int inventoryCapacity = 5;
    public Object[] inventory = new Object[inventoryCapacity];

    private final int inventoryDrawSize = Tile.normalTileSize;
    public int inventoryFinalDrawSize = Tile.tileMultipler*inventoryDrawSize;
    private final int innerGap = inventoryFinalDrawSize/6;
    private final int outerGap = inventoryFinalDrawSize/4;

    private final int outlineWidth =  (6*innerGap) + inventoryFinalDrawSize*inventoryCapacity;
    private final int outlineHeight = (2*innerGap) + inventoryFinalDrawSize;

    private final int outlineX = (int)(GamePanel.screenWidth - outerGap - outlineWidth);
    private final int outlineY = (int)(GamePanel.screenHeight - outerGap - outlineHeight);

    private final Color innerSquare = new Color(190,190,190, 220);

    public int indexSelected;
    public int indexFree;

    public InventoryHandler(KeyHandler keyHandler, Player player) {
        this.keyHandler = keyHandler;
        this.player = player;
        Arrays.fill(inventory, null);
    }

    public void update() {
        if (keyHandler.toggleInventory) {
            if (keyHandler.rightPress) {
                indexSelected++;
                if (indexSelected >= inventoryCapacity) {
                    indexSelected = 0;
                }
                keyHandler.rightPress = false;
            }
            if (keyHandler.leftPress) {
                indexSelected--;
                if (indexSelected < 0) {
                    indexSelected = inventoryCapacity - 1;
                }
                keyHandler.leftPress = false;
            }
            if(keyHandler.choicePress) {
                if(inventory[indexSelected] != null) {
                    if(inventory[indexSelected].name.equalsIgnoreCase("Key")) {
                        inventory[indexSelected].isUsed(player);
                        keyHandler.toggleInventory = false;
                    } else {
                        inventory[indexSelected].isUsed(player);
                        inventory[indexSelected] = null;
                        keyHandler.toggleInventory = false;
                    }
                }
            }
        }

        indexFree = -1;
        for(int i = 0; i < inventoryCapacity; i++) {
            if(inventory[i] == null) {
                indexFree = i;
                break;
            }
        }
    }

    public void draw(Graphics2D g2, int drawSize) {
        drawOutline(g2);
        drawInventorySquares(g2, outlineX, outlineY, drawSize);
        if(keyHandler.toggleInventory) {
            drawSelectedOutline(g2,drawSize);
        }
    }

    public void drawWithContraints(int x, int y, int width, int height, Graphics2D g2) {
        // Calculate the scale factor based on the width and height constraints
        double scaleX = (double) width / outlineWidth;
        double scaleY = (double) height / outlineHeight;
        double scale = Math.min(scaleX, scaleY);

        System.out.println("WHATAWE");

        // Calculate new dimensions and gaps based on the scale
        int scaledDrawSize = (int) (inventoryFinalDrawSize * scale);
        int scaledInnerGap = (int) (innerGap * scale);
        int scaledOuterGap = (int) (outerGap * scale);

        // Calculate the new outline dimensions
        int scaledOutlineWidth = (int) (outlineWidth * scale);
        int scaledOutlineHeight = (int) (outlineHeight * scale);
        int scaledOutlineX = x; // Use the provided x position
        int scaledOutlineY = y; // Use the provided y position

        // Draw the scaled outline
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke((float) (5 * scale)));
        g2.drawRoundRect(scaledOutlineX, scaledOutlineY, scaledOutlineWidth, scaledOutlineHeight, (int) (10 * scale), (int) (10 * scale));

        // Draw the scaled inventory squares
        int boxX = scaledOutlineX + scaledInnerGap;
        int boxY = scaledOutlineY + scaledInnerGap;

        g2.setColor(innerSquare);
        for (int i = 0; i < inventoryCapacity; i++) {
            if (inventory[i] == null) {
                g2.fillRoundRect(boxX, boxY, scaledDrawSize, scaledDrawSize, (int) (10 * scale), (int) (10 * scale));
            } else {
                inventory[i].drawHUD(g2, boxX, boxY, scaledDrawSize);
            }
            boxX += scaledInnerGap + scaledDrawSize;
        }

        // Draw the selected outline if inventory is toggled
        if (keyHandler.toggleInventory) {
            int selectedBoxX = scaledOutlineX + scaledInnerGap + (indexSelected * (scaledInnerGap + scaledDrawSize));
            int selectedBoxY = scaledOutlineY + scaledInnerGap;
            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke((float) (5 * scale)));
            g2.drawRoundRect(selectedBoxX, selectedBoxY, scaledDrawSize, scaledDrawSize, (int) (10 * scale), (int) (10 * scale));
        }
    }

    private void drawSelectedOutline(Graphics2D g2, int drawSize) {
        int boxX = outlineX + innerGap + (indexSelected * (innerGap + drawSize));
        int boxY = outlineY + innerGap;
        g2.setColor(Color.RED);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(boxX, boxY, drawSize, drawSize, 10, 10);
    }

    private void drawOutline(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(outlineX, outlineY,  outlineWidth, outlineHeight, 10, 10);
    }

    private void drawInventorySquares(Graphics2D g2, int outlineX, int outlineY, int drawSize) {
        int boxX = outlineX + innerGap;
        int boxY = outlineY+ innerGap;
        g2.setColor(innerSquare);
        for(int i = 0; i < inventoryCapacity; i++) {
            if(inventory[i] == null) {
                g2.fillRoundRect(boxX, boxY, drawSize, drawSize, 10, 10);
            } else {
                inventory[i].drawHUD(g2, boxX, boxY, drawSize);
            }
            boxX += innerGap + drawSize;
        }
    }



}
