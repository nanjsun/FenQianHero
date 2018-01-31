package com.fenqian;

import com.fenqian.click.HandIn;
import com.fenqian.image.ScreenShotImage;
import com.fenqian.image.ValidRegion;
import com.fenqian.ocr.ali.AliOCR;
import com.fenqian.ocr.baidu.BaiduOCR;
import com.fenqian.search.SearchQuestion;
import org.omg.CORBA.IMP_LIMIT;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author nanj
 */
public class LetUsGetMoney {
    private final int NUM_OF_ANSWERS = 2;
    private final String QUESTION_FLAG="?";
    private String[] questionAndOptions = new String[4];
    private String[] answers = new String[3];
    private String question;
    private long startTime;
    private long endTime;
    private int validRegionLeft = 660;
    private int validRegionTop = 140;
    private int validRegionWidth = 600;
    private int validRegionHeight = 360;

    private int globalRegionLeft = 160;
    private int globalRegionTop = 120;
    private int globalRegionWidth = 1000;
    private int globalRegionHeight = 1000;

    private int[] optionCentralCoordinates = new int[6];

    private String lastQuestionKeyWord = "Nothing";
    private String ocrSupplier = "baidu";


    public void init() {
        ScreenShotImage screenShotImage = new ScreenShotImage();
        BufferedImage globalImage = screenShotImage.getBufferedImage(globalRegionLeft,globalRegionTop,
                globalRegionWidth,globalRegionHeight);

        try {
            File globaleImageFile = new File(".","./photos/globalImage.png");
            ImageIO.write(globalImage,"png",globaleImageFile);
        } catch (IOException e){
            e.printStackTrace();
        }
        ValidRegion validRegion = new ValidRegion(globalImage);
        int[] validRegionCoordinate = validRegion.getValidRegion();

        validRegionLeft = validRegionCoordinate[0] + globalRegionLeft;
        validRegionTop = validRegionCoordinate[1] + globalRegionTop;

        validRegionWidth = validRegionCoordinate[2] - validRegionCoordinate[0];
        validRegionHeight = validRegionCoordinate[3] - validRegionCoordinate[1];

    }

    public void startWithOnlineOCR() throws IOException{
        boolean imageValid = false;
        int recaptureCount = 0;
        ScreenShotImage screenShotImage = new ScreenShotImage();
        BufferedImage bufferedImage = screenShotImage.getBufferedImage(validRegionLeft, validRegionTop,
                validRegionWidth, validRegionHeight);

        try {
            File validRegionImageFile = new File(".", "./photos/validRegion.jpg");
            ImageIO.write(bufferedImage,"jpg",validRegionImageFile);
        } catch (IOException e){
            e.printStackTrace();
        }

        if(screenShotImage.isValidImage()){
            imageValid = true;
        } else {
            while (!imageValid){
                recaptureCount ++;
                System.out.println("invalid image, recapturing imags, count:" + recaptureCount);
                bufferedImage = screenShotImage.getBufferedImage(validRegionLeft, validRegionTop,
                        validRegionWidth, validRegionHeight);
                if(screenShotImage.isValidImage()){
                    imageValid = true;
                }
                try{
                    Thread.sleep(500);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
        }
        startTime = System.currentTimeMillis();

        if("baidu".equals(ocrSupplier)){
            BaiduOCR baiduOCR = new BaiduOCR();
            baiduOCR.parseImage(bufferedImage);
            System.out.println("print the result from baidu");
            questionAndOptions = baiduOCR.getQuestionAndOptions();
            optionCentralCoordinates = baiduOCR.getOptionCentralCoordinates();
            for (int i = 0; i < 4; i ++){
                System.out.println("baidu:" + questionAndOptions[i]);
            }
        } else {
            AliOCR aliOCR = new AliOCR();
            questionAndOptions = aliOCR.parseReslut(aliOCR.callAliOcrAPI(aliOCR.getBase64Code(bufferedImage)));
        }

        question = questionAndOptions[0];
        System.out.println("questionAndOptions size:"  + questionAndOptions.length);

        for(int i = 0; i < questionAndOptions.length -1 ; i ++){
            this.answers[i] = this.questionAndOptions[i +1];
        }

        SearchQuestion searchQuestion = new SearchQuestion(questionAndOptions, answers);
        try {
            searchQuestion.search(question);
        } catch (IOException e){
            e.printStackTrace();
        }
        int finalResult = searchQuestion.finalResultIndex();
        endTime = System.currentTimeMillis();


        System.out.println("finalResult :" + finalResult + "---" + answers[finalResult]);

        System.out.println("totalTime :" + (endTime - startTime));

        HandIn handIn = new HandIn(optionCentralCoordinates);

        while(System.currentTimeMillis() - startTime < 3500){
            try{
                Thread.sleep(500);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
//        auto hand in result with keyboard

//handin twice to make sure handin success!
        for(int i = 0; i < 2 ; i ++){
//            click the app zone , to focus on this APP
            handIn.mouseClick(3);
            handIn.keyboardClick(finalResult);
            System.out.println("finalTime :" + (System.currentTimeMillis() - startTime));

        }

    }
}
