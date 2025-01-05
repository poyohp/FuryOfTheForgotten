package Objects.UnusableObjects;

import Entities.Players.Player;
import Handlers.ImageHandler;
import Objects.Object;
import System.Panels.GamePanel;
import World.Level;

import java.awt.*;

public class Coin extends Object {

    private int rarity;
    public int value;

    private int frameCounter;
    private final int animationFrames = 10;

    private int currentImage;
    private final int numImages = 2; //0-2 = 3 total

    public Coin(int rarity, String name, double width, double height, double worldX, double worldY, double screenX, double screenY, double vx, double vy) {
        super(name, width, height, worldX, worldY, screenX, screenY, vx, vy);
        this.rarity = rarity;
        determineValue();
        getImageCoords();

        frameCounter = 0;
        image = ImageHandler.loadImage("Assets/Objects/coins.png");
        currentImage = 0;

        isEquippable = false;

    }

    private void updateFrames() {
        if(frameCounter < animationFrames) {
            frameCounter++;

            imageY = currentImage*imageWidth;

        } else {

            currentImage++;
            if(currentImage > numImages) {
                currentImage = 0;
            }

            frameCounter = 0;
        }
    }

    @Override
    public void getImageCoords() {
        imageY = 0;
        switch(rarity) {
            case 0:
                //lowest rarity (brown)
                imageX = imageWidth*2;
                break;
            case 1:
                //second lower (blue)
                imageX = imageWidth*8;
                break;
            case 2:
                // mid (purple)
                imageX = imageWidth*6;
                break;
            case 3:
                // high (pink)
                imageX = imageWidth*7;
                break;
            default:
                imageX = 0;
                break;
        }
    }

    private void determineValue() {
        //lowest rarity (brown) - 0
        //second lower (blue) - 1
        // mid (purple) - 2
        // high (pink) - 3
        switch(rarity) {
            case 0:
                value = GamePanel.random.nextInt(1, 6);
                break;
            case 1:
                value = GamePanel.random.nextInt(6, 11);
                break;
            case 2:
                value = GamePanel.random.nextInt(11, 16);
                break;
            case 3:
                value = GamePanel.random.nextInt(16, 21);
                break;
            default:
                value = 0;
                break;
        }
    }

    @Override
    public void isPickedUp(Player player, Level level) {
        isPickedUp = true;
        player.coinValue += this.value;
        level.objectsToRemove.add(this);
    }

    @Override
    public void isUsed(Player player) {
        //DO NOTHING
        //COIN CANNOT BE USED
    }

    @Override
    public void isDropped(Player player, Level level) {
        //DO NOTHING
        //COIN CANNOT BE DROPPED!
    }

    @Override
    public void draw(Graphics2D g2) {
        updateFrames();
        g2.drawImage(image, (int) screenX, (int) screenY, (int) (screenX+width), (int) (screenY+height), imageX, imageY, imageX+imageWidth, imageY+imageWidth, null);
    }

    @Override
    public void drawHUD(Graphics2D g2, int x, int y, int size) {
        //NEVER DRAW COIN HERE
    }

}
