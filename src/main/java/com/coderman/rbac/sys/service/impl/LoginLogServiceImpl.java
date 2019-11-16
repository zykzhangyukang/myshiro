package com.coderman.rbac.sys.service.impl;

import com.coderman.rbac.sys.bean.LoginLog;
import com.coderman.rbac.sys.contast.MyConstant;
import com.coderman.rbac.sys.converter.TimeConverter;
import com.coderman.rbac.sys.mapper.LoginLogMapper;
import com.coderman.rbac.sys.service.LoginLogService;
import com.coderman.rbac.sys.vo.LoginLogVo;
import com.coderman.rbac.sys.vo.PageVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangyukang on 2019/11/10 15:06
 */
@Service
public class LoginLogServiceImpl implements LoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    public PageVo<LoginLog> findPage(LoginLogVo loginLogVo) {
        PageHelper.startPage(loginLogVo.getPage(),loginLogVo.getLimit());
        Example example=new Example(LoginLog.class);
        Example.Criteria criteria = example.createCriteria();
        if(loginLogVo!=null){
            if(loginLogVo.getIp()!=null&&!"".equals(loginLogVo.getIp())){
                criteria.andLike("ip","%"+loginLogVo.getIp()+"%");
            }
            if(loginLogVo.getUserName()!=null&&!"".equals(loginLogVo.getUserName())){
                criteria.andLike("userName","%"+loginLogVo.getUserName()+"%");
            }
            if(loginLogVo.getLocation()!=null&&!"".equals(loginLogVo.getLocation())){
                criteria.andLike("location","%"+loginLogVo.getLocation()+"%");
            }
            if(loginLogVo.getRange()!=null&&!"".equals(loginLogVo.getRange())){
                Map<String, Object> timeListByRange = TimeConverter.getTimeListByRange(loginLogVo.getRange());
                criteria.andGreaterThanOrEqualTo("loginTime",timeListByRange.get(MyConstant.START_TIME));
                criteria.andLessThanOrEqualTo("loginTime",timeListByRange.get(MyConstant.END_TIME));
            }
        }
        example.setOrderByClause("login_time "+ MyConstant.ORDER_DESC);
        List<LoginLog> loginLogs = loginLogMapper.selectByExample(example);
        PageInfo info=new PageInfo(loginLogs);
        return new PageVo<>(info.getTotal(),info.getList());
    }

    @Override
    public void saveLog(LoginLog loginLog) {
        loginLogMapper.insertSelective(loginLog);
    }

    @Override
    public void deleteLog(LoginLogVo loginLogVo) {
        loginLogMapper.deleteByPrimaryKey(loginLogVo.getId());
    }

    @Override
    public void batchDelete(LoginLogVo loginLogVo) {
        String ids = loginLogVo.getIds();
        List<Long> idList=new ArrayList<>();
        if(ids!=null&&!"".equals(ids)){
            String[] idStrs = ids.split(",");
            for (String idStr : idStrs) {
                idList.add(Long.parseLong(idStr));
            }
        }
        Example o = new Example(LoginLog.class);
        o.createCriteria().andIn("id",idList);
        loginLogMapper.deleteByExample(o);
    }
}
