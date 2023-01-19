package ir.maktab.util;

import ir.maktab.exception.NotFoundException;
import ir.maktab.exception.ValidationException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class UtilImage {
    private static void checkFormatImage(File file) throws IOException {
        ImageInputStream imageInputStream = ImageIO.createImageInputStream(file);
        Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(imageInputStream);
        if (!imageReaders.hasNext()) {
            throw new NotFoundException("Image Readers Not Found!!!");
        }
        ImageReader reader = imageReaders.next();
        if (!reader.getFormatName().equalsIgnoreCase("JPEG"))
            throw new ValidationException("Photo format not valid should be jpg or jpeg format ");
        imageInputStream.close();
    }

    public static byte[] validateImage(File file) throws IOException {
        checkFormatImage(file);
        BufferedImage originalImage = ImageIO.read(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(originalImage, "jpg", bos);
        if (bos.size() / 1024 > 300)
            throw new ValidationException("format image bigger of 300Kb");
        return bos.toByteArray();
    }
}
