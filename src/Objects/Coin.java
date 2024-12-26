package Objects;

import Entities.Players.Player;
import Handlers.ImageHandler;
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
        determineImageCoords(rarity);
        determineValue(rarity);

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

    private void determineImageCoords(int rarity) {
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

    private void determineValue(int rarity) {
        //lowest rarity (brown) - 0
        //second lower (blue) - 1
        // mid (purple) - 2
        // high (pink) - 3
        switch(rarity) {
            case 0:
                value = GamePanel.random.nextInt(11);
                break;
            case 1:
                value = GamePanel.random.nextInt(11, 21);
                break;
            case 2:
                value = GamePanel.random.nextInt(21, 31);
                break;
            case 3:
                value = GamePanel.random.nextInt(31, 41);
                break;
            default:
                value = 0;
                break;
        }
    }

    @Override
    public void isInteracted(Player player, Level level) {
        System.out.println("HERE");
        player.coinValue += this.value;
        level.objectsToRemove.add(this);
    }

    @Override
    public void draw(Graphics2D g2) {
        updateFrames();
        g2.drawImage(image, (int) screenX, (int) screenY, (int) (screenX+width), (int) (screenY+height), imageX, imageY, imageX+imageWidth, imageY+imageWidth, null);
    }

}
