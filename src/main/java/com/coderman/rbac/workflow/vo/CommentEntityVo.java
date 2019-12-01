package com.coderman.rbac.workflow.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 评论批注信息
 * Created by zhangyukang on 2019/12/1 18:32
 */
@Data
public class CommentEntityVo {
    private  String id;
    private  String type;
    private  String userId;

    @JsonFormat(pattern = "yyyy年MM月dd日")
    private  Date time;
    private  String taskId;
    private  String message;

}
