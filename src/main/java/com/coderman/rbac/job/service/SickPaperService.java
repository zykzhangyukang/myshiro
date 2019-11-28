package com.coderman.rbac.job.service;

import com.coderman.rbac.job.bean.SickPaper;
import com.coderman.rbac.job.vo.SickPaperVo;
import com.coderman.rbac.sys.vo.PageVo;

import java.text.ParseException;

/**
 * Created by zhangyukang on 2019/11/27 16:52
 */
public interface SickPaperService {
    /**
     * 获取系统请假单
     * @param sickPaperVo
     * @return
     */
    PageVo<SickPaper> findPage(SickPaperVo sickPaperVo);

    /**
     * 发布请假单
     * @param sickPaperVo
     */
    void add(SickPaperVo sickPaperVo) throws ParseException;

    /**
     * 删除请假单
     * @param sickPaperVo
     */
    void delete(SickPaperVo sickPaperVo);

    /**
     * 更新请假单
     * @param sickPaperVo
     */
    void update(SickPaperVo sickPaperVo) throws ParseException;
}
