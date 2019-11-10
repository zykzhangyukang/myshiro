package com.coderman.rent.sys.service.impl;

import com.coderman.rent.sys.bean.User;
import com.coderman.rent.sys.contast.MyConstant;
import com.coderman.rent.sys.mapper.DepartmentMapper;
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

    @Override
    public PageVo<User> findPage(UserVo userVo) {
        PageHelper.startPage(userVo.getPage(),userVo.getLimit());
        Example example=new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        if(userVo!=null){
            if(userVo.getUserName()!=null&&!"".equals(userVo.getUserName())){
                criteria.andLike("userName","%"+userVo.getUserName()+"%");
            }
            if(userVo.getEmail()!=null&&!"".equals(userVo.getEmail())){
                criteria.andLike("email","%"+userVo.getEmail()+"%");
            }
            if(userVo.getSex()!=null&&!"".equals(userVo.getSex())){
                criteria.andEqualTo("sex",userVo.getSex());
            }
        }
        List<User> users = userMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(users)){
            PageInfo<User> info=new PageInfo<>(users);
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
        userMapper.deleteByPrimaryKey(userVo.getId());
    }
}
