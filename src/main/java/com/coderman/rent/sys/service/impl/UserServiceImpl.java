package com.coderman.rent.sys.service.impl;

import com.coderman.rent.sys.bean.User;
import com.coderman.rent.sys.mapper.UserMapper;
import com.coderman.rent.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Created by zhangyukang on 2019/11/10 10:45
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserByName(String userName) {
        Example example = new Example(User.class);
        example.createCriteria().andLike("userName",userName);
        List<User> users = userMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(users)){
            return   users.get(0);
        }
        return null;
    }
}
