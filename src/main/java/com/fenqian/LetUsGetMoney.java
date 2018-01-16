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

    public void start() {


        boolean imageValid = false;
        ScreenShotImage screenShotImage = new ScreenShotImage();
        BufferedImage bufferedImage = screenShotImage.getBufferedImage(x, y, width, height);
        if (screenShotImage.isValidImage()){
            imageValid = true;
        }

        while (!imageValid){
            bufferedImage = screenShotImage.getBufferedImage(x, y, width, height);
            if(screenShotImage.isValidImage()){
                imageValid = true;
            }
        }

        Long beginOfImageDectect = System.currentTimeMillis();
        AliOCR aliOCR = new AliOCR();

        question = aliOCR.parseReslut(aliOCR.callAliOcrAPI(aliOCR.getBase64Code(bufferedImage)))[0];

        String originalWords = new TessOCR().getOCRByBufferedImage(bufferedImage);

        System.out.println("----->Original word:");
        System.out.println(originalWords);
        System.out.println("----->Image resolve time:");
        System.out.println(System.currentTimeMillis() - beginOfImageDectect);

        //detect if this image is valid
        if (!originalWords.contains(QUESTION_FLAG)) {
            System.out.println("There is no '?' in image");
        }
        if (originalWords.contains(lastQuestionKeyWord)) {
            System.out.println("same question");
        }

        if (originalWords.contains(QUESTION_FLAG) && !originalWords.contains(lastQuestionKeyWord)) {

            Information information = new Information(originalWords);
            lastQuestionKeyWord = information.getQuestionKeyWord();
            this.question = information.getQuestion();
            this.answers = information.getAns();

            System.out.println("Question is:");
            System.out.println(question);
            System.out.println("Answer is :");
            int numberOfAns = answers.length;
            for(int i = 0; i < numberOfAns; i++){
                System.out.println((i + 1) + ":" + answers[i]);
            }

            beginSearch();
        }
    }


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
        HandIn handIn = new HandIn(finalResult);
        while(System.currentTimeMillis() - startTime < 7500){
            try{
                Thread.sleep(500);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
//        handIn.mouseClick();
        System.out.println("finalTime :" + (System.currentTimeMillis() - startTime));

    }

    private void beginSearch(){
        long countQuestion = 1;
        int maxIndex = 0;

        Search serachQuestion = new Search(question);

        ArrayBlockingQueue<Runnable> workQuenue = new ArrayBlockingQueue<Runnable>(5);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5,10,
                200, TimeUnit.MINUTES,workQuenue, Executors.defaultThreadFactory(),new RejectedExecutionHandlerForSearch());

        FutureTask<Long> futureQuestion = new FutureTask<Long>(new SearchAndOpen(question));
        new Thread(futureQuestion).start();
        for (int i = 0; i < NUM_OF_ANSWERS; i++) {
//            searchQuestionAndAnswer[i] = new Search(question + " " + answers[i]);
//            searchAnswers[i] = new Search(answers[i]);

//            futureQA[i] = new FutureTask<Long>(searchQuestionAndAnswer[i]);
//            futureAnswers[i] = new FutureTask<Long>(searchAnswers[i]);
//            new Thread(futureQA[i]).start();
//            new Thread(futureAnswers[i]).start();
        }
        try {
            while (!futureQuestion.isDone()) {}
            countQuestion = futureQuestion.get();
            for (int i = 0; i < NUM_OF_ANSWERS; i++) {
//                while (!futureQA[i].isDone()) {}
//                countQuestionAndAnswer[i] = futureQA[i].get();
//                while (!futureAnswers[i].isDone()) {}
//                countAnswer[i] = futureAnswers[i].get();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        float[] ans = new float[NUM_OF_ANSWERS];
        for (int i = 0; i < NUM_OF_ANSWERS; i++) {
//            ans[i] = (float)countQuestionAndAnswer[i] / (float)(countQuestion * countAnswer[i]);
            maxIndex = (ans[i]>ans[maxIndex] ) ? i : maxIndex;
        }
        //根据pmi值进行打印搜索结果
        int[] rank=rank(ans);
        for (int i : rank) {
            System.out.print(answers[i]);
//            System.out.print(" countQuestionAndAnswer:"+countQuestionAndAnswer[i]);
//            System.out.print(" countAnswer:"+countAnswer[i]);
            System.out.println(" ans:"+ans[i]);
        }

        System.out.println("--------最终结果-------");
        System.out.println(answers[maxIndex]);
        endTime = System.currentTimeMillis();
        float excTime = (float) (endTime - startTime) / 1000;

        System.out.println("执行时间：" + excTime + "s");


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


/*

    private static void begainSearch(){


    }
}
*/