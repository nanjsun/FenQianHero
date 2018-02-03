package com.fenqian.answer;

/**
 * FenQianHero class
 *
 * @author nanj
 * @date 2018/2/3
 */
public interface OnlineAnswer {


    /**
     * @return question and options , {question, option 1, option2, option 3}
     */
    public String[] getQuestionAndOptions();


    /**
     * @return index of answer in options
     */
    public int getAnswerIndex();

}
