package com.coderman.rbac.sys.converter;

import com.coderman.rbac.sys.contast.MyConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 根据时间范围解析时间
 * Created by zhangyukang on 2019/11/10 17:46
 */
public class TimeConverter {

    /**
     * 解析开始时间结束时间
     * @param range
     * @return
     */
    public static Map<String,Object> getTimeListByRange(String range){
        Map<String,Object> map=new HashMap<>();
        if(range.length()>0&&range.contains("~")){
            String[] strings = range.split("~");
            map.put(MyConstant.START_TIME,strings[0]);
            map.put(MyConstant.END_TIME,strings[1]);
        }
        return map;
    }

}
