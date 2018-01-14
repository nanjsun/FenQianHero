package com.fenqian.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenShotImage {
    private BufferedImage bufferedImage;

    public BufferedImage getBufferedImage(int x, int y, int witdh, int height) {
        BufferedImage bfImage = null;

        try {
            Robot robot = new Robot();
            bfImage = robot.createScreenCapture(new Rectangle(x, y, witdh, height));

        } catch (AWTException e){
            e.printStackTrace();
        }
        this.bufferedImage = bfImage;
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

    public Boolean isValidImage(){
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        int pixelPointSum = 0;
        int pixelsCount = 0;

        for(int i = 0; i < width; i = i + width / 10){
            for(int j = 0; j < height; j = j + height / 10 ){
                int pixel = bufferedImage.getRGB(i, j);
                int r = (pixel & 0xff0000) >> 16;
                int g = (pixel &0xff00) >> 8;
                int b = (pixel &0xff);
                pixelPointSum = pixelPointSum + r + g + b;
                pixelsCount = pixelsCount + 3;

//                System.out.println("pixel :" + i + "-" + j + ":" + pixel);
//                System.out.println("r :" + i + "-" + j + ":" + r);
//                System.out.println("g :" + i + "-" + j + ":" + g);
//                System.out.println("b :" + i + "-" + j + ":" + b);
            }
        }
        if (pixelPointSum / pixelsCount < 200){
            System.out.println("false");
            return false;
        }

        System.out.println("true");

        return true;
    }

}
