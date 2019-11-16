package com.coderman.rbac.sys.service.impl;

import com.coderman.rbac.sys.bean.Department;
import com.coderman.rbac.sys.bean.DeptDTreeJson;
import com.coderman.rbac.sys.mapper.DepartmentMapper;
import com.coderman.rbac.sys.service.DepartmentService;
import com.coderman.rbac.sys.vo.DepartmentVo;
import com.coderman.rbac.sys.vo.PageVo;
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

/**
 * Created by zhangyukang on 2019/11/13 10:33
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public PageVo<DepartmentVo> findPage(DepartmentVo departmentVo) {
        PageHelper.startPage(departmentVo.getPage(),departmentVo.getLimit());
        Example o = new Example(Department.class);
        o.setOrderByClause("order_number asc");
        Example.Criteria criteria = o.createCriteria();
        if(departmentVo!=null){
            if(departmentVo.getDeptName()!=null&&!"".equals(departmentVo.getDeptName())){
                criteria.andLike("deptName","%"+departmentVo.getDeptName()+"%");
            }
            if(departmentVo.getId()!=null&&!"".equals(departmentVo.getId())){
                criteria.andEqualTo("id",departmentVo.getId());
                criteria.orEqualTo("parentId",departmentVo.getId());
            }
        }
        List<Department> departments = departmentMapper.selectByExample(o);
        PageInfo info=new PageInfo(departments);
        return new PageVo<>(info.getTotal(),info.getList());
    }

    @Override
    public void update(DepartmentVo departmentVo) {
        Department department = new Department();
        BeanUtils.copyProperties(departmentVo,department);
        department.setCreateTime(new Date());
        department.setModifiedTime(new Date());
        departmentMapper.updateByPrimaryKeySelective(department);
    }

    @Override
    public void add(DepartmentVo departmentVo) {
        Department department = new Department();
        BeanUtils.copyProperties(departmentVo,department);
        department.setCreateTime(new Date());
        department.setModifiedTime(new Date());
        if(department.getParentId()==null||"".equals(departmentVo.getParentId())){
            department.setParentId(0L);
        }
        departmentMapper.insertSelective(department);
    }
    @Override
    public void delete(DepartmentVo departmentVo) {
        departmentMapper.deleteByPrimaryKey(departmentVo.getId());
    }

    @Override
    public List<DeptDTreeJson> loadDepartmentTreeJSON() {
        Example o = new Example(Department.class);
        o.setOrderByClause("order_number asc");
        List<Department> departments = departmentMapper.selectByExample(o);
        List<DeptDTreeJson> deptDTreeJsons =new ArrayList<>();
        if(!CollectionUtils.isEmpty(departments)){
            for (Department department : departments) {
                Boolean spread=department.getIsOpen()==1?true:false;
                DeptDTreeJson deptDTreeJson=new DeptDTreeJson(department.getId(), department.getDeptName(), spread,department.getParentId());
                deptDTreeJsons.add(deptDTreeJson);
            }
        }
       return deptDTreeJsons;
    }
}
