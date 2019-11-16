package com.coderman.rbac.sys.vo;

import com.coderman.rbac.sys.bean.Department;
import lombok.Data;

/**
 * Created by zhangyukang on 2019/11/13 10:34
 */
@Data
public class DepartmentVo extends Department {
    private int page;
    private int limit;
}
