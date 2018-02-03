package com.fenqian.answer.sougou;

import java.util.List;

/**
 * FenQianHero class
 *
 * @author nanj
 * @date 2018/2/4
 */

public class JsonAnswer {
    private String[] answers = new String[3] ;

    private String cd_id;

    private String channel;

    private String choices;

    private String debug;

    private int error;

    private boolean hasAnswer;

    private String recommend;

    private String result;

    private List<SearchInfos> search_infos ;

    private String title;

    private String uid;

    public void setAnswers(String[] answers){
        this.answers[0] = answers[0];
        this.answers[1] = answers[1];
        this.answers[2] = answers[2];
    }
    public String[] getAnswers(){
        return this.answers;
    }
    public void setCd_id(String cd_id){
        this.cd_id = cd_id;
    }
    public String getCd_id(){
        return this.cd_id;
    }
    public void setChannel(String channel){
        this.channel = channel;
    }
    public String getChannel(){
        return this.channel;
    }
    public void setChoices(String choices){
        this.choices = choices;
    }
    public String getChoices(){
        return this.choices;
    }
    public void setDebug(String debug){
        this.debug = debug;
    }
    public String getDebug(){
        return this.debug;
    }
    public void setError(int error){
        this.error = error;
    }
    public int getError(){
        return this.error;
    }
    public void setHasAnswer(boolean hasAnswer){
        this.hasAnswer = hasAnswer;
    }
    public boolean getHasAnswer(){
        return this.hasAnswer;
    }
    public void setRecommend(String recommend){
        this.recommend = recommend;
    }
    public String getRecommend(){
        return this.recommend;
    }
    public void setResult(String result){
        this.result = result;
    }
    public String getResult(){
        return this.result;
    }
    public void setSearch_infos(List<SearchInfos> search_infos){
        this.search_infos = search_infos;
    }
    public List<SearchInfos> getSearch_infos(){
        return this.search_infos;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setUid(String uid){
        this.uid = uid;
    }
    public String getUid(){
        return this.uid;
    }

}