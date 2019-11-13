package com.coderman.rent.sys.controller;

import com.coderman.rent.sys.dto.UserDTO;
import com.coderman.rent.sys.enums.ResultEnum;
import com.coderman.rent.sys.service.UserService;
import com.coderman.rent.sys.vo.PageVo;
import com.coderman.rent.sys.vo.ResultVo;
import com.coderman.rent.sys.vo.UserVo;
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
     * 给用户分配权限
     * @param rids
     * @param userVo
     * @return
     */
    @RequiresPermissions({"user:giveUserRoles"})
    @GetMapping("/giveUserRoles")
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
