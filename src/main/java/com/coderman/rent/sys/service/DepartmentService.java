package com.coderman.rent.sys.service;

import com.coderman.rent.sys.bean.DeptDTreeJson;
import com.coderman.rent.sys.bean.MenuDTreeJson;
import com.coderman.rent.sys.vo.DepartmentVo;
import com.coderman.rent.sys.vo.PageVo;

import java.util.List;

/**
 * Created by zhangyukang on 2019/11/13 10:32
 */
public interface DepartmentService {
    /**
     * 查询部门
     * @param departmentVo
     * @return
     */
    PageVo<DepartmentVo> findPage(DepartmentVo departmentVo);

    /**
     * 部门更新
     * @param departmentVo
     */
    void update(DepartmentVo departmentVo);

    /**
     * 添加部门信息
     * @param departmentVo
     */
    void add(DepartmentVo departmentVo);

    /**
     * 删除部门
     * @param departmentVo
     */
    void delete(DepartmentVo departmentVo);

    /**
     * 加载部门树的JSON（dTree简单格式）
     * @return
     */
    List<DeptDTreeJson> loadDepartmentTreeJSON();

}
