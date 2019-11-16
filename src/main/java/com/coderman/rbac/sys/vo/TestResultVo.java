package com.coderman.rbac.sys.vo;

import org.junit.Test;

import java.util.Arrays;

/**
 * Created by zhangyukang on 2019/11/10 16:10
 */

public class TestResultVo {

    @Test
    public void testResultVo(){
        ResultVo ok = ResultVo.OK(Arrays.asList(1,2,3,4,5));
        System.out.println(ok);
    }
}
