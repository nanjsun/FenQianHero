package com.fenqian.click;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * FenQianHero class
 *
 * @author nanj
 * @date 2018/1/14
 */
public class HandIn {
    private Robot robot = null;
//    private int[] optionA = {1160,400};
//    private int[] optionB = {1160,500};
//    private int[] optionC = {1160,600};
    private int[] optionA = {960,340};
    private int[] optionB = {960,420};
//    private int[] optionC = {960,500};
    private int[] optionC = {1960,1080};
    int x;
    int y;

    public HandIn(int option){
        try{
            robot = new Robot();
        } catch (AWTException e){
            e.printStackTrace();
        }
        switch (option){
            case 0:
                this.x = optionA[0];
                this.y = optionA[1];
                break;
            case 1:
                this.x = optionB[0];
                this.y = optionB[1];
                break;
            case 2:
                this.x = optionC[0];
                this.y = optionC[1];
                break;
            default:
                this.x = optionA[0];
                this.y = optionA[1];
        }

    }

    public void mouseClick(){
        robot.mouseMove(x,y);
//        click this position 3 times
        for(int i = 0; i < 4; i ++){
            robot.mousePress(KeyEvent.BUTTON1_MASK);
            robot.delay(100);
            robot.mouseRelease(KeyEvent.BUTTON1_MASK);
        }
    }

    public static void main(String[] args){
        HandIn handIn = new HandIn(1);
        handIn.mouseClick();
    }
}
