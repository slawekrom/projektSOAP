package utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;


public class LoadImageUtil {

    public static byte[] loadImageAsByteArray(String name){
        BufferedImage bImage = null;
        try {
            bImage = ImageIO.read(new File(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bImage, "jpg", bos );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bos.toByteArray();
    }
    public byte[] loadImageFromURL(String path){
        BufferedImage bImage = null;
        try {
            URL url = new URL(path);
            bImage = ImageIO.read(url);
        } catch (IOException e) {
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bImage, "jpg", bos );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bos.toByteArray();
    }
}
