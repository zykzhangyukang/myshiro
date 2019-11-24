package com.coderman.rbac.sys.service.impl;

import com.coderman.rbac.base.utils.OssUploadImgProvider;
import com.coderman.rbac.base.vo.ResultFileVo;
import com.coderman.rbac.sys.bean.*;
import com.coderman.rbac.sys.contast.MyConstant;
import com.coderman.rbac.sys.dto.UserDTO;
import com.coderman.rbac.sys.mapper.*;
import com.coderman.rbac.sys.service.DepartmentService;
import com.coderman.rbac.sys.service.UserService;
import com.coderman.rbac.sys.utils.MD5Util;
import com.coderman.rbac.sys.vo.PageVo;
import com.coderman.rbac.sys.vo.UserVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.sql.Ref;
import java.time.chrono.IsoEra;
import java.util.*;

/**
 * Created by zhangyukang on 2019/11/10 10:45
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserExtMapper userExtMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMapper roleMapper;


    @Override
    public User findUserByName(String userName) {
        Example example = new Example(User.class);
        example.createCriteria().andLike("userName", userName);
        List<User> users = userMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(users)) {
            return users.get(0);
        }
        return null;
    }

    @Override
    public PageVo<UserDTO> findPage(UserVo userVo) {
        PageHelper.startPage(userVo.getPage(), userVo.getLimit());
        List<UserDTO> users = userExtMapper.findAllWithDepartment(userVo);
        if (!CollectionUtils.isEmpty(users)) {
            PageInfo<UserDTO> info = new PageInfo<>(users);
            return new PageVo<>(info.getTotal(), info.getList());
        }
        return new PageVo<>(null);
    }

    @Override
    public void update(UserVo userVo) {
        User t = new User();
        BeanUtils.copyProperties(userVo, t);
        t.setModifiedTime(new Date());
        userMapper.updateByPrimaryKeySelective(t);
    }

    @Override
    public void add(UserVo userVo) {
        User user = new User();
        BeanUtils.copyProperties(userVo, user);
        user.setCreateTime(new Date());
        String salt = UUID.randomUUID().toString();
        user.setPassWord(MD5Util.encrypt(salt, MyConstant.DEFAULT_PWD));
        user.setSalt(salt);
        user.setModifiedTime(new Date());
        userMapper.insertSelective(user);
    }

    @Transactional
    @Override
    public void delete(UserVo userVo) {
        //删除中间表(user_role)
        Example o = new Example(UserRole.class);
        o.createCriteria().andEqualTo("userId", userVo.getId());
        userRoleMapper.deleteByExample(o);
        userMapper.deleteByPrimaryKey(userVo.getId());
    }

    @Transactional
    @Override
    public void batchDelete(UserVo userVo) {

        String ids = userVo.getIds();
        String[] split = ids.split(",");
        List<Long> idsList = new ArrayList<>();
        for (String s : split) {
            idsList.add(Long.parseLong(s));
        }
        if (!CollectionUtils.isEmpty(idsList)) {
            Example example = new Example(User.class);
            example.createCriteria().andIn("id", idsList);
            userMapper.deleteByExample(example);
            Example o = new Example(UserRole.class);
            o.createCriteria().andIn("userId", idsList);
            userRoleMapper.deleteByExample(o);
        }
    }

    @Override
    public void updateLastLoginTime(ActiveUser activeUser) {
        User user = activeUser.getUser();
        user.setLastLoginTime(new Date());
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public List<Role> listRolesByUserId(UserVo userVo) {
        Example o = new Example(UserRole.class);
        o.createCriteria().andEqualTo("userId", userVo.getId());
        List<UserRole> userRoles = userRoleMapper.selectByExample(o);
        if (!CollectionUtils.isEmpty(userRoles)) {

        }
        return null;
    }

    @Override
    public Map<String, Object> loadUserRoles(UserVo userVo) {
        //已经拥有的
        Example o = new Example(UserRole.class);
        o.createCriteria().andEqualTo("userId", userVo.getId());
        List<UserRole> userRoles = userRoleMapper.selectByExample(o);
        //所有的角色
        List<Long> haveList = new ArrayList<>();
        List<Role> roles = roleMapper.selectAll();
        if (!CollectionUtils.isEmpty(roles)) {
            haveList = new ArrayList<>();
            for (Role role : roles) {
                for (UserRole userRole : userRoles) {
                    if (userRole.getRoleId().equals(role.getId())) {
                        haveList.add(role.getId());
                        break;
                    }
                }
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("allRoles", roles);
        map.put("haveRoles", haveList);
        return map;
    }

    @Transactional
    @Override
    public void giveUserRoles(UserVo userVo, List<Integer> rids) {
        Long userId = userVo.getId();
        //先删除之前该用户拥有的角色
        Example o = new Example(UserRole.class);
        o.createCriteria().andEqualTo("userId", userId);
        userRoleMapper.deleteByExample(o);
        if (!CollectionUtils.isEmpty(rids)) {
            userExtMapper.insertUserWithRoles(userId, rids);
        }
    }

    @Override
    public void reSetUser(UserVo userVo) {
        String ids = userVo.getIds();
        List<Long> idlist = new ArrayList<>();
        if (ids.contains(",")) {
            idlist = new ArrayList<>();
            String[] idstr = ids.split(",");
            for (String s : idstr) {
                idlist.add(Long.parseLong(s));
            }
        } else {
            idlist.add(Long.parseLong(ids));
        }
        //重置
        if (!CollectionUtils.isEmpty(idlist)) {
            for (Long id : idlist) {
                String salt = UUID.randomUUID().toString().toUpperCase();
                String password = MD5Util.encrypt(salt, MyConstant.DEFAULT_PWD);
                User user = new User();
                user.setId(id);
                user.setModifiedTime(new Date());
                user.setSalt(salt);
                user.setPassWord(password);
                userMapper.updateByPrimaryKeySelective(user);
            }
        }
    }

    @Override
    public List<User> loadManagersByParentDeptId(UserVo userVo) {
        Long parentDeptId = userVo.getParentDeptId();
        Example o = new Example(User.class);
        o.createCriteria().andEqualTo("deptId", parentDeptId);
        List<User> users = userMapper.selectByExample(o);
        return users;
    }

    @Override
    public User findUserById(UserVo userVo) {
        User user = userExtMapper.findUserById(userVo);
        return user;
    }

    @Override
    public Long count() {
        Long count = Long.valueOf(userMapper.selectCountByExample(null));
        return count;
    }

    @Override
    public UserDTO userInfo(UserVo userVo) {
        UserDTO userDTO = userExtMapper.userInfo(userVo);
        return userDTO;
    }

    @Autowired
    private OssUploadImgProvider ossUploadImgProvider;

    @Override
    public ResultFileVo changAvatar(MultipartFile file, UserVo userVo) {
        String src;
        try {
            src = ossUploadImgProvider.UploadFile(file.getInputStream(), file.getContentType(), file.getOriginalFilename());
            User user = new User();
            BeanUtils.copyProperties(userVo,user);
            user.setAvatar(src);
            userMapper.updateByPrimaryKeySelective(user);
            HashMap obj = new HashMap();
            obj.put("src",src);
            return ResultFileVo.SUCCESS(obj);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultFileVo.ERROR();
        }
    }
}