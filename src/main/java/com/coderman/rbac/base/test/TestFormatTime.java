package com.coderman.rbac.base.test;

import com.coderman.rbac.base.utils.DateStyleUtil;
import org.junit.Test;

import java.util.Date;

/**
 * Created by zhangyukang on 2019/11/24 12:08
 */
public class TestFormatTime {


    @Test
    public void testFormate(){
        String s = DateStyleUtil.DateStyle(new Date());
        System.out.println(s);
    }
}
