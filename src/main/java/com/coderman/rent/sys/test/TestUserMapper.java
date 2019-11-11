package com.coderman.rent.sys.test;

//import com.coderman.rent.sys.mapper.UserMapper;
import com.coderman.rent.sys.bean.Menu;
import com.coderman.rent.sys.bean.User;
import com.coderman.rent.sys.mapper.UserMapper;
import com.coderman.rent.sys.service.MenuService;
import com.coderman.rent.sys.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.List;

//import java.util.List;

/**
 * Created by zhangyukang on 2019/11/9 20:48
 */

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class TestUserMapper {

    @Autowired
    private MenuService menuService;

    @Test
    public void testgetAllAvailableMenu(){
        List<Menu> menus = menuService.loadAllMenu();
        System.out.println(menus.size());
    }

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testGetAllUser(){
        List<User> users = userMapper.selectAll();
        System.out.println(users);
    }
    @Test
    public void test(){
        System.out.println("hello word");
    }

    @Test
    public void testListAllUserWithDepartment(){
//        UserVo userVo = new UserVo();
//        List<User> userVos = userMapper.listAllUserWithDept(userVo);
//        if(!CollectionUtils.isEmpty(userVos)){
//            for (User vo : userVos) {
//                System.out.println(vo);
//            }
//        }
    }

}
