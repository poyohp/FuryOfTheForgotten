package Handlers;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageHandler {

    public static BufferedImage loadImage(String filename) {
        BufferedImage img = null;
        try{
            img = ImageIO.read(new File(filename));
        } catch (IOException e) {
            System.out.println(e.toString());
            JOptionPane.showMessageDialog(null, "An image failed to load: " + filename, "Error", JOptionPane.ERROR_MESSAGE);
        }
        //DEBUG
        //if (img == null) System.out.println("null");
        //else System.out.printf("w=%d, h=%d%n",img.getWidth(), img.getHeight());
        return img;
    }

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
