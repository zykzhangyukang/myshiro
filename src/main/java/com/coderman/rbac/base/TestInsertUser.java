package com.coderman.rbac.base;

import com.coderman.rbac.sys.bean.User;
import com.coderman.rbac.sys.contast.MyConstant;
import com.coderman.rbac.sys.mapper.UserExtMapper;
import com.coderman.rbac.sys.utils.MD5Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.UUID;

/**
 * Created by zhangyukang on 2019/11/25 11:21
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestInsertUser {

    @Autowired
    private UserExtMapper userExtMapper;


    @Test
    public  void testInsertUser(){
        User user = new User();
        user.setStatus(MyConstant.UN_LOCKED);
        user.setModifiedTime(new Date());
        user.setDescription("fdsafdsaf");
        user.setSex(MyConstant.SEX_MAN);
        user.setUserName("fasdfdsaf");
        String salt= UUID.randomUUID().toString().toUpperCase();
        user.setPassWord(MD5Util.encrypt(salt,MyConstant.DEFAULT_PWD));
        user.setType(1);
        user.setMgrId(1l);
        user.setEmail("fdasf@qq.com");
        user.setBirth("ffasdfasd");
        user.setSalt(salt);
        user.setCreateTime(new Date());
        Long aLong = userExtMapper.insertUserReturnUser(user);
        System.out.println("id:----->"+user.getId());
    }
}
