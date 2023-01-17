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

    }
    public static byte[] getSizeImage() throws IOException {
        BufferedImage originalImage = ImageIO.read(new File("image.png"));
        ImageInputStream imageInputStream = ImageIO.createImageInputStream(originalImage);
        Iterator<ImageReader> imageReadersList = ImageIO.getImageReaders(imageInputStream);
        if (!imageReadersList.hasNext()) {
            throw new RuntimeException("Image Readers Not Found!!!");
        }
        ImageReader reader = imageReadersList.next();
        System.out.println("Image Format: " + reader.getFormatName());
        imageInputStream.close();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(originalImage, "jpg", bos);
       return bos.toByteArray();
        /*BufferedImage imBuff = ImageIO.read(object.getInputStream());
        ImageInputStream iis = ImageIO.createImageInputStream(is);*/
    }

}