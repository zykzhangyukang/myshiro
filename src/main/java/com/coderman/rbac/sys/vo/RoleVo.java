package com.coderman.rbac.sys.vo;

import com.coderman.rbac.sys.bean.Role;
import lombok.Data;

import java.util.List;

/**
 * Created by zhangyukang on 2019/11/11 16:51
 */
@Data
public class RoleVo extends Role {
    private int page;
    private int limit;
    //接收用户的菜单Id
    private List<Long> mIds;
}
