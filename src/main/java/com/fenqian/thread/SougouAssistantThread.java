package com.fenqian.thread;

import com.fenqian.answer.sougou.SougouAssistant;

import java.util.concurrent.Callable;

/**
 * FenQianHero class
 *
 * @author nanj
 * @date 2018/2/6
 */
public class SougouAssistantThread implements Callable {
    private int appIndex;

    public SougouAssistantThread(int appIndex){
        this.appIndex = appIndex;
    }

    @Override
    public String[] call (){
        SougouAssistant sougouAssistant = new SougouAssistant(appIndex);
        String[] questionAndOptionsFromSougou = sougouAssistant.getQuestionAndOptions();
        int answerIndexFromSougou = sougouAssistant.getAnswerIndex();
        String[] resultFromSougou = new String[5];
        for(int i = 0; i< 4; ++i){
            resultFromSougou[i] = questionAndOptionsFromSougou[i];
        }
        resultFromSougou[4] = "" + answerIndexFromSougou;
        return resultFromSougou;

    }

}
