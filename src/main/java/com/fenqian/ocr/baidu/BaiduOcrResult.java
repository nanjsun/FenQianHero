package com.fenqian.ocr.baidu;

import java.util.List;

/**
 * FenQianHero class
 *
 * @author nanj
 * @date 2018/1/28
 */
public class BaiduOcrResult {
    private String log_id;

    private List<Words_result> words_result;

    private int words_result_num;

    private int language;

    private int direction;

    public void setLog_id(String log_id) {
        this.log_id = log_id;
    }

    public String getLog_id() {
        return this.log_id;
    }

    public void setWords_result(List<Words_result> words_result) {
        this.words_result = words_result;
    }

    public List<Words_result> getWords_result() {
        return this.words_result;
    }

    public void setWords_result_num(int words_result_num) {
        this.words_result_num = words_result_num;
    }

    public int getWords_result_num() {
        return this.words_result_num;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public int getLanguage() {
        return this.language;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return this.direction;
    }
}
