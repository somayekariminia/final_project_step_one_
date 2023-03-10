package ir.maktab.util;

import ir.maktab.exception.PhotoValidationException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class UtilImage {
    private static final int KILOBYTE = 1024;
    private static final int SIZEIMAGE = 300;

    private static void checkFormatImage(File file) {
        ImageInputStream imageInputStream;
        try {
            imageInputStream = ImageIO.createImageInputStream(file);
            Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(imageInputStream);
            if (!imageReaders.hasNext()) {
                throw new PhotoValidationException("Image Readers Not Found!!!");
            }
            ImageReader reader = imageReaders.next();
            if (!(reader.getFormatName().equalsIgnoreCase("jpeg") || reader.getFormatName().equalsIgnoreCase("jpg")))
                throw new PhotoValidationException("Photo format not valid should be jpg or jpeg format ");
            imageInputStream.close();
        } catch (IOException e) {
            throw new PhotoValidationException(e.getMessage());
        }

    }

    public static byte[] validateImage(File file) {
        try {
            checkFormatImage(file);
            BufferedImage originalImage = ImageIO.read(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(originalImage, "jpg", bos);
            if (bos.size() / KILOBYTE > SIZEIMAGE)
                throw new PhotoValidationException("format image bigger of 300Kb");
            return bos.toByteArray();
        } catch (IOException e) {
            throw new PhotoValidationException(e.getMessage());
        }
    }
}
