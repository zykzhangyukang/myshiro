package com.coderman.rbac.sys.controller;

import com.coderman.rbac.sys.annotation.ControllerEndpoint;
import com.coderman.rbac.sys.bean.DeptDTreeJson;
import com.coderman.rbac.sys.enums.ResultEnum;
import com.coderman.rbac.sys.service.DepartmentService;
import com.coderman.rbac.sys.vo.DepartmentVo;
import com.coderman.rbac.sys.vo.PageVo;
import com.coderman.rbac.sys.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 部门管理
 * Created by zhangyukang on 2019/11/13 10:32
 */
@Api(value = "部门模块")
@RestController
@RequestMapping("/dept")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;


    /**
     * 添加部门
     * @param departmentVo
     * @return
     */
    @ApiOperation(value = "添加部门",notes = "添加部门的详细信息")
    @ApiImplicitParam(value = "部门参数对象",paramType = "query")
    @RequiresPermissions({"dept:add"})
    @PostMapping("/add")
    @ControllerEndpoint(exceptionMessage = "添加部门失败",operation ="添加部门")
    public ResultVo add(DepartmentVo departmentVo){
        try {
            departmentService.add(departmentVo);
            return ResultVo.OK(ResultEnum.ADD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.ERROR(ResultEnum.ADD_FAIL);
        }
    }
    /**
     * 删除部门信息
     * @param departmentVo
     * @return
     */
    @ApiOperation(value = "删除部门",notes = "根据部门id删除部门")
    @RequiresPermissions({"dept:delete"})
    @PostMapping("/delete")
    @ControllerEndpoint(exceptionMessage = "删除部门失败",operation ="删除部门")
    public ResultVo delete(DepartmentVo departmentVo){
        try {
            departmentService.delete(departmentVo);
            return ResultVo.OK(ResultEnum.DELETE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.ERROR(ResultEnum.DELETE_FAIL);
        }
    }
    /**
     * 更新信息
     * @return
     */
    @ApiOperation(value = "更新部门信息",notes = "更新部门的详细信息")
    @ApiImplicitParam(value = "部门的参数对象",paramType = "query")
    @RequiresPermissions({"dept:update"})
    @PostMapping("/update")
    @ControllerEndpoint(exceptionMessage = "修改部门失败",operation ="修改部门")
    public ResultVo update(DepartmentVo departmentVo){
        try {
            departmentService.update(departmentVo);
            return ResultVo.OK(ResultEnum.UPDATE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.ERROR(ResultEnum.UPDATE_FAIL);
        }
    }
    /**
     * 加载部门树
     * @return
     */
    @ApiOperation(value = "加载部门树",notes = "加载前端的部门树JSON对象")
    @PostMapping("/loadDeptTree")
    public ResultVo<List<DeptDTreeJson>> loadDepartmentTreeJSON(){
        List<DeptDTreeJson> deptDTreeJsons = departmentService.loadDepartmentTreeJSON();
        return ResultVo.OK(deptDTreeJsons);
    }
    /**
     * 查询部门
     * @return
     */
    @ApiOperation(value = "查询部门的列表",notes = "加载部门的分页信息")
    @ApiImplicitParam(value = "部门参数对象",paramType = "query")
    @GetMapping("/findPage")
    public PageVo<DepartmentVo> findPage(DepartmentVo departmentVo){
        PageVo<DepartmentVo> page = departmentService.findPage(departmentVo);
        return page;
    }
}
