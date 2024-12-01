package Handlers;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageHandler {

    /**
     * Static method to load an image from a specified path
     * @param filename the path to the image
     * @return
     */
    public static BufferedImage loadImage(String filename) {
        BufferedImage img = null;
        try{
            img = ImageIO.read(new File(filename));
        } catch (IOException e) {
            System.out.println(e.toString());
            JOptionPane.showMessageDialog(null, "An image failed to load: " + filename, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return img;
    }

    /**
     * Static method to draw a rotated image (rotates around the center of the image, draws, then resets rotation)
     * @param g2 the graphics object
     * @param centerX the x coordinate of the center of the object to draw
     * @param centerY the y coordinate of the center of the object to draw
     * @param angle the angle to rotate the image
     * @param image the image to draw
     * @param dx1 the x coordinate of the first corner of the destination rectangle
     * @param dy1 the y coordinate of the first corner of the destination rectangle
     * @param dx2 the x coordinate of the second corner of the destination rectangle
     * @param dy2 the y coordinate of the second corner of the destination rectangle
     * @param sx1 the x coordinate of the first corner of the source rectangle
     * @param sy1 the y coordinate of the first corner of the source rectangle
     * @param sx2 the x coordinate of the second corner of the source rectangle
     * @param sy2 the y coordinate of the second corner of the source rectangle
     */
    public static void drawRotatedImage(Graphics2D g2, int centerX, int centerY, double angle, BufferedImage image, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2) {

        // GETTING ORIGINAL ROTATION
        AffineTransform originalTransform = g2.getTransform();

        // ROTATING IMAGE
        g2.rotate(angle, centerX, centerY);
        g2.drawImage(image, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);

        // RESETTING ORIGINAL ROTATION
        g2.setTransform(originalTransform);
    }

}
