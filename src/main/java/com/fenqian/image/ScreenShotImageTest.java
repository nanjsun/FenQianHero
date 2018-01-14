package com.fenqian.image;


import com.fenqian.image.ScreenShotImage;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenShotImageTest {
    @Test
    public void shotTest(){
        ScreenShotImage screenShotImage = new ScreenShotImage();
                BufferedImage bufferedImage = screenShotImage.getBufferedImage(660, 140, 600, 360);
        try {
            Date currentTime = new Date();
            SimpleDateFormat fomatter = new SimpleDateFormat("yyyyMMdd-HHmmss");
            String imageName = "./photos/" + fomatter.format(currentTime) + ".png";

            ImageIO.write(bufferedImage, "jpg", new File(".",imageName));


//        }catch (AWTException e){
//            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        screenShotImage.isValidImage();


    }

}
