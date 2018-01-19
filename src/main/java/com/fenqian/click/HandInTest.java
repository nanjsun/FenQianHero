package com.fenqian.click;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.PushbackInputStream;

/**
 * FenQianHero class
 *
 * @author nanj
 * @date 2018/1/19
 */
public class HandInTest {

    Robot clickRobot = null;

    public HandInTest(){
        try{
            clickRobot = new Robot();
        } catch (AWTException e){
            e.printStackTrace();
        }

    }
    public void click(int x, int y){
        clickRobot.mouseMove(x, y);
        clickRobot.mousePress(KeyEvent.BUTTON1_MASK);
        clickRobot.mouseRelease(KeyEvent.BUTTON1_MASK);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("click:" + x + ":" + y);
    }

    public static void main(String[] args){
        HandInTest handInTest = new HandInTest();
        handInTest.click(960,300);
        handInTest.click(960,400);
        handInTest.click(960,500);
        handInTest.click(960,600);
        handInTest.click(960,700);
        handInTest.click(960,800);
        handInTest.click(960,900);
    }
}