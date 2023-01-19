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
   private static InterFaceValid valid= (v,r,e)->{
      if(v=="" || !v.matches(r))
          throw new ValidationException(e);
    };
    public static void validateUserName(String userName) {
        valid.accept(userName,"(?=.{8}$)(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9]).*$","Your Username Is Invalid");
    }

    public static void validateEmail(String email) {
        valid.accept(email,"^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.]+@[a-zA-Z0-9.]+$","Your email Is Invalid");
    }

    public static void validateName(String name) {
        valid.accept(name,"[a-zA-Z]+","your name is invalid");
    }

}
