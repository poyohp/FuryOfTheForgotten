package Handlers.HUD;

import Handlers.KeyHandler;
import Objects.Object;
import System.Panels.GamePanel;
import World.Tile;

import java.awt.*;
import java.util.Arrays;

public class InventoryHandler {

    private KeyHandler keyHandler;

    private final int inventoryCapacity = 5;
    public Object[] inventory = new Object[inventoryCapacity];

    private final int inventoryDrawSize = Tile.normalTileSize;
    private final int inventoryFinalDrawSize = Tile.tileMultipler*inventoryDrawSize;
    private final int innerGap = inventoryFinalDrawSize/6;
    private final int outerGap = inventoryFinalDrawSize/4;

    private final int outlineWidth =  (6*innerGap) + inventoryFinalDrawSize*inventoryCapacity;
    private final int outlineHeight = (2*innerGap) + inventoryFinalDrawSize;

    private final int outlineX = (int)(GamePanel.screenWidth - outerGap - outlineWidth);
    private final int outlineY = (int)(GamePanel.screenHeight - outerGap - outlineHeight);

    private final Color innerSquare = new Color(190,190,190, 220);

    private int indexSelected;
    public int indexFree;

    public InventoryHandler(KeyHandler keyHandler) {
        this.keyHandler = keyHandler;
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
        } else {
            indexSelected = 0;
        }

        indexFree = -1;
        for(int i = 0; i < inventoryCapacity; i++) {
            if(inventory[i] == null) {
                indexFree = i;
                break;
            }
        }
    }

    public void draw(Graphics2D g2) {
        drawOutline(g2);
        drawInventorySquares(g2, outlineX, outlineY);
        if(keyHandler.toggleInventory) {
            drawSelectedOutlne(g2);
        }
    }

    private void drawSelectedOutlne(Graphics2D g2) {
        int boxX = outlineX + innerGap + (indexSelected * (innerGap + inventoryFinalDrawSize));
        int boxY = outlineY + innerGap;
        g2.setColor(Color.RED);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(boxX, boxY, inventoryFinalDrawSize, inventoryFinalDrawSize, 10, 10);
    }

    private void drawOutline(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(outlineX, outlineY,  outlineWidth, outlineHeight, 10, 10);
    }

    private void drawInventorySquares(Graphics2D g2, int outlineX, int outlineY) {
        int boxX = outlineX + innerGap;
        int boxY = outlineY+ innerGap;
        g2.setColor(innerSquare);
        for(int i = 0; i < inventoryCapacity; i++) {
            if(inventory[i] == null) {
                g2.fillRoundRect(boxX, boxY, inventoryFinalDrawSize, inventoryFinalDrawSize, 10, 10);
            } else {
                inventory[i].drawHUD(g2, boxX, boxY, inventoryFinalDrawSize);
            }
            boxX += innerGap + inventoryFinalDrawSize;
        }
    }



}
