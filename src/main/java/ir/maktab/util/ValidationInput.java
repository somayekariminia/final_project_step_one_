package ir.maktab.util;

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

    public static byte[] getSizeImage() throws IOException {
        BufferedImage originalImage = ImageIO.read(new File("R.jpg"));
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

    }

    public String validateUserName(String userName) {
        if (userName.matches("(?=.{8}$)(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9]).*$"))
            return userName;
        else
            throw new ValidationException("Your Username Is Invalid");
    }

    public String validateEmail(String email) {
        if (email.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.]+@[a-zA-Z0-9.]+$"))
            return email;
        else
            throw new ValidationException("Your email Is Invalid");
    }

    public String validateName(String name) {
        if (name.matches("[a-zA-Z]+"))
            return name;
        else
            throw new ValidationException("your name is invalid");
    }
}
