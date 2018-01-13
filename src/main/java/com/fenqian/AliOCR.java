package com.fenqian;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import sun.misc.BASE64Encoder;

public class AliOCR {
    private BufferedImage bufferedImage;

    public String getWords(BufferedImage bufferedImage){
        this.bufferedImage = bufferedImage;


        return "ss";
    }
    public static void main(String[] args) {
        String host = "http://tysbgpu.market.alicloudapi.com";
        String path = "/api/predict/ocr_general";
        String method = "POST";
        String appcode = "17f0a3c27cb74e3f95a6f5ae731761fc";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/json; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();

//        String base64Code = "fakecode";
        String base64Code = getBase64Code();
        String bodys = "{\"image\":" +"\"" + base64Code + "\"" + ",\"configure\"" +":{\"min_size\":16," + "\"output_prob\":true}}";
//        String bodys = "{\"image\":\"base64Code\",\"configure\":\"{\\\"min_size\\\":16,#图片中文字的最小高度\\\"output_prob\\\":true#是否输出文字框的概率}\"}";
        System.out.println(bodys);

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
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
//            //获取response的body
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String getBase64Code(){
        String imageFile = "./photos/4.png";
        InputStream in = null;
        byte[] data = null;
        try{
            in = new FileInputStream(imageFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        String encodeString = encoder.encode(data);
//        System.out.println(encodeString);

        return encodeString;

    }
}
