package com.fenqian;

import com.fenqian.answer.sougou.SougouAssistant;
import com.fenqian.click.HandIn;
import com.fenqian.image.ScreenShotImage;
import com.fenqian.image.ValidRegion;
import com.fenqian.ocr.ali.AliOCR;
import com.fenqian.ocr.baidu.BaiduOCR;
import com.fenqian.search.SearchQuestion;

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

    private int[] relativeOptionCentralCoordinates = new int[6];
    private int[] absoluteOptionCentralCoordinates = new int[6];

    private String lastQuestionKeyWord = "Nothing";
    private String ocrSupplier = "baidu";

    private BufferedImage globalImage;
    private BufferedImage validImage;

//    sequence of: 0->xigua(dafault), 1->zhishichaoren, 2->bobo
    private final int[] validRegionTopOffset = {0,60,25,40};
    private final int[] validRegionBottonOffset = {0,70,50,40};
    private int appIndex;


    public void init(int appIndex) {
        this.appIndex = appIndex;
        ScreenShotImage screenShotImage = new ScreenShotImage();
        globalImage = screenShotImage.getBufferedImage(globalRegionLeft,globalRegionTop,
                globalRegionWidth,globalRegionHeight);

        try {
            File globaleImageFile = new File(".","./photos/globalImage.jpg");
            ImageIO.write(globalImage,"jpg",globaleImageFile);
        } catch (IOException e){
            e.printStackTrace();
        }
        ValidRegion validRegion = new ValidRegion(globalImage);
        int[] validRegionCoordinate = validRegion.getValidRegion();

        validRegionLeft = validRegionCoordinate[0] + globalRegionLeft;
        validRegionTop = validRegionCoordinate[1] + globalRegionTop + validRegionTopOffset[this.appIndex];

        validRegionWidth = validRegionCoordinate[2] - validRegionCoordinate[0];
        validRegionHeight = validRegionCoordinate[3] - validRegionCoordinate[1] - validRegionBottonOffset[this.appIndex];
        validImage = screenShotImage.getBufferedImage(validRegionLeft, validRegionTop,
                validRegionWidth, validRegionHeight);

    }

    public void startWithOnlineOCR() throws IOException{

        boolean imageValid = false;
        boolean answerBoardValid = false;
        int recaptureCount = 0;
        ScreenShotImage screenShotImage = new ScreenShotImage();
        BufferedImage bufferedImage = screenShotImage.getBufferedImage(validRegionLeft, validRegionTop,
                validRegionWidth, validRegionHeight);
//        validImage = bufferedImage;

        try {
            File validRegionImageFile = new File(".", "./photos/validImage.jpg");
            ImageIO.write(bufferedImage,"jpg",validRegionImageFile);
        } catch (IOException e){
            e.printStackTrace();
        }

        if(screenShotImage.isValidImage() && screenShotImage.isAnswerStatus()){
            imageValid = true;
        } else {
            while (!imageValid){
                recaptureCount ++;
                System.out.println("invalid image, recapturing imags, count:" + recaptureCount);
                bufferedImage = screenShotImage.getBufferedImage(validRegionLeft, validRegionTop,
                        validRegionWidth, validRegionHeight);
                if(screenShotImage.isValidImage() && screenShotImage.isAnswerStatus()){
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
            relativeOptionCentralCoordinates = baiduOCR.getOptionCentralCoordinates();
            for(int i = 0; i < 3; i++){
                absoluteOptionCentralCoordinates[i * 2] = relativeOptionCentralCoordinates[i * 2]
                        + validRegionLeft;
//                System.out.println("globalX:" + globalRegionLeft + "validX" + validRegionLeft + "ax" + relativeOptionCentralCoordinates[i * 2]);
                absoluteOptionCentralCoordinates[i * 2 + 1] = relativeOptionCentralCoordinates[i * 2 + 1]
                        + validRegionTop;
//                System.out.println("globalY:" + globalRegionTop + "validY" + validRegionTop + "ay" + relativeOptionCentralCoordinates[i * 2 + 1]);

            }
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

        SougouAssistant sougouAssistant = new SougouAssistant(appIndex);
        String[] questionAndOptionsFromSougou = sougouAssistant.getQuestionAndOptions();
        int answerIndexFromSougou = sougouAssistant.getAnswerIndex();

        System.out.println("finalResult :" + finalResult + "---" + answers[finalResult]);
        System.out.println("finalResultFromSougou :" + answerIndexFromSougou + "---"
                + questionAndOptionsFromSougou[answerIndexFromSougou + 1]);

        System.out.println("totalTime :" + (endTime - startTime));

        HandIn handIn = new HandIn(absoluteOptionCentralCoordinates);
        final int DELAY_TIME = 8000;

        while(System.currentTimeMillis() - startTime < DELAY_TIME){
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
            handIn.mouseClick(answerIndexFromSougou);
            handIn.mouseClick(finalResult);
            handIn.keyboardClick(finalResult);
            System.out.println("finalTime :" + (System.currentTimeMillis() - startTime));

        }

    }

    public BufferedImage getGlobalImage(){
        return globalImage;
    }

    public BufferedImage getValidImage(){
        return validImage;
    }
}
