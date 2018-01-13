package com.fenqian;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenShotImage {
    public BufferedImage getBufferedImage(int x, int y, int witdh, int height) {
        BufferedImage bfImage = null;

        try {
            Robot robot = new Robot();
            bfImage = robot.createScreenCapture(new Rectangle(x, y, witdh, height));

        } catch (AWTException e){
            e.printStackTrace();
        }
        return bfImage;
    }
    public File getImage() {
        //image file name
        Date currentTime = new Date();
        SimpleDateFormat fomatter = new SimpleDateFormat("yyyyMMdd-HHmmss");
        String imageName = fomatter.format(currentTime) + ".png";

        File imageFile = new File("./photos/", imageName);

        ScreenShotImage screenShotImage = new ScreenShotImage();
        BufferedImage bufferedImage = screenShotImage.getBufferedImage(660, 140, 600, 360);
        try {
            ImageIO.write(bufferedImage, "png", imageFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile;
    }

}
