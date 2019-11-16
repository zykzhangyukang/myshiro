package com.coderman.rbac.sys.controller;

import com.coderman.rbac.sys.annotation.ControllerEndpoint;
import com.coderman.rbac.sys.bean.Role;
import com.coderman.rbac.sys.enums.ResultEnum;
import com.coderman.rbac.sys.service.RoleService;
import com.coderman.rbac.sys.vo.PageVo;
import com.coderman.rbac.sys.vo.ResultVo;
import com.coderman.rbac.sys.vo.RoleVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * 角色前端控制器
 * Created by zhangyukang on 2019/11/11 17:00
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 更新角色的菜单
     * @param roleVo
     * @param mIds
     * @return
     */
    @RequiresPermissions({"role:updateRoleMenu"})
    @PostMapping("/updateRoleMenu")
    @ControllerEndpoint(exceptionMessage = "分配菜单失败",operation = "分配菜单/按钮")
    public ResultVo updateRoleMenu(RoleVo roleVo,Long mIds[]){
        roleVo.setMIds(Arrays.asList(mIds));
        try {
            roleService.updateRoleMenu(roleVo);
            return ResultVo.OK(ResultEnum.SQ_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.OK(ResultEnum.SQ_FAIL);
        }
    }

    /**
     * 查询角色
     * @param roleVo
     * @return
     */
    @GetMapping("/findPage")
    public PageVo<Role> findPage(RoleVo roleVo){
        PageVo<Role> page = roleService.findPage(roleVo);
        return page;
    }

    /**
     * 删除角色
     * @param roleVo
     * @return
     */
    @RequiresPermissions({"role:delete"})
    @GetMapping("/delete")
    @ControllerEndpoint(exceptionMessage = "删除角色失败",operation = "删除角色")
    public ResultVo delete(RoleVo roleVo){
        try {
            roleService.delete(roleVo);
            return ResultVo.OK(ResultEnum.DELETE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.ERROR(ResultEnum.DELETE_FAIL);
        }
    }

    /**
     * 添加角色
     * @param roleVo
     * @return
     */
    @RequiresPermissions({"role:add"})
    @PostMapping("/add")
    @ControllerEndpoint(exceptionMessage = "添加角色失败",operation = "添加角色")
    public ResultVo add(RoleVo roleVo,Long mIds[]){
        try {
            roleVo.setMIds(Arrays.asList(mIds));
            roleService.add(roleVo);
            return ResultVo.OK(ResultEnum.ADD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.ERROR(ResultEnum.ADD_FAIL);
        }
    }

    /**
     * 更新角色基本信息
     * @param roleVo
     * @return
     */
    @RequiresPermissions({"role:update"})
    @PostMapping("/update")
    @ControllerEndpoint(exceptionMessage = "修改角色失败",operation = "修改角色")
    public ResultVo update(RoleVo roleVo){
        try {
            roleService.update(roleVo);
            return ResultVo.OK(ResultEnum.UPDATE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.ERROR(ResultEnum.UPDATE_FAIL);
        }
    }

}
