package ir.maktab.util;

import ir.maktab.exception.ValidationException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class ValidationInput {

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


    public static void getSizeAimage() throws IOException {
        BufferedImage originalImage = ImageIO.read(new File("R.jpg"));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(originalImage, "jpg", bos);
        byte[] data = bos.toByteArray();
        ;
        System.out.println(data.length / 1024);
    }
}
