package Handlers;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.io.File;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.util.Map;

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

    public static void drawRotatedImage(BufferedImage image, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, double angle, Graphics2D g2) {
        double centerX = dx1 + (dx2 - dx1) / 2.0;
        double centerY = dy1 + (dy2 - dy1) / 2.0;

        AffineTransform rotate = new AffineTransform();
        rotate.rotate(Math.PI*2-angle, centerX, centerY);

        AffineTransform translate = new AffineTransform();
        translate.translate(dx1 - centerX, dy1 - centerY);
        rotate.concatenate(translate);

        g2.setTransform(rotate);
        g2.drawImage(image, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
        g2.setTransform(new AffineTransform());
    }

}
