package com.coderman.rbac.base.mapper;

import com.coderman.rbac.sys.bean.User;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangyukang on 2019/11/26 11:01
 */
public interface LoginLogExtMapper {
    List<Map<String,Object>> findLastSevenDaysVisitCount(User user);

    List<Map<String,Object>> loadLoginLocation();

}
