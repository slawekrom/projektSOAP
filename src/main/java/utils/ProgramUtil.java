package utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;


public class ProgramUtil {

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
    public static String initFreePlaces(){
        return "1A;2A;3A;4A;5A;6A;7A;8A;9A;10A;11A;12A;13A;14A;15A;16A;17A;18A;19A;20A;" +
                "1B;2B;3B;4B;5B;6B;7B;8B;9B;10B;11B;12B;13B;14B;15B;16B;17B;18B;19B;20B;" +
                "1C;2C;3C;4C;5C;6C;7C;8C;9C;10C;11C;12C;13C;14C;15C;16C;17C;18C;19C;20C;" +
                "1D;2D;3D;4D;5D;6D;7D;8D;9D;10D;11D;12D;13D;14D;15D;16D;17D;18D;19D;20D;" +
                "1E;2E;3E;4E;5E;6E;7E;8E;9E;10E;11E;12E;13E;14E;15E;16E;17E;18E;19E;20E;" +
                "1F;2F;3F;4F;5F;6F;7F;8F;9F;10F;11F;12F;13F;14F;15F;16F;17F;18F;19F;20F";
    }
}
