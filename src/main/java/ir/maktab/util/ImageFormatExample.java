package ir.maktab.util;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 * Get Image Format Example
 *
 * @author Krishna
 *
 */
public class ImageFormatExample {
    public static void main(String[] args) throws IOException {

//Create Image File
        File imageFile = new File("Desert.jpg");

//Create ImageInputStream using Image File
        ImageInputStream imageInputStream = ImageIO.createImageInputStream(new File("image.png"));
        BufferedImage image = ImageIO.read(imageInputStream);
//Get the image readers for that file
        Iterator<ImageReader> imageReadersList = ImageIO.getImageReaders(imageInputStream);

        if (!imageReadersList.hasNext()) {
            throw new RuntimeException("Image Readers Not Found!!!");
        }

//Get the image type
        ImageReader reader = imageReadersList.next();
        System.out.println("Image Format: " + reader.getFormatName());

//Close stream (best practice)
        imageInputStream.close();
    }
}
