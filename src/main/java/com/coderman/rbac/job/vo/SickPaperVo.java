package com.coderman.rbac.job.vo;

import com.coderman.rbac.job.bean.SickPaper;
import lombok.Data;

/**
 * Created by zhangyukang on 2019/11/27 16:52
 */
@Data
public class SickPaperVo extends SickPaper {
    private int page;
    private int limit;
    private String range;

    /**请假时间范围**/
    private String sickRange;
}
