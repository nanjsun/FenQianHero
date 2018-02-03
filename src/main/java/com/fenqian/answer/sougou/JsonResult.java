package com.fenqian.answer.sougou;

/**
 * FenQianHero class
 *
 * @author nanj
 * @date 2018/2/4
 */
public class JsonResult {

    private String[] result;

    private String code;

    public String[] getResult ()
    {
        return result;
    }

    public void setResult (String[] result)
    {
        this.result = result;
    }

    public String getCode ()
    {
        return code;
    }

    public void setCode (String code)
    {
        this.code = code;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [result = "+result+", code = "+code+"]";
    }
}
