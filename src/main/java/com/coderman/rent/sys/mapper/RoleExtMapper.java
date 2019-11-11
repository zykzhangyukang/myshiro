package com.coderman.rent.sys.mapper;

import com.coderman.rent.sys.bean.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zhangyukang on 2019/11/11 20:21
 */
public interface RoleExtMapper {

    Long insertRole(Role role);

    void insertRoleWithMenu(@Param("roleId") Long roleId, @Param("mIds") List<Long> mIds);
}
