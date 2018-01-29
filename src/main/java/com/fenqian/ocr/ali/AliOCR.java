package com.fenqian.ocr.ali;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.fenqian.ocr.OcrApi;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;

public class AliOCR implements OcrApi {
    private String appcode = "17f0a3c27cb74e3f95a6f5ae731761fc";
    private BufferedImage bufferedImage;
    private Map<String, String> headers = new HashMap<String, String>();

    private String jsonResult;

    private String[] questionAndOptions = new String[4];



    @Override
    public void parseImage(BufferedImage bufferedImage){
        this.bufferedImage = bufferedImage;
        String encodeImage = getBase64Code(this.bufferedImage);

        jsonResult = callAliOcrAPI(encodeImage);


    }

    @Override
    public void parseImage(String filePath){
        try {
            bufferedImage = ImageIO.read(new File(filePath));
        } catch (IOException e){
            e.printStackTrace();
        }
        parseImage(bufferedImage);
    }



    @Override
    public String[] getQuestionAndOptions(){
        questionAndOptions = parseReslut(jsonResult);
        return questionAndOptions;
    }

    
    public String callAliOcrAPI(String base64Code){
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/json; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();

//        this.bufferedImage = bufferedImage;
//        String base64Code = base64Code();
        String bodys = "{\"image\":" +"\"" + base64Code + "\"" + ",\"configure\"" +":{\"min_size\":16," + "\"output_prob\":true}}";
//        System.out.println(bodys);

        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            String host = "http://tysbgpu.market.alicloudapi.com";
            String path = "/api/predict/ocr_general";
            String method = "POST";
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
//            //获取response的body
            jsonResult = EntityUtils.toString(response.getEntity());
            System.out.println(jsonResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonResult;
    }
    public String getBase64Code(BufferedImage bufferedImage){
        ByteArrayOutputStream outputStream = null;
        try{
            outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", outputStream);
        } catch (IOException e){
            e.printStackTrace();
        }

        BASE64Encoder encoder = new BASE64Encoder();
        String encodeString = encoder.encode(outputStream.toByteArray());
//        System.out.println(encodeString);

        return encodeString;

    }
    public BufferedImage getBufferedImage(String fileName){
        File file = new File(fileName);
//        BufferedImage bufferedImage;

        try {
            bufferedImage = ImageIO.read(file);
        } catch (IOException e){
            System.out.println("read file failed");
        }
        return bufferedImage;
    }

    public String[] parseReslut(String jsonResult){
        String[] questionAndAnswer = new String[4];
        AliOcrResult aliOcrResult = JSON.parseObject(jsonResult, AliOcrResult.class);
        List<Ret> rets = aliOcrResult.getRet();
        String word = rets.get(0).getWord();
        Map<String, Integer> resultMap = new HashMap<String, Integer>();

        System.out.println(word);
        int retNumber = rets.size();
        for(int i = 0; i < retNumber; i ++){
            resultMap.put(rets.get(i).getWord(), Integer.valueOf(rets.get(i).getRect().getTop()) );
        }
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>();
        list.addAll(resultMap.entrySet());
        ValueComparator valueComparator = new ValueComparator();
        Collections.sort(list, valueComparator);

        int index = 0;
        StringBuilder stringBuilder = new StringBuilder();

        for(Iterator<Map.Entry<String,Integer>> it=list.iterator();it.hasNext();)
        {
            String wordFragments = it.next().toString();
            if(index > 2){
                stringBuilder.append(wordFragments.split("=")[0].replaceAll("^\\d.", ""));

            } else {
                questionAndAnswer[3 - index] = wordFragments.split("=")[0];
            }

            System.out.println(index + ":" + wordFragments.split("=")[0]);
            index ++;
        }
        questionAndAnswer[0] = stringBuilder.toString();

        System.out.println("Question:" + questionAndAnswer[0]);

        for(int i = 1; i < 4; i ++){
            System.out.println("Answer" + i + ":" + questionAndAnswer[i]);
        }

//        System.out.println(resultMap.size());

        return questionAndAnswer;
    }



    public static void main(String[] args) {
        AliOCR aliOCR = new AliOCR();
        BufferedImage bufferedImage = aliOCR.getBufferedImage("./photos/4.png");
        System.out.println(bufferedImage.toString());
        String base64Code = aliOCR.getBase64Code(bufferedImage);

        String jsonResult = aliOCR.callAliOcrAPI(base64Code);
        System.out.println(jsonResult);
//        String jsonResult = "{\"request_id\":\"20180113200821_48cde439759dfd04f7aeb174223bf1eb\",\"ret\":[{\"prob\":0.99955934286117554,\"rect\":{\"angle\":-90,\"height\":266,\"left\":364.5,\"top\":1,\"width\":23},\"word\":\"这首歌改编的舞蹈在抖音大\"},{\"prob\":0.99690848588943481,\"rect\":{\"angle\":-90,\"height\":133,\"left\":155,\"top\":1,\"width\":17},\"word\":\".《Panama》\"},{\"prob\":0.98947912454605103,\"rect\":{\"angle\":0,\"height\":20.999996185302734,\"left\":156,\"top\":75,\"width\":281.99993896484375},\"word\":\"这个舞蹈被大部分人称为\"},{\"prob\":0.98642551898956299,\"rect\":{\"angle\":0,\"height\":17.999996185302734,\"left\":86,\"top\":176,\"width\":60.999992370605469},\"word\":\"拍手舞\"},{\"prob\":0.98394185304641724,\"rect\":{\"angle\":-90,\"height\":88,\"left\":120.5,\"top\":215.5,\"width\":17},\"word\":\"C哩C哩舞\"},{\"prob\":0.96577662229537964,\"rect\":{\"angle\":0,\"height\":20,\"left\":86,\"top\":324,\"width\":101},\"word\":\"嘀哩嘀哩舞\"}],\"success\":true}";
        String[] result = aliOCR.parseReslut(jsonResult);

//        System.out.println(result[0]);
    }
}
