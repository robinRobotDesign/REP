import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;

public class makeBarcode {
	public static void makeCode(String code, String OrderNumber) {
		File file = new File("barcode.png");
		Barcode bc;
		try {
			bc = BarcodeFactory.createCode128(code);
			bc.setBarHeight(40);
			bc.setDrawingText(false);
	        BarcodeImageHandler.savePNG(bc, file);
	        BufferedImage image = ImageIO.read(file);
	        BufferedImage bi = new BufferedImage(image.getWidth(), 20,BufferedImage.TYPE_INT_RGB);
	        Graphics g = bi.createGraphics();
	        g.setColor(Color.white);
	        g.fillRect(0, 0, image.getWidth(), 20);
	        g.setColor(Color.black);
	        g.setFont(new Font("Arial Black", Font.BOLD, 20));
	        g.drawString(OrderNumber, 10, 20);
	        BufferedImage joinedImage = joinBufferedImage(bi, image);
	        ImageIO.write(joinedImage, "png", new File("combined.png"));
		} catch (OutputException e) {
			e.printStackTrace();
		} catch (BarcodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static BufferedImage joinBufferedImage(BufferedImage img1,BufferedImage img2) {

        //do some calculate first
        int offset  = 5;
        int wid =  Math.max(img1.getWidth(), img2.getWidth());
        int height = img1.getHeight()+img2.getHeight()+offset;
        //create a new buffer and draw two image into the new image
        BufferedImage newImage = new BufferedImage(wid,height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = newImage.createGraphics();
        //fill background
   //     g.setPaint(Color.WHITE);
        g.fillRect(0, 0, wid, height);
        //draw image
        g.drawImage(img1, null, 0, 0);
        g.drawImage(img2, null, 0, img1.getHeight()+offset);
        g.dispose();
        return newImage;
    }
}
