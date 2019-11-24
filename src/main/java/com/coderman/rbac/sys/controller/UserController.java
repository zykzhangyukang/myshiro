package com.coderman.rbac.sys.controller;

import com.coderman.rbac.sys.annotation.ControllerEndpoint;
import com.coderman.rbac.sys.bean.User;
import com.coderman.rbac.sys.dto.UserDTO;
import com.coderman.rbac.sys.enums.ResultEnum;
import com.coderman.rbac.sys.service.UserService;
import com.coderman.rbac.sys.utils.WebUtil;
import com.coderman.rbac.sys.vo.PageVo;
import com.coderman.rbac.sys.vo.ResultVo;
import com.coderman.rbac.sys.vo.UserVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Repeat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 用户管理控制器
 * Created by zhangyukang on 2019/11/10 18:56
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * 查询用户数量
     * @return
     */
    @GetMapping("/count")
    public Map<String,Object> count(){
        Long  count=userService.count();
        Map<String,Object> map=new HashMap<>();
        map.put("count",count-1);//减去超级用户不显示
        return map;
    }

    /**
     * 根据id查询用户
     * @param userVo
     * @return
     */
    @GetMapping("/findUserById")
    public ResultVo findUserById(UserVo userVo){
        User user =userService.findUserById(userVo);
        return ResultVo.OK(user);
    }

    /**
     * 加载父级领导通过父级部门ID
     * @return
     */
    @PostMapping("/loadManagersByParentDeptId")
    public ResultVo loadManagersByParentDeptId(UserVo userVo){
        List<User> managerList=userService.loadManagersByParentDeptId(userVo);
        return ResultVo.OK(managerList);
    }

    /**
     * 重置用户(密码)
     * @return
     */
    @RequiresPermissions("user:reSet")
    @GetMapping("/reSetUser")
    @ControllerEndpoint(exceptionMessage = "重置用户失败",operation ="重置用户")
    public ResultVo reSetUser(UserVo userVo){
        try {
            userService.reSetUser(userVo);
            return ResultVo.OK(ResultEnum.RESET_PWD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.ERROR(ResultEnum.RESET_PWD_FAIL);
        }
    }
    /**
     * 给用户分配权限
     * @param rids
     * @param userVo
     * @return
     */
    @RequiresPermissions({"user:giveUserRoles"})
    @GetMapping("/giveUserRoles")
    @ControllerEndpoint(exceptionMessage = "分配角色失败",operation ="分配角色")
    public ResultVo giveUserRoles(Integer rids[],UserVo userVo){
        try {
            List<Integer> list=new ArrayList<>();
            if(rids!=null&&rids.length>0){
                list= Arrays.asList(rids);
            }
            userService.giveUserRoles(userVo, list);
            return ResultVo.OK(ResultEnum.GIVE_ROLE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.ERROR(ResultEnum.GIVE_ROLE_FAIL);
        }
    }

    /**
     * 加载用户拥有的角色和没用拥有的角色
     * @param userVo
     * @return
     */
    @GetMapping("/loadUserRoles")
    public ResultVo loadUserRoles(UserVo userVo){
        Map<String,Object> map=userService.loadUserRoles(userVo);
        return ResultVo.OK(map);
    }
    /**
     * 批量删除
     * @param userVo
     * @return
     */
    @RequiresPermissions({"user:batchDelete"})
    @GetMapping("/batchDelete")
    @ControllerEndpoint(exceptionMessage = "批量删除用户失败",operation ="批量删除用户")
    public ResultVo batchDelete(UserVo userVo){
        try {
            userService.batchDelete(userVo);
            return ResultVo.OK(ResultEnum.DELETE_SUCCESS);
        } catch (Exception e) {
            return ResultVo.ERROR(ResultEnum.DELETE_FAIL);
        }
    }
    /**
     * 删除用户
     * @param userVo
     * @return
     */
    @RequiresPermissions({"user:delete"})
    @GetMapping("/delete")
    @ControllerEndpoint(exceptionMessage = "删除用户失败",operation ="删除用户")
    public ResultVo delete(UserVo userVo){
        try {
            userService.delete(userVo);
            return ResultVo.OK(ResultEnum.DELETE_SUCCESS);
        } catch (Exception e) {
            return ResultVo.OK(ResultEnum.DELETE_FAIL);
        }
    }
    /**
     * 查询用户
     * @param userVo
     * @return
     */
    @GetMapping("/findPage")
    public PageVo<UserDTO> findPage(UserVo userVo){
        PageVo<UserDTO> page = userService.findPage(userVo);
        return page;
    }
    /**
     * 锁定用户
     * @return
     */
    @RequiresPermissions({"user:lock"})
    @PostMapping("/lock")
    @ControllerEndpoint(exceptionMessage = "锁定用户失败",operation ="用户锁定/解锁")
    public ResultVo lock(UserVo userVo){
        try {
            userService.update(userVo);
            return new ResultVo().OK(ResultEnum.LOCK_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultVo().ERROR(ResultEnum.LOCK_FAIL);
        }
    }
    /**
     * 更新用户
     * @param userVo
     * @return
     */
    @RequiresPermissions({"user:update"})
    @PostMapping("/update")
    @ControllerEndpoint(exceptionMessage = "修改用户失败",operation ="修改用户")
    public ResultVo update(UserVo userVo){
        try {
            userService.update(userVo);
            return new ResultVo().OK(ResultEnum.UPDATE_SUCCESS);
        } catch (Exception e) {
            return new ResultVo().ERROR(ResultEnum.UPDATE_FAIL);
        }
    }

    /**
     * 添加用户
     * @param userVo
     * @return
     */
    @RequiresPermissions({"user:add"})
    @PostMapping("/add")
    @ControllerEndpoint(exceptionMessage = "添加用户失败",operation ="添加用户")
    public ResultVo add(UserVo userVo){
        try {
            userService.add(userVo);
            return ResultVo.OK(ResultEnum.ADD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.OK(ResultEnum.ADD_FAIL);
        }
    }
}
