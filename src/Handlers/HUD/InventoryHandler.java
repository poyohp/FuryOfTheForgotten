package Handlers.HUD;

import Handlers.KeyHandler;
import Objects.Object;
import System.Panels.GamePanel;
import World.Tile;

import java.awt.*;

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

    public InventoryHandler(KeyHandler keyHandler) {
        this.keyHandler = keyHandler;
    }

    public void update() {
        if(keyHandler.rightPress) {
            indexSelected++;
            if(indexSelected >= inventoryCapacity) {
                indexSelected = 0;
            }
        }
        if(keyHandler.leftPress) {
            indexSelected--;
            if(indexSelected < 0) {
                indexSelected = inventoryCapacity - 1;
            }
        }
    }


    public void draw(Graphics2D g2) {
        drawOutline(g2);
        drawInventorySquares(g2, outlineX, outlineY);
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
            g2.fillRoundRect(boxX, boxY, inventoryFinalDrawSize, inventoryFinalDrawSize, 10, 10);
            boxX += innerGap + inventoryFinalDrawSize;
        }
    }



}
