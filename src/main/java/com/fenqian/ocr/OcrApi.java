package com.fenqian.ocr;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * FenQianHero class
 *
 * @author nanj
 * @date 2018/1/29
 */
public interface OcrApi {
    /**
     * this method parse image to a json object
     * @param bufferedImage
     * @throws IOException
     */
    public void parseImage(BufferedImage bufferedImage) throws IOException;


    /**
     * this method parse image to a json object
     *
     * @param filePath local image path
     */
    public void parseImage(String filePath);


    /**
     * get the question and options in the order of : question, option1, option2, option3
     * @return
     */
    public String[] getQuestionAndOptions();

}
