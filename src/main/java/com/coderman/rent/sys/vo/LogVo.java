package com.coderman.rent.sys.vo;

import com.coderman.rent.sys.bean.Log;
import lombok.Data;

/**
 * Created by zhangyukang on 2019/11/15 17:29
 */
@Data
public class LogVo extends Log {
    private int page;
    private int limit;
    private String ids;
    private String range;
}
