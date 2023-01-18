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

public class ValidationInput {
    public static String validateUserName(String userName) {
        if (userName.matches("(?=.{8}$)(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9]).*$"))
            return userName;
        else
            throw new ValidationException("Your Username Is Invalid");
    }

    public static String validateEmail(String email) {
        if (email.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.]+@[a-zA-Z0-9.]+$"))
            return email;
        else
            throw new ValidationException("Your email Is Invalid");
    }

    public static String validateName(String name) {
        if (name.matches("[a-zA-Z]+"))
            return name;
        else
            throw new ValidationException("your name is invalid");
    }

    private static void checkFormatImage(File file) throws IOException {
        ImageInputStream imageInputStream = ImageIO.createImageInputStream(file);
        Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(imageInputStream);
        if (!imageReaders.hasNext()) {
            throw new NotFoundException("Image Readers Not Found!!!");
        }
        ImageReader reader = imageReaders.next();
        if (!reader.getFormatName().equals("jpej"))
            throw new ValidationException("Photo format not valid should be jpg format");
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
