package com.fenqian.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PushbackInputStream;
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

    public void readImage(String imagePath) throws IOException{
        this.bufferedImage = ImageIO.read(new File(imagePath));
    }

    public File getImage() {
        //image file name
        Date currentTime = new Date();
        SimpleDateFormat fomatter = new SimpleDateFormat("yyyyMMdd-HHmmss");
        String imageName = fomatter.format(currentTime) + ".png";

        File imageFile = new File("./photos/", imageName);

        ScreenShotImage screenShotImage = new ScreenShotImage();
        BufferedImage bufferedImage = screenShotImage.getBufferedImage(660, 140, 600, 360);
//        BufferedImage bufferedImage = screenShotImage.getBufferedImage(260, 140, 800, 800);
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

    public boolean isAnswerStatus(){
        int pixelPointSum = 0;
        int pixelCount = 0;
        boolean result = true;
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        for(int i = (height - 10) / 10; i > height / 30; --i){
            pixelCount ++;
            int x = width / 3;
            int y = i * 10;
            System.out.println("X - Y:" + x + ":" + y);
            int pixel = bufferedImage.getRGB(x ,y);


            int r = (pixel & 0xff0000) >> 16;
            int g = (pixel &0xff00) >> 8;
            int b = (pixel &0xff);
            pixelPointSum = pixelPointSum + (r + g + b) / 3;
            if((r < 60 && b > 200) || (r > 240 && g < 190)){
                result = false;
                break;
            }


//            System.out.println("pixel:" + (r + g + b) / 3 );
//            sumPixel = sumPixel + pixel;
        }
//        if(pixelPointSum / pixelCount < 240){
//            result = false;
//        } else {
//            result = true;
//        }

        System.out.println("pixelAverage:" + pixelPointSum / pixelCount );

        System.out.println("status:" + result);
        return result;
    }

    public static void main(String[] args) throws Exception{
        ScreenShotImage screenShotImage = new ScreenShotImage();

//        screenShotImage.readImage("./photos/2.png");
//        screenShotImage.readImage("./photos/validImage.jpg");
        screenShotImage.readImage("./photos/3.png");

        Boolean status = screenShotImage.isAnswerStatus();
        System.out.println(status);

    }
}
