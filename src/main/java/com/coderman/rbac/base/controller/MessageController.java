package com.coderman.rbac.base.controller;

import com.coderman.rbac.base.bean.Message;
import com.coderman.rbac.base.service.MessageService;
import com.coderman.rbac.base.vo.MessageVo;
import com.coderman.rbac.sys.enums.ResultEnum;
import com.coderman.rbac.sys.vo.PageVo;
import com.coderman.rbac.sys.vo.ResultVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 系统留言
 * Created by zhangyukang on 2019/11/23 17:33
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * 系统留言
     * @param messageVo
     * @return
     */
    @ApiOperation(value = "系统留言",notes = "系统留言的分页信息")
    @GetMapping("/findPage")
    public PageVo findPage(MessageVo messageVo){
        PageVo<Message> messages=messageService.findPage(messageVo);
        return messages;
    }

    /**
     * 发布留言
     * @return
     */
    @ApiOperation(value = "发布留言",notes = "发布留言信息")
    @PostMapping("/publish")
    public ResultVo publish(MessageVo messageVo){
        try {
            messageService.publish(messageVo);
            return ResultVo.OK(ResultEnum.PUBLISH_MESSAGE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.ERROR(ResultEnum.PUBLISH_MESSAGE_FAIL);
        }
    }

    /**
     * 修改留言
     * @return
     */
    @ApiOperation(value = "修改留言",notes = "修改留言信息")
    @PostMapping("/update")
    public ResultVo update(MessageVo messageVo){
        try {
            messageService.update(messageVo);
            return ResultVo.OK(ResultEnum.UPDATE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.ERROR(ResultEnum.UPDATE_FAIL);
        }
    }

    /**
     * 删除留言
     * @return
     */
    @ApiOperation(value = "删除留言",notes = "删除留言信息")
    @GetMapping("/delete")
    public ResultVo delete(MessageVo messageVo){
        try {
            messageService.delete(messageVo);
            return ResultVo.OK(ResultEnum.DELETE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.ERROR(ResultEnum.DELETE_FAIL);
        }
    }
}
