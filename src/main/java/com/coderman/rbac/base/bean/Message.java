package com.coderman.rbac.base.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 系统留言消息
 * Created by zhangyukang on 2019/11/23 17:19
 */
@Data
@Table(name = "base_message")
public class Message {

    @Id
    private Long id;//主键

    /**留言标题**/
    private String title;

    /**创建时间**/
    @JsonFormat(pattern = "yyyy年MM月dd日 HH时mm分ss秒")
    private Date createTime;

    /**留言人IP**/
    private String ip;

    /**留言人地理位置**/
    private String location;

    /**留言人姓名**/
    private String creator;

    /**留言是否可见**/
    private int isShow=1;

    /**留言内容**/
    private String content;


}
