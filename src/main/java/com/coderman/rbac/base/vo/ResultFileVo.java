package com.coderman.rbac.base.vo;

import lombok.Data;

import java.util.Map;

/**
 * Created by zhangyukang on 2019/11/23 19:53
 */
@Data
public class ResultFileVo {
    private Integer code;
     private String msg;
     private Map<String,Object> data;

     public static ResultFileVo SUCCESS(Map obj){
         ResultFileVo resultFileVo = new ResultFileVo();
         resultFileVo.setCode(0);
         resultFileVo.setData(obj);
         resultFileVo.setMsg("上传成功");
         return resultFileVo;
     }

     public static ResultFileVo ERROR(){
         ResultFileVo resultFileVo = new ResultFileVo();
         resultFileVo.setCode(1);
         resultFileVo.setMsg("上传失败");
         return resultFileVo;
     }
}
