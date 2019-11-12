package com.coderman.rent.sys.service.impl;

import com.coderman.rent.sys.bean.ActiveUser;
import com.coderman.rent.sys.bean.User;
import com.coderman.rent.sys.contast.MyConstant;
import com.coderman.rent.sys.dto.UserDTO;
import com.coderman.rent.sys.mapper.DepartmentMapper;
import com.coderman.rent.sys.mapper.UserExtMapper;
import com.coderman.rent.sys.mapper.UserMapper;
import com.coderman.rent.sys.service.UserService;
import com.coderman.rent.sys.utils.MD5Util;
import com.coderman.rent.sys.vo.PageVo;
import com.coderman.rent.sys.vo.UserVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by zhangyukang on 2019/11/10 10:45
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserExtMapper userExtMapper;


    @Override
    public User findUserByName(String userName) {
        Example example = new Example(User.class);
        example.createCriteria().andLike("userName",userName);
        List<User> users = userMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(users)){
            return users.get(0);
        }
        return null;
    }

    @Override
    public PageVo<UserDTO> findPage(UserVo userVo) {
        PageHelper.startPage(userVo.getPage(),userVo.getLimit());
        List<UserDTO> users = userExtMapper.findAllWithDepartment(userVo);
        if(!CollectionUtils.isEmpty(users)){
            PageInfo<UserDTO> info=new PageInfo<>(users);
            return new PageVo<>(info.getTotal(),info.getList());
        }
        return null;
    }

    @Override
    public void update(UserVo userVo) {
        userVo.setModifiedTime(new Date());
        userMapper.updateByPrimaryKeySelective(userVo);
    }

    @Override
    public void add(UserVo userVo) {
        User user = new User();
        BeanUtils.copyProperties(userVo,user);
        user.setCreateTime(new Date());
        String salt= UUID.randomUUID().toString()+user.getUserName().toUpperCase();
        user.setPassWord(MD5Util.encrypt(salt, MyConstant.DEFAULT_PWD));
        user.setSalt(salt);
        user.setModifiedTime(new Date());
        userMapper.insertSelective(user);
    }

    @Override
    public void delete(UserVo userVo) {
        //删除中间表
        //TODO
        userMapper.deleteByPrimaryKey(userVo.getId());
    }

    @Override
    public void batchDelete(UserVo userVo) {
        //删除中间表
        //TODO
        String ids = userVo.getIds();
        String[] split = ids.split(",");
        List<Long> idsList=new ArrayList<>();
        for (String s : split) {
            idsList.add(Long.parseLong(s));
        }
        if(!CollectionUtils.isEmpty(idsList)){
            Example example = new Example(User.class);
            example.createCriteria().andIn("id",idsList);
            userMapper.deleteByExample(example);
        }
    }

    @Override
    public void updateLastLoginTime(ActiveUser activeUser) {
        User user = activeUser.getUser();
        user.setLastLoginTime(new Date());
        userMapper.updateByPrimaryKeySelective(user);
    }
}
