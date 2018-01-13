package com.fenqian;

import java.util.Comparator;
import java.util.Map;

/**
 * FenQianHero class
 *
 * @author nanj
 * @date 2018/1/13
 */
public class ValueComparator implements Comparator<Map.Entry<String, Integer>> {
    @Override
    public int compare(Map.Entry<String, Integer> m, Map.Entry<String, Integer> n){
        return n.getValue() - m.getValue();
    }
}
