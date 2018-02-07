package com.fenqian.thread;

import com.fenqian.LetUsGetMoney;

/**
 * FenQianHero class
 *
 * @author nanj
 * @date 2018/2/6
 */
public class AnswerQuestionThread extends Thread {
    public AnswerQuestionThread(){
        super.setName("AnswerQuestionThread");
    }
    @Override
    public void run(){
        LetUsGetMoney letUsGetMoney = new LetUsGetMoney();
        try {
            letUsGetMoney.startWithOnlineOCR();
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
