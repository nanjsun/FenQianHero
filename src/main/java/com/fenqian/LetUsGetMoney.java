package com.fenqian;

import com.fenqian.click.HandIn;
import com.fenqian.image.ScreenShotImage;
import com.fenqian.ocr.ali.AliOCR;
import com.fenqian.ocr.baidu.BaiduOCR;
import com.fenqian.search.SearchQuestion;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

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
    private int x = 660;
    private int y = 140;
    private int width = 600;
    private int height = 360;
    private String lastQuestionKeyWord = "Nothing";
    private String ocrSupplier = "baidu";

    public void startWithOnlineOCR() throws IOException{
        boolean imageValid = false;
        int recaptureCount = 0;
        ScreenShotImage screenShotImage = new ScreenShotImage();
        BufferedImage bufferedImage = screenShotImage.getBufferedImage(x, y, width, height);
        if(screenShotImage.isValidImage()){
            imageValid = true;
        } else {
            while (!imageValid){
                recaptureCount ++;
                System.out.println("invalid image, recapturing imags, count:" + recaptureCount);
                bufferedImage = screenShotImage.getBufferedImage(x, y, width, height);
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
        HandIn handIn = new HandIn(finalResult);
        while(System.currentTimeMillis() - startTime < 7500){
            try{
                Thread.sleep(500);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        handIn.mouseClick();
        System.out.println("finalTime :" + (System.currentTimeMillis() - startTime));

    }
}
