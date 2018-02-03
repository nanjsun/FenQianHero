package com.fenqian.answer.sougou;

/**
 * FenQianHero class
 *
 * @author nanj
 * @date 2018/2/4
 */
public class SearchInfos {
    private String summary;

    private String title;

    private String url;

    public void setSummary(String summary){
        this.summary = summary;
    }
    public String getSummary(){
        return this.summary;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setUrl(String url){
        this.url = url;
    }
    public String getUrl(){
        return this.url;
    }
}