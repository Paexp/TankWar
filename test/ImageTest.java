import org.junit.jupiter.api.Test;
import tech.paexp.tank.ResourceMgr;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ImageTest {

    @Test
    public void testLoadImage() {
        try {
            BufferedImage image = ImageIO.read(new File("D:/Codes/TankWar/src/images/bulletD.gif"));
            assertNotNull(image);

            BufferedImage image2 = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/GoodTank1.png"));
            // this.getClass()
            assertNotNull(image2);

            BufferedImage image3 = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/e" + 1 + ".gif"));
            assertNotNull(image3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRotedImage() {
        try {
            BufferedImage tankL = ImageIO.read(ImageTest.class.getClassLoader().getResourceAsStream("images/GoodTank1.png"));
            tankL = rotateImage(tankL, 90);
            assertNotNull(tankL);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private BufferedImage rotateImage(final BufferedImage bufferedImage,
                                      final int degree) {
        int w = bufferedImage.getWidth();
        int h = bufferedImage.getHeight();
        int type = bufferedImage.getColorModel().getTransparency();
        BufferedImage img;
        Graphics2D graphics2D;
        (graphics2D = (img = new BufferedImage(w, h, type))
                .createGraphics()).setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.rotate(Math.toRadians(degree), w/2,h/2);
        graphics2D.drawImage(bufferedImage, 0, 0, null);
        graphics2D.dispose();
        return img;
    }

}
