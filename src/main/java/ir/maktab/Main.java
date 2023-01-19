package ir.maktab;

import ir.maktab.data.model.entity.SubJob;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        try {
            final var sizeImage = getSizeImage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] getSizeImage() throws IOException {

        ImageInputStream imageInputStream = ImageIO.createImageInputStream(new File("OIF.jpg"));
        Iterator<ImageReader> imageReadersList = ImageIO.getImageReaders(imageInputStream);
        if (!imageReadersList.hasNext()) {
            throw new RuntimeException("Image Readers Not Found!!!");
        }
        ImageReader reader = imageReadersList.next();
        System.out.println("Image Format: " + reader.getFormatName());
        imageInputStream.close();
        BufferedImage bufferedImage=ImageIO.read(new File("image.png"));
        /*BufferedImage imBuff = ImageIO.read(object.getInputStream());
        ImageInputStream iis = ImageIO.createImageInputStream(is);*/
        return new byte[1400];
    }
}