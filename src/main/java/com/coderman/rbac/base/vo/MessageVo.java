package com.coderman.rbac.base.vo;

import com.coderman.rbac.base.bean.Message;
import lombok.Data;

/**
 * Created by zhangyukang on 2019/11/23 17:35
 */
@Data
public class MessageVo extends Message {
    private int page;
    private int limit;
    private String range;
}
