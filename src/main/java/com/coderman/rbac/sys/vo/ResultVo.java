package com.coderman.rent.sys.vo;

import com.coderman.rent.sys.enums.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回给前端的JSON对象
 * Created by zhangyukang on 2019/11/10 10:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultVo<T> {

    public Integer code;

    private String msg;

    private T data;

    public ResultVo(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public static ResultVo ERROR(){
        ResultVo<Object> objectResultVo = new ResultVo<>();
        objectResultVo.setCode(ResultEnum.ERROR.getCode());
        objectResultVo.setMsg(ResultEnum.ERROR.getMsg());
        return objectResultVo;
    }
    public static ResultVo ERROR(String msg){
        ResultVo<Object> objectResultVo = new ResultVo<>();
        objectResultVo.setCode(ResultEnum.ERROR.getCode());
        objectResultVo.setMsg(msg);
        return objectResultVo;
    }

    public static ResultVo ERROR(ResultEnum resultEnum){
        ResultVo<Object> objectResultVo = new ResultVo<>();
        objectResultVo.setCode(ResultEnum.ERROR.getCode());
        objectResultVo.setMsg(resultEnum.getMsg());
        return objectResultVo;
    }

    /**
     * 放数据
     * @param object
     * @return
     */
    public static ResultVo OK(Object object){
        ResultVo<Object> objectResultVo = new ResultVo<>();
        objectResultVo.setCode(ResultEnum.OK.getCode());
        objectResultVo.setMsg(ResultEnum.OK.getMsg());
        objectResultVo.setData(object);
        return objectResultVo;
    }
    public static ResultVo OK(ResultEnum resultEnum){
        ResultVo<Object> objectResultVo = new ResultVo<>();
        objectResultVo.setCode(ResultEnum.OK.getCode());
        objectResultVo.setMsg(resultEnum.getMsg());
        return objectResultVo;
    }

    public static ResultVo OK(){
        ResultVo<Object> objectResultVo = new ResultVo<>();
        objectResultVo.setCode(ResultEnum.OK.getCode());
        objectResultVo.setMsg(ResultEnum.OK.getMsg());
        return objectResultVo;
    }
}
