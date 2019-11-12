package com.coderman.rent.sys.test;

import org.junit.Test;

/**
 * Created by zhangyukang on 2019/11/12 21:10
 */
public class TestInteger {
    @Test
    public void test1(){
        Integer integer = new Integer(100);
        Integer integer2 = new Integer(100);
        System.out.println(integer==integer2);

        System.out.println(integer.equals(integer2));
        System.out.println(integer==100.0f);
    }
}
