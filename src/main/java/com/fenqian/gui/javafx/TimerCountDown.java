package com.fenqian.gui.javafx;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * FenQianHero class
 *
 * @author nanj
 * @date 2018/2/7
 */
public class TimerCountDown {

    private SimpleIntegerProperty value = new SimpleIntegerProperty(this, "value");

    public TimerCountDown(){
        value.set(5);
    }

    public int getValue(){
        return value.get();
    }

    public void setValue(int value){
        this.value.set(value);
    }
    public IntegerProperty valueProperty(){
        return value;
    }
}
