package com.coderman.rent.sys.controller;

import com.coderman.rent.sys.bean.User;
import com.coderman.rent.sys.enums.ResultEnum;
import com.coderman.rent.sys.service.UserService;
import com.coderman.rent.sys.vo.PageVo;
import com.coderman.rent.sys.vo.ResultVo;
import com.coderman.rent.sys.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * 删除用户
     * @param userVo
     * @return
     */
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
    public PageVo<User> findPage(UserVo userVo){
        PageVo<User> page = userService.findPage(userVo);
        return page;
    }

    /**
     * 更新用户
     * @param userVo
     * @return
     */
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
