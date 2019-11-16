package com.coderman.rbac.sys.test;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;

import java.util.UUID;

/**
 * Created by zhangyukang on 2019/11/10 10:56
 */
public class TestMD5 {
    @Test
    public void testMD5(){
        //加密方式
        String hashAlgorithmName = "MD5";
        //盐：为了即使相同的密码不同的盐加密后的结果也不同
        String salt= UUID.randomUUID().toString().toUpperCase();
        ByteSource byteSalt = ByteSource.Util.bytes(salt);
        //密码
        Object source = "zhangyukang";
        //加密次数
        int hashIterations = 1024;
        SimpleHash result = new SimpleHash(hashAlgorithmName, source, byteSalt, hashIterations);
        System.out.println(result.toString());
        System.out.println("salt"+":"+salt);
    }
}
