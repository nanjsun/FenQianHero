package com.fenqian.answer.sougou;

import com.alibaba.fastjson.JSON;
import com.fenqian.answer.OnlineAnswer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * FenQianHero class
 *
 * @author nanj
 * @date 2018/2/3
 */
public class SougouAssistant implements OnlineAnswer {
    private String[] questionAndOptions = new String[4];
    private int answerIndex;
    private String jsonResponse;

    @Override
    public String[] getQuestionAndOptions(){
        return questionAndOptions;
    }

    @Override
    public int getAnswerIndex(){
        return answerIndex;
    }

    public void requestServer() throws Exception{
        String url = "http://140.143.49.31/api/ans2?wdcallback=xx&key=bwyx";
        String host = "140.143.49.31";
        String connections = "keep-alive";
        String accept = "*/*";
        String userAgent = "Mozilla/5.0 (iPhone; CPU iPhone OS 9_3_5 like Mac OS X) AppleWebKit/601.1.46 " +
                "(KHTML, like Gecko) Mobile/13G36 Sogousearch/Ios/5.9.7";
        String acceptLanguage = "zh-cn";
        String referer = "http://nb.sa.sogou.com/";
        String acceptEncoding = "gzip, deflate";

        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("Host", host);
        connection.setRequestProperty("Connection", connections);
        connection.setRequestProperty("Accept", accept);
        connection.setRequestProperty("User-Agent", userAgent);
        connection.setRequestProperty("Accept-Language", acceptLanguage);
        connection.setRequestProperty("Referer", referer);
        connection.setRequestProperty("Accept_Encoding", acceptEncoding);

        int responseCode = connection.getResponseCode();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream())
        );
        String rawResponse = in.readLine();
        int rawResponseLength = rawResponse.length();

        jsonResponse = rawResponse.substring(3, rawResponseLength - 1);
//        System.out.println(rawResponse);
        System.out.println(jsonResponse);
    }

    public void parseJson(){

        JsonResult jsonResult = JSON.parseObject(jsonResponse, JsonResult.class);
        String currentResult = jsonResult.getResult()[1];

        JsonAnswer jsonAnswer = JSON.parseObject(currentResult, JsonAnswer.class);

        System.out.println(jsonAnswer.getResult());

//        questionAndOptions[0] = jsonAnswer.getTitle();
//        questionAndOptions[1] = jsonAnswer.setAnswers();
//        questionAndOptions[2] = jsonAnswer.getTitle();
//        questionAndOptions[3] = jsonAnswer.getTitle();

        questionAndOptions[0] = jsonAnswer.getTitle();
        String result = jsonAnswer.getResult();
        for(int i = 0; i < 3; ++i){
            questionAndOptions[i + 1] = jsonAnswer.getAnswers()[i];
            if(result.equals(questionAndOptions[i + 1])){
                answerIndex = i;
            }
        }


        for(int i = 0; i < 4; i ++){
            System.out.println(questionAndOptions[i]);
        }
//        System.out.println(jsonResult.getResult()[0]);
//        System.out.println(jsonResult.getResult()[1]);
    }

    public static void main(String[] args) throws Exception{
        SougouAssistant sougouAssistant = new SougouAssistant();
        sougouAssistant.requestServer();
        sougouAssistant.parseJson();
    }
}