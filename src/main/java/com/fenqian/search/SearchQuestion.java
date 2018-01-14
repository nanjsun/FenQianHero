package com.fenqian.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;

/**
 * FenQianHero class
 *
 * @author nanj
 * @date 2018/1/14
 */
public class SearchQuestion {
    private String[] questionAndAnswers;
    private String[] answers;
    private int[] answerCount;
    private HashMap<String, Integer> answerMap = new HashMap<String, Integer>();

    public SearchQuestion(String[] questionAndAnswers, String[] answer){
        this.questionAndAnswers = questionAndAnswers;
        this.answers = answer;

    }

    public int[] search(String question) throws IOException {
//        this &rn = 20 mean that will present 20 results in baidu
        String path = "http://www.baidu.com/s?tn=ichuner&lm=-1&word=" +
                URLEncoder.encode(question, "gb2312") + "&rn=20";

        String line = null;
        int maxLines = 10000;
        int countLines = 0;
        int[] answerCount = new int[3];
        URL url = new URL(path);
        BufferedReader breaded = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + path);
        while ((line = breaded.readLine()) != null && countLines < maxLines) {
            countLines ++;
            if (line.contains(questionAndAnswers[1])) {
                answerCount[0]++;
            }
            if (line.contains(questionAndAnswers[2])) {
                answerCount[1]++;
            }
            if (line.contains(questionAndAnswers[3])) {
                answerCount[2]++;
            }

        }
        answerMap.put(questionAndAnswers[1], answerCount[0]);
        answerMap.put(questionAndAnswers[2], answerCount[1]);
        answerMap.put(questionAndAnswers[3], answerCount[2]);

        for(int i = 0; i < 3; i ++ ){
            System.out.println(answers[i] + ":" + answerCount[i]);
        }
        this.answerCount = answerCount;
        return answerCount;
    }
    public String[] rankResult(){
        String[] rankedResult;
        for (int i = 0; i < answerCount.length - 1; i ++){
            for(int j = i; j < answerCount.length - 1; j ++){
                if (answerCount[j] < answerCount[j + 1]){
                    int temp = answerCount[j];
                    answerCount[j] = answerCount[j + 1];
                    answerCount[j + 1] = temp;

                    String tempAnswer = answers[j];
                    answers[j] = answers[j + 1];
                    answers[j + 1] = tempAnswer;

                }


            }
        }
        return answers;


    }
    public int finalResultIndex(){
        System.out.println("real:");
        int maxCountIndex = 0;
        int maxCount = 0;
        for(int i = 0; i < 3; i ++) {
            if(answerCount[i] > maxCount){
                maxCount = answerCount[i];
                maxCountIndex = i;

            }
//            System.out.println(maxCount + ":" + maxCountIndex);


        }
        return maxCountIndex;
    }

    public static void main(String[] arges){
        String[] questionAndAnswers = {"静夜思", "李白","是", "床前明月光"};
        String[] answers = { "李白","是", "床前明月光"};
        SearchQuestion searchQuestion = new SearchQuestion(questionAndAnswers, answers);
        try {
            searchQuestion.search(questionAndAnswers[0]);

        }catch (IOException e){
            e.printStackTrace();
        }
//        searchQuestion.rankResult();
        ;
        System.out.println(searchQuestion.answerMap.toString());
        Object[] key = searchQuestion.answerMap.values().toArray();
        Arrays.sort(key);
        System.out.println("maxValue:" + searchQuestion.answerMap.get(key[2]));
        System.out.println("maxValue:" + searchQuestion.answerMap.keySet().toString());
//        System.out.println(searchQuestion.answerMap.);


        for(String answer : answers){
            System.out.println(answer);
        }

    }



}
