package com.coderman.rbac.sys.test;

import com.coderman.rbac.sys.bean.Role;
import com.coderman.rbac.sys.mapper.RoleExtMapper;
import com.coderman.rbac.sys.service.RoleService;
import com.coderman.rbac.sys.vo.RoleVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * Created by zhangyukang on 2019/11/11 20:26
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRoleExtMapper {

    @Autowired
    private RoleService roleService;

    @Test
    public void testInsertRole(){
        RoleVo roleVo = new RoleVo();
        roleVo.setRemark("test");
        roleVo.setCreateTime(new Date());
        roleVo.setRoleName("test");
        roleVo.setModifiedTime(new Date());
        roleService.add(roleVo);
    }
}
