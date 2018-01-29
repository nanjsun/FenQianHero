package com.fenqian.ocr.baidu;

import java.util.List;

/**
 * FenQianHero class
 *
 * @author nanj
 * @date 2018/1/28
 */

public class Words_result {
    private List<Vertexes_location> vertexes_location ;

    private Probability probability;

    private List<Min_finegrained_vertexes_location> min_finegrained_vertexes_location ;

    private String words;

    private Location location;

    private List<Finegrained_vertexes_location> finegrained_vertexes_location ;

    public void setVertexes_location(List<Vertexes_location> vertexes_location){
        this.vertexes_location = vertexes_location;
    }
    public List<Vertexes_location> getVertexes_location(){
        return this.vertexes_location;
    }
    public void setProbability(Probability probability){
        this.probability = probability;
    }
    public Probability getProbability(){
        return this.probability;
    }
    public void setMin_finegrained_vertexes_location(List<Min_finegrained_vertexes_location> min_finegrained_vertexes_location){
        this.min_finegrained_vertexes_location = min_finegrained_vertexes_location;
    }
    public List<Min_finegrained_vertexes_location> getMin_finegrained_vertexes_location(){
        return this.min_finegrained_vertexes_location;
    }
    public void setWords(String words){
        this.words = words;
    }
    public String getWords(){
        return this.words;
    }
    public void setLocation(Location location){
        this.location = location;
    }
    public Location getLocation(){
        return this.location;
    }
    public void setFinegrained_vertexes_location(List<Finegrained_vertexes_location> finegrained_vertexes_location){
        this.finegrained_vertexes_location = finegrained_vertexes_location;
    }
    public List<Finegrained_vertexes_location> getFinegrained_vertexes_location(){
        return this.finegrained_vertexes_location;
    }

}