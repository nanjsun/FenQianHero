package com.fenqian.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * FenQianHero class
 *
 * @author nanj
 * @date 2018/1/30
 */
public class ValidRegion {
    private BufferedImage bufferedImage;
    private int[] validRegion = new int[4];

    private final int DEVIDE = 5;
    private final int STEP_LENGTH = 5;

    public ValidRegion(BufferedImage bufferedImage){
        this.bufferedImage = bufferedImage;
    }



    public int[] getValidRegion(){
//        searchValidRegion();
        return validRegion;
    }

    private void searchValidRegion(){
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        System.out.println("width:" + width + "- height" + height);

        int firstFoundValidX = 0;
        int firstFoundValidY = 0;

        int validRegionLeft = 0;
        int validRegionTop = 0;
        int validRegionRight = 0;
        int validRegionBottom = 0;

        int validRegionWidth = 0;
        int validRegionHeight = 0;

        outLoop:
        for (int i = 0; i < (width / DEVIDE) - 2; i ++){
            for (int j = 0; j < (height / DEVIDE) - 2; j ++){
//                System.out.println(i * DEVIDE + "-" + j *DEVIDE);
                if((isWhitePoint(i * DEVIDE, j * DEVIDE))
                        && (isWhitePoint((i + 1) * DEVIDE, j * DEVIDE))
                        && (isWhitePoint(i * DEVIDE, (j + 1) * DEVIDE))
                        && (isWhitePoint((i + 1) * DEVIDE, (j + 1) * DEVIDE))){
                    firstFoundValidX = i * DEVIDE;
                    firstFoundValidY = j * DEVIDE;
                    System.out.println("firstFoundValidPoint X:" + firstFoundValidX + "-Y:" + firstFoundValidY);
                    break outLoop;
                }
            }
        }
        System.out.println("searching region");


        validRegionLeft = firstFoundValidX;
        validRegionTop = firstFoundValidY;
        validRegionRight = firstFoundValidX;
        validRegionBottom = firstFoundValidY;


//        find left of valid region
        for(int i = 0; i < firstFoundValidX / STEP_LENGTH -1; i ++){
            validRegionLeft = firstFoundValidX - i * STEP_LENGTH;
            if(!isWhitePoint(validRegionLeft, validRegionTop)
                    && !isWhitePoint(validRegionLeft - STEP_LENGTH, validRegionTop)
                    && !isWhitePoint(validRegionLeft, validRegionTop - STEP_LENGTH)
                    && !isWhitePoint(validRegionLeft - STEP_LENGTH, validRegionTop - STEP_LENGTH)){

                System.out.println("left:" + validRegionLeft);
                validRegion[0] = validRegionLeft;
                break;
            }
        }


//        find top of valid region
        for (int i = 0; i < height - firstFoundValidY / STEP_LENGTH -1; i ++){
            validRegionTop = firstFoundValidY - i * STEP_LENGTH;
            if(!isWhitePoint(validRegionLeft, validRegionTop)
                    && !isWhitePoint(validRegionLeft - STEP_LENGTH, validRegionTop)
                    && !isWhitePoint(validRegionLeft, validRegionTop - STEP_LENGTH)
                    && !isWhitePoint(validRegionLeft - STEP_LENGTH, validRegionTop - STEP_LENGTH)){

                System.out.println("top: " + validRegionTop);
                validRegion[1] = validRegionTop;
                break;
            }
            System.out.println(validRegionWidth + "and" + validRegionHeight);
        }

//        find valid region right
        for(int i = 0; i < (width - firstFoundValidX) / STEP_LENGTH -1; i ++){
            validRegionRight = firstFoundValidX + i * STEP_LENGTH;
            if(!isWhitePoint(validRegionRight, firstFoundValidY)
                    && !isWhitePoint(validRegionRight + STEP_LENGTH, firstFoundValidY)
                    && !isWhitePoint(validRegionRight, firstFoundValidY - STEP_LENGTH)
                    && !isWhitePoint(validRegionRight - STEP_LENGTH, firstFoundValidY - STEP_LENGTH)){

                System.out.println("right:" + validRegionRight);
                validRegion[2] = validRegionRight;
                break;
            }
        }

//        find valid region bottom
        boolean findBottomFlag = false;
        for (int i = 0; i < (height - firstFoundValidY) / STEP_LENGTH -1; i ++){
            validRegionBottom = firstFoundValidY + i * STEP_LENGTH;
            if(!isWhitePoint(firstFoundValidX, validRegionBottom)
                    && !isWhitePoint(firstFoundValidX + STEP_LENGTH, validRegionBottom)
                    && !isWhitePoint(firstFoundValidX, validRegionBottom + STEP_LENGTH)
                    && !isWhitePoint(firstFoundValidX + STEP_LENGTH, validRegionBottom + STEP_LENGTH)){
                System.out.println("bottom:" + validRegionBottom);
                validRegion[3] = validRegionBottom;
                findBottomFlag = true;
                break ;
            }
//            validRegionHeight = validRegionHeight + STEP_LENGTH;
//            System.out.println("width:" + validRegionWidth + "and height:" + validRegionHeight);
        }
        if(!findBottomFlag){
            validRegionBottom = height;
            validRegion[3] = height;
        }
    }
    private boolean isWhitePoint(int x, int y){
        int pixelPointSum = 0;
        int pixel = bufferedImage.getRGB(x, y);
        int r = (pixel & 0xff0000) >> 16;
        int g = (pixel & 0xff00) >> 8;
        int b = (pixel & 0xff);
        pixelPointSum = r + g + b;
        if (pixelPointSum / 3 < 240){
//            System.out.println("false");
            return false;
        }
//        System.out.println("true");
        return true;
    }

    public static void main(String[] args) throws IOException{
            BufferedImage bufferedImage;
            bufferedImage = ImageIO.read(new File("./photos/10.jpg"));
            ValidRegion validRegion = new ValidRegion(bufferedImage);
            validRegion.searchValidRegion();
            for(int i = 0; i < 4; i ++ ){
                System.out.println(validRegion.getValidRegion()[i]);

            }
    }

}
