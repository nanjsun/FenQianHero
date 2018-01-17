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

    private int[] optionA = {960,340};
    private int[] optionB = {960,420};
    private int[] optionC = {960,500};
    private int[] optionD = {960,580};
    private int CLICK_TIME = 1;

    public HandIn(){
    }
    public HandIn(int[][] coordinates){
        optionA = coordinates[10];
        optionB = coordinates[11];
        optionC = coordinates[12];
    }

    public void mouseClick(int option){
        int x;
        int y;
        try{
            robot = new Robot();
        } catch (AWTException e){
            e.printStackTrace();
        }
        switch (option){
            case 0:
                x = optionA[0];
                y = optionA[1];
                break;
            case 1:
                x = optionB[0];
                y = optionB[1];
                break;
            case 2:
                x = optionC[0];
                y = optionC[1];
                break;
            default:
                x = optionD[0];
                y = optionD[1];
        }
        robot.mouseMove(x, y);
//        click this position 2 times
        for(int i = 0; i < CLICK_TIME; i ++){
            robot.mousePress(KeyEvent.BUTTON1_MASK);
            robot.delay(0);
            robot.mouseRelease(KeyEvent.BUTTON1_MASK);
            System.out.println("click:" + x + "-" + y);
        }
    }

    public static void main(String[] args){
        HandIn handIn = new HandIn();
        handIn.mouseClick(0);
        handIn.mouseClick(1);
        handIn.mouseClick(2);
        handIn.mouseClick(3);
//        handIn.mouseClick(4);
    }
}
