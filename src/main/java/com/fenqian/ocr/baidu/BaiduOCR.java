package com.fenqian.ocr.baidu;

import com.alibaba.fastjson.JSON;
import com.baidu.aip.ocr.AipOcr;
import com.fenqian.ocr.OcrApi;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * FenQianHero class
 *
 * @author nanj
 * @date 2018/1/19
 */
public class BaiduOCR implements OcrApi{
    private static final String APP_ID = "10693302";
    private static final String API_KEY = "xWGRL8E8VTLciHnwKwr5SLdA";
    private static final String SECRET_KEY = "r4RGGtDTf9rS1H7upYx4vHqiGgMEwGWw";
    private final int OPTION_NUMBER = 3;

    private AipOcr client;
    private JSONObject result;

    private long startTime;

    private int[] questionAndOptionsCoordinates = new int[16];

    private String[] questionAndOption = new String[4];
    private HashMap<String, String> options = new HashMap<String, String>();


    public BaiduOCR(){
        options.put("recognize_granularity", "big");
        options.put("language_type", "CHN_ENG");
        options.put("detect_direction", "true");
        options.put("detect_language", "true");
        options.put("vertexes_location", "true");
        options.put("probability", "true");

        client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

    }

    @Override
    public void parseImage(BufferedImage bufferedImage) throws IOException {
        startTime = System.currentTimeMillis();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        result = client.basicGeneral(imageBytes, options);
        System.out.println(result.toString());
    }

    @Override
    public void parseImage(String file){
        startTime = System.currentTimeMillis();
        result = client.basicGeneral(file, options);
        System.out.println(result.toString());
    }


    @Override
    public String[] getQuestionAndOptions(){
        BaiduOcrResult baiduOcrResult = JSON.parseObject(result.toString(), BaiduOcrResult.class);
        int ocrCount = baiduOcrResult.getWords_result_num();
        for(int i = 0; i < OPTION_NUMBER; i++){
            questionAndOption[OPTION_NUMBER - i] = baiduOcrResult.getWords_result().get(ocrCount - i - 1).getWords();

            questionAndOptionsCoordinates[(OPTION_NUMBER -i) * 4 + 0] = baiduOcrResult.getWords_result()
                    .get(ocrCount - i - 1).getMin_finegrained_vertexes_location().get(0).getX();
            questionAndOptionsCoordinates[(OPTION_NUMBER -i) * 4 + 1] = baiduOcrResult.getWords_result()
                    .get(ocrCount - i - 1).getMin_finegrained_vertexes_location().get(0).getY();
            questionAndOptionsCoordinates[(OPTION_NUMBER -i) * 4 + 2] = baiduOcrResult.getWords_result()
                    .get(ocrCount - i - 1).getMin_finegrained_vertexes_location().get(2).getX();
            questionAndOptionsCoordinates[(OPTION_NUMBER -i) * 4 + 3] = baiduOcrResult.getWords_result()
                    .get(ocrCount - i - 1).getMin_finegrained_vertexes_location().get(2).getY();
        }
        StringBuffer question = new StringBuffer();
        for(int i = 0; i < ocrCount - OPTION_NUMBER; i ++){
            question.append(baiduOcrResult.getWords_result().get(i).getWords());
            questionAndOption[0] = question.toString();

            questionAndOptionsCoordinates[0] = baiduOcrResult.getWords_result().get(0)
                    .getMin_finegrained_vertexes_location().get(0).getX();
            questionAndOptionsCoordinates[1] = baiduOcrResult.getWords_result().get(0)
                    .getMin_finegrained_vertexes_location().get(0).getY();
            questionAndOptionsCoordinates[2] = baiduOcrResult.getWords_result().get(0)
                    .getMin_finegrained_vertexes_location().get(2).getX();
            questionAndOptionsCoordinates[3] = baiduOcrResult.getWords_result().get(0)
                    .getMin_finegrained_vertexes_location().get(2).getY();

//            questionAndOption[0] = questionAndOption[0].
        }
        System.out.println("resolve time :" + (System.currentTimeMillis() - startTime));


        return questionAndOption;

    }

    public static void main(String[] args) throws IOException{
        BaiduOCR baiduOCR = new BaiduOCR();

        String file = "./photos/9.png";

        BufferedImage bufferedImage = ImageIO.read(new File(file));

        baiduOCR.parseImage(bufferedImage);
        String[] result = baiduOCR.getQuestionAndOptions();

        for(int i = 0; i < 4; i++ ){
            System.out.println(result[i]);

            System.out.println("leftTop :" + baiduOCR.questionAndOptionsCoordinates[i * 4 + 0] + "-" +
                    baiduOCR.questionAndOptionsCoordinates[i * 4 + 1]);
            System.out.println("rightBottom :" + baiduOCR.questionAndOptionsCoordinates[i * 4  + 2] + "-" +
                    baiduOCR.questionAndOptionsCoordinates[i * 4  + 3]);
        }



    }
}
