package com.coderman.rbac.base.service;

import com.coderman.rbac.base.bean.Message;
import com.coderman.rbac.base.vo.MessageVo;
import com.coderman.rbac.sys.vo.PageVo;

/**
 * Created by zhangyukang on 2019/11/23 17:34
 */
public interface MessageService {
    /**
     * 获取系统留言
     * @param messageVo
     * @return
     */
    PageVo<Message> findPage(MessageVo messageVo);

    /**
     * 发布留言
     * @param messageVo
     */
    void publish(MessageVo messageVo);

    /**
     * 删除留言
     * @param messageVo
     */
    void delete(MessageVo messageVo);

    /**
     * 更新留言
     * @param messageVo
     */
    void update(MessageVo messageVo);
}
