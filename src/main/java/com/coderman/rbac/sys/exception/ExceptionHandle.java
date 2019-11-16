package com.coderman.rent.sys.exception;

import com.coderman.rent.sys.enums.ResultEnum;
import com.coderman.rent.sys.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zhangyukang on 2019/10/30 13:39
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandle {

    /**
     * 处理权限不足时候的异常处理器
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    public ResultVo UnAuthMethod(Exception e){
        log.warn("【权限不足】 message={}",e.getMessage());
        return ResultVo.ERROR(ResultEnum.YOU_NOT_PERMISSION);
    }
}
