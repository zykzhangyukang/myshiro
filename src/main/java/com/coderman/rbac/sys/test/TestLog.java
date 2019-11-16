package com.coderman.rent.sys.test;

import com.coderman.rent.sys.bean.Log;
import com.coderman.rent.sys.mapper.LogMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by zhangyukang on 2019/11/15 17:26
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestLog {

    @Autowired
    private LogMapper logMapper;

    @Test
    public void testGetAllLogs(){
        List<Log> logs = logMapper.selectAll();
        System.out.println(logs);
    }
}
