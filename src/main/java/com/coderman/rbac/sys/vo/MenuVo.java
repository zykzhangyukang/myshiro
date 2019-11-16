package com.coderman.rbac.sys.vo;

import com.coderman.rbac.sys.bean.Menu;
import lombok.Data;

/**
 * Created by zhangyukang on 2019/11/12 17:52
 */
@Data
public class MenuVo extends Menu {
    private int page;
    private int limit;
}
