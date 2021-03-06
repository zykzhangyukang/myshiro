package com.coderman.rbac.sys.service.impl;

import com.coderman.rbac.sys.bean.Role;
import com.coderman.rbac.sys.bean.RoleMenu;
import com.coderman.rbac.sys.bean.UserRole;
import com.coderman.rbac.sys.mapper.RoleExtMapper;
import com.coderman.rbac.sys.mapper.RoleMapper;
import com.coderman.rbac.sys.mapper.RoleMenuMapper;
import com.coderman.rbac.sys.mapper.UserRoleMapper;
import com.coderman.rbac.sys.service.RoleService;
import com.coderman.rbac.sys.vo.PageVo;
import com.coderman.rbac.sys.vo.RoleVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangyukang on 2019/11/11 16:49
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleExtMapper roleExtMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public PageVo<Role> findPage(RoleVo roleVo) {
        PageHelper.startPage(roleVo.getPage(), roleVo.getLimit());
        Example example=new Example(Role.class);
        Example.Criteria criteria = example.createCriteria();
        if(roleVo!=null){
            if(roleVo.getRoleName()!=null&&!"".equals(roleVo.getRoleName())){
                criteria.andLike("roleName","%"+roleVo.getRoleName()+"%");
            }
        }
        List<Role> roles = roleMapper.selectByExample(example);
        PageInfo<Role> info=new PageInfo<>(roles);
        return new PageVo<>(info.getTotal(),info.getList());
    }

    @Override
    public void update(RoleVo roleVo) {
        Role t = new Role();
        t.setModifiedTime(new Date());
        BeanUtils.copyProperties(roleVo,t);
        t.setModifiedTime(new Date());
        roleMapper.updateByPrimaryKeySelective(t);
    }

    @Transactional
    @Override
    public void delete(RoleVo roleVo) {
        //删除该中间表的数据(sys_role_menu)
        Example o = new Example(RoleMenu.class);
        o.createCriteria().andEqualTo("roleId",roleVo.getId());
        roleMenuMapper.deleteByExample(o);
        roleMapper.deleteByPrimaryKey(roleVo.getId());
    }

    @Transactional
    @Override
    public void add(RoleVo roleVo) {
        Role t = new Role();
        BeanUtils.copyProperties(roleVo,t);
        t.setCreateTime(new Date());
        t.setModifiedTime(new Date());
        roleExtMapper.insertRole(t);
        Long roleId=t.getId();
        //角色菜单关联表中插入数据
        if(!CollectionUtils.isEmpty(roleVo.getMIds())){
            roleExtMapper.insertRoleWithMenu(roleId,roleVo.getMIds());
        }
    }

    @Override
    public List<Role> findRoleList(List<Long> roleIds) {
        Example o = new Example(Role.class);
        if(!CollectionUtils.isEmpty(roleIds)){
            o.createCriteria().andIn("id",roleIds);
        }
        List<Role> roles = roleMapper.selectByExample(o);
        return roles;
    }

    @Transactional
    @Override
    public void updateRoleMenu(RoleVo roleVo) {
        //先此用户删除角色菜单表的数据
        Example o = new Example(RoleMenu.class);
        o.createCriteria().andEqualTo("roleId",roleVo.getId());
        roleMenuMapper.deleteByExample(o);
        //插入新的数据
        if(!CollectionUtils.isEmpty(roleVo.getMIds())){
            roleExtMapper.insertRoleWithMenu(roleVo.getId(),roleVo.getMIds());
        }
    }

    @Override
    public List<String> findRoleNameByUserId(Long id) {
        List<String> roleNames=new ArrayList<>();
        Example o = new Example(UserRole.class);
        o.createCriteria().andEqualTo("userId",id);
        List<UserRole> userRoles = userRoleMapper.selectByExample(o);
        if(!CollectionUtils.isEmpty(userRoles)){
            List<Long> roleIds=new ArrayList<>();
            for (UserRole userRole : userRoles) {
                roleIds.add(userRole.getRoleId());
            }
            Example o1 = new Example(Role.class);
            o1.createCriteria().andIn("id",roleIds);
            List<Role> roles = roleMapper.selectByExample(o1);
            if(!CollectionUtils.isEmpty(roles)){
                for (Role role : roles) {
                    roleNames.add(role.getRoleName());
                }
            }
        }
        return roleNames;
    }

}
