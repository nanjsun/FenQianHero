package com.fenqian.ocr;

import com.baidu.aip.ocr.AipOcr;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * FenQianHero class
 *
 * @author nanj
 * @date 2018/1/19
 */
public class BaiduOCR {
    public static final String APP_ID = "10693302";
    public static final String API_KEY = "xWGRL8E8VTLciHnwKwr5SLdA";
    public static final String SECRET_KEY = "r4RGGtDTf9rS1H7upYx4vHqiGgMEwGWw";

    public static void main(String[] args){
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("recognize_granularity", "big");
        options.put("language_type", "CHN_ENG");
        options.put("detect_direction", "true");
        options.put("detect_language", "true");
        options.put("vertexes_location", "true");
        options.put("probability", "true");


        long startTime = System.currentTimeMillis();
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
        String path = "./photos/4.png";
        JSONObject res = client.basicGeneral(path, options);
        System.out.println("resolve time :" + (System.currentTimeMillis() - startTime));
        System.out.println(res.toString());



    }

}
