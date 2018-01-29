package com.fenqian.ocr.baidu;

/**
 * FenQianHero class
 *
 * @author nanj
 * @date 2018/1/28
 */
public class Probability {
    private double average;

    private double min;

    private double variance;

    public void setAverage(double average){
        this.average = average;
    }
    public double getAverage(){
        return this.average;
    }
    public void setMin(double min){
        this.min = min;
    }
    public double getMin(){
        return this.min;
    }
    public void setVariance(double variance){
        this.variance = variance;
    }
    public double getVariance(){
        return this.variance;
    }

}