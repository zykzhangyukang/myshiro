package com.coderman.rent.sys.mapper;

import com.coderman.rent.sys.dto.UserDTO;
import com.coderman.rent.sys.vo.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zhangyukang on 2019/11/11 15:32
 */
public interface UserExtMapper {
    List<UserDTO> findAllWithDepartment(UserVo userVo);

    void insertUserWithRoles(@Param("userId") Long userId, @Param("roles") List<Integer> rids);
}
