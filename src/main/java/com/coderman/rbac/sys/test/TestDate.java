package com.coderman.rent.sys.test;

import com.coderman.rent.sys.converter.TimeConverter;
import org.junit.Test;

import java.util.Map;

/**
 * Created by zhangyukang on 2019/11/10 17:44
 */
public class TestDate {
    private String dateStr="2019-11-01 ~ 2019-12-25";
    @Test
    public void setDateStr(){
        String[] split = dateStr.split("~");
        for (String s : split) {
            System.out.println(s.trim());
        }
    }
    @Test
    public void testTimeConverter(){
        Map<String, Object> map = TimeConverter.getTimeListByRange(dateStr);
        System.out.println(map);

    }
}
