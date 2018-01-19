package com.fenqian;

import com.fenqian.click.HandIn;
import com.fenqian.image.ScreenShotImage;
import com.fenqian.ocr.AliOCR;
import com.fenqian.search.SearchQuestion;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.ServiceConfigurationError;
import java.util.concurrent.*;

/**
 * @author nanj
 */
public class LetUsGetMoney {
    private final int NUM_OF_ANSWERS = 2;
    private final String QUESTION_FLAG="?";
    private String[] questionAndAnswer = new String[4];
    private String[] answers = new String[3];
    private String question;
    private long startTime;
    private long endTime;
    private int x = 660;
    private int y = 140;
    private int width = 600;
    private int height = 360;
    private String lastQuestionKeyWord = "Nothing";

    public void startWithAliOCR(){
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

        Long beginOfImageDectect = System.currentTimeMillis();
        AliOCR aliOCR = new AliOCR();
        questionAndAnswer = aliOCR.parseReslut(aliOCR.callAliOcrAPI(aliOCR.getBase64Code(bufferedImage)));
        question = questionAndAnswer[0];
        System.out.println("questionAndAnswer size:"  + questionAndAnswer.length);

        for(int i = 0; i < questionAndAnswer.length -1 ; i ++){
            this.answers[i] = this.questionAndAnswer[i +1];
        }

        SearchQuestion searchQuestion = new SearchQuestion(questionAndAnswer, answers);
        try {
            searchQuestion.search(question);
        } catch (IOException e){
            e.printStackTrace();
        }
        int finalResult = searchQuestion.finalResultIndex();
        endTime = System.currentTimeMillis();


        System.out.println("finalResult :" + finalResult + "---" + answers[finalResult]);

        System.out.println("totalTime :" + (endTime - startTime));
        HandIn handIn = new HandIn();
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

    private int[] rank(float[] floats){
        int[] rank=new int[NUM_OF_ANSWERS];
        float[] f= Arrays.copyOf(floats,3);
        Arrays.sort(f);
        for (int i = 0; i < NUM_OF_ANSWERS; i++) {
            for (int j = 0; j < NUM_OF_ANSWERS; j++) {
                if(f[i]==floats[j]){
                    rank[i]=j;
                }
            }
        }
        return rank;
    }
}
