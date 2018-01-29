package com.fenqian.ocr.baidu;

/**
 * FenQianHero class
 *
 * @author nanj
 * @date 2018/1/28
 */
public class Location {
    private int top;

    private int left;

    private int width;

    private int height;

    public void setTop(int top){
        this.top = top;
    }
    public int getTop(){
        return this.top;
    }
    public void setLeft(int left){
        this.left = left;
    }
    public int getLeft(){
        return this.left;
    }
    public void setWidth(int width){
        this.width = width;
    }
    public int getWidth(){
        return this.width;
    }
    public void setHeight(int height){
        this.height = height;
    }
    public int getHeight(){
        return this.height;
    }

}