package com.coderman.rbac.sys.controller;

import com.coderman.rbac.base.vo.ResultFileVo;
import com.coderman.rbac.sys.annotation.ControllerEndpoint;
import com.coderman.rbac.sys.bean.User;
import com.coderman.rbac.sys.contast.MyConstant;
import com.coderman.rbac.sys.dto.UserDTO;
import com.coderman.rbac.sys.enums.ResultEnum;
import com.coderman.rbac.sys.service.UserService;
import com.coderman.rbac.sys.utils.MD5Util;
import com.coderman.rbac.sys.utils.WebUtil;
import com.coderman.rbac.sys.vo.PageVo;
import com.coderman.rbac.sys.vo.ResultVo;
import com.coderman.rbac.sys.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.validator.internal.IgnoreForbiddenApisErrors;
import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Repeat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用户管理控制器
 * Created by zhangyukang on 2019/11/10 18:56
 */
@Api(value = "用户模块")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户更改密码
     * @return
     */
    @ApiOperation(value = "用户更改密码")
    @ApiImplicitParam(value = "用户信息的参数对象",paramType = "query")
    @PostMapping("/changePwd")
    public ResultVo changePwd(UserVo userVo){
        //验证旧密码
        String oldPassWord = userVo.getOldPassWord();
        User user = (User) WebUtil.getSession().getAttribute(MyConstant.USER);
        String encrypt = MD5Util.encrypt(user.getSalt(), oldPassWord);
        if(!encrypt.equals(user.getPassWord())){
            return ResultVo.ERROR(ResultEnum.OLD_PWD_WRONG);
        }
        try {
            userService.changePwd(userVo);
            return ResultVo.OK(ResultEnum.CHANGE_PWD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.OK(ResultEnum.CHANGE_PWD_ERROR);
        }
    }
    /**
     * 用户更换头像
     * @return
     */
    @ApiOperation(value = "更改用户的头像",notes = "更改登入用户的头像")
    @ControllerEndpoint(exceptionMessage = "更换头像失败",operation ="更换头像")
    @PostMapping("/changeAvatar")
    public ResultFileVo changAvatar(MultipartFile file,UserVo userVo){
        try {
            return  userService.changAvatar(file,userVo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultFileVo.ERROR();
        }
    }
    /**
     * 用户信息
     * @return
     */
    @ApiOperation(value = "获取用户的信息",notes = "获取用户的详细信息")
    @ApiImplicitParam(value = "用户的参数对象",paramType = "query")
    @GetMapping("/userInfo")
    public ResultVo userInfo(UserVo userVo){
            UserDTO user= userService.userInfo(userVo);
            return ResultVo.OK(user);
    }
    /**
     * 查询用户数量
     * @return
     */
    @ApiOperation(value = "查询用户的对象数量",notes = "查询该系统的用户数量")
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
    @ApiOperation(value = "查询单个用户",notes = "根据ID查询单个用户信息")
    @ApiImplicitParam(value = "用户参数对象")
    @GetMapping("/findUserById")
    public ResultVo findUserById(UserVo userVo){
        User user =userService.findUserById(userVo);
        return ResultVo.OK(user);
    }

    /**
     * 加载父级领导通过父级部门ID
     * @return
     */
    @ApiOperation(value = "加载用户父级领导",notes = "通过父级部门ID查询所有的父级领导")
    @PostMapping("/loadManagersByParentDeptId")
    public ResultVo loadManagersByParentDeptId(UserVo userVo){
        List<User> managerList=userService.loadManagersByParentDeptId(userVo);
        return ResultVo.OK(managerList);
    }

    /**
     * 重置用户(密码)
     * @return
     */
    @ApiOperation(value = "重置用户的密码",notes = "重置用户的密码")
    @ApiImplicitParam(value = "用户参数对象",paramType = "query")
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
    @ApiOperation(value = "用户分配角色",notes = "用户分配角色")
    @ApiImplicitParam(value ="角色ID的数组",paramType = "query")
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
    @ApiOperation(value = "加载用户的角色",notes = "加载用户拥有的角色和没用拥有的角色")
    @ApiImplicitParam(value = "用户的参数的对象",paramType = "query")
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
    @ApiOperation(value = "批量删除",notes = "批量删除用户")
    @ApiImplicitParam(value ="用户的参数对象",paramType = "query")
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
    @ApiOperation(value = "删除用户",notes = "删除用户根据用户的id")
    @ApiImplicitParam(value = "用户的参数对象",paramType = "query")
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
    @ApiOperation(value = "查询用户的列表",notes = "查询用户的分页信息")
    @ApiImplicitParam(value = "用户的参数对象",paramType = "query")
    @GetMapping("/findPage")
    public PageVo<UserDTO> findPage(UserVo userVo){
        PageVo<UserDTO> page = userService.findPage(userVo);
        return page;
    }
    /**
     * 锁定用户
     * @return
     */
    @ApiOperation(value = "锁定用户",notes = "锁定用户")
    @ApiImplicitParam(value = "用户的参数对象",paramType = "query")
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
     * 更新用户的性别
     * @return
     */
    @ApiOperation(value = "更新用户的性别",notes = "更新用户的性别")
    @ApiImplicitParam(value = "用户的参数对象",paramType = "query")
    @RequiresPermissions({"user:update:sex"})
    @ControllerEndpoint(exceptionMessage = "更新用户性别失败",operation ="更新用户性别")
    @PostMapping("/updateSex")
    public ResultVo updateSex(UserVo userVo){
        try {
            userService.updateSex(userVo);
            return new ResultVo().OK(ResultEnum.UPDATE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultVo().ERROR(ResultEnum.UPDATE_FAIL);
        }
    }

    /**
     * 更新用户
     * @param userVo
     * @return
     */
    @ApiOperation(value = "更新用户",notes = "更新用户的信息")
    @ApiImplicitParam(value = "更新用户的参数对象",paramType = "query")
    @RequiresPermissions({"user:update"})
    @PostMapping("/update")
    @ControllerEndpoint(exceptionMessage = "修改用户失败",operation ="修改用户")
    public ResultVo update(UserVo userVo){
        try {
            //验证用户名是否被占用
            if(nameIsUsed(userVo)){
                return ResultVo.ERROR(ResultEnum.NAME_IS_USED);
            }
            userService.update(userVo);
            return new ResultVo().OK(ResultEnum.UPDATE_SUCCESS);
        } catch (Exception e) {
            return new ResultVo().ERROR(ResultEnum.UPDATE_FAIL);
        }
    }

    /**
     * 判断用户名是否被占用
     * @return
     */
    private boolean nameIsUsed(UserVo userVo) {
        return userService.nameIsUsed(userVo);
    }

    /**
     * 添加用户
     * @param userVo
     * @return
     */
    @ApiOperation(value = "添加用户",notes = "添加用户的详细信息")
    @ApiImplicitParam(value = "用户的参数对象",paramType = "query")
    @RequiresPermissions({"user:add"})
    @PostMapping("/add")
    @ControllerEndpoint(exceptionMessage = "添加用户失败",operation ="添加用户")
    public ResultVo add(UserVo userVo){
        try {
            //验证用户名是否被占用
            if(nameIsUsed(userVo)){
                return ResultVo.ERROR(ResultEnum.NAME_IS_USED);
            }
            userService.add(userVo);
            return ResultVo.OK(ResultEnum.ADD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.OK(ResultEnum.ADD_FAIL);
        }
    }
}
