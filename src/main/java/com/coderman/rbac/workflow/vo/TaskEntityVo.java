package com.coderman.rbac.workflow.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * Created by zhangyukang on 2019/11/30 16:49
 */
@Data
public class TaskEntityVo {
    private String id;
    private String name;
    @JsonFormat(pattern = "yyyy年MM月dd日 HH时mm分ss秒")
    private Date createTime;
    private String assignee;

}
