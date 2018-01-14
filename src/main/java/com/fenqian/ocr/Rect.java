package com.fenqian.ocr;

/**
 * FenQianHero class
 *
 * @author nanj
 * @date 2018/1/13
 */
public class Rect {
    private int angle;

    private int height;

    private double left;

    private int top;

    private int width;

    public void setAngle(int angle){
        this.angle = angle;
    }
    public int getAngle(){
        return this.angle;
    }
    public void setHeight(int height){
        this.height = height;
    }
    public int getHeight(){
        return this.height;
    }
    public void setLeft(double left){
        this.left = left;
    }
    public double getLeft(){
        return this.left;
    }
    public void setTop(int top){
        this.top = top;
    }
    public int getTop(){
        return this.top;
    }
    public void setWidth(int width){
        this.width = width;
    }
    public int getWidth(){
        return this.width;
    }

}
