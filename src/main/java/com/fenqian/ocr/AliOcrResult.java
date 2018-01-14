package com.fenqian.ocr;

import java.util.List;

/**
 * FenQianHero class
 *
 * @author nanj
 * @date 2018/1/13
 */

public class AliOcrResult {
    private String request_id;

    private List<Ret> ret ;

    private boolean success;

    public void setRequest_id(String request_id){
        this.request_id = request_id;
    }
    public String getRequest_id(){
        return this.request_id;
    }
    public void setRet(List<Ret> ret){
        this.ret = ret;
    }
    public List<Ret> getRet(){
        return this.ret;
    }
    public void setSuccess(boolean success){
        this.success = success;
    }
    public boolean getSuccess(){
        return this.success;
    }
}

