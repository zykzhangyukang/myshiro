package com.coderman.rent.sys.service.impl;

import ch.qos.logback.core.LogbackException;
import com.coderman.rent.sys.bean.ActiveUser;
import com.coderman.rent.sys.bean.Log;
import com.coderman.rent.sys.bean.LoginLog;
import com.coderman.rent.sys.bean.User;
import com.coderman.rent.sys.contast.MyConstant;
import com.coderman.rent.sys.converter.TimeConverter;
import com.coderman.rent.sys.mapper.LogMapper;
import com.coderman.rent.sys.service.LogService;
import com.coderman.rent.sys.utils.AddressUtil;
import com.coderman.rent.sys.utils.IPUtil;
import com.coderman.rent.sys.vo.LogVo;
import com.coderman.rent.sys.vo.PageVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangyukang on 2019/11/15 17:26
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;

    @Override
    public PageVo<Log> findPage(LogVo logVo) {
        PageHelper.startPage(logVo.getPage(),logVo.getLimit());
        Example example = new Example(Log.class);
        Example.Criteria criteria = example.createCriteria();
        if(logVo!=null){
            if(logVo.getUserName()!=null&&!"".equals(logVo.getUserName())){
                criteria.andLike("userName","%"+logVo.getUserName()+"%");
            }
            if(logVo.getOperation()!=null&&!"".equals(logVo.getOperation())){
                criteria.andLike("operation","%"+logVo.getOperation()+"%");
            }
            if(logVo.getLocation()!=null&&!"".equals(logVo.getLocation())){
                criteria.andLike("location","%"+logVo.getLocation()+"%");
            }
            if(logVo.getIp()!=null&&!"".equals(logVo.getIp())){
                criteria.andLike("ip","%"+logVo.getIp()+"%");
            }
            if(logVo.getRange()!=null&&!"".equals(logVo.getRange())){
                Map<String, Object> timeListByRange = TimeConverter.getTimeListByRange(logVo.getRange());
                criteria.andGreaterThanOrEqualTo("createTime",timeListByRange.get(MyConstant.START_TIME));
                criteria.andLessThanOrEqualTo("createTime",timeListByRange.get(MyConstant.END_TIME));
            }
        }
        List<Log> logs = logMapper.selectByExample(example);
        PageInfo info=new PageInfo(logs);
        return new PageVo<>(info.getTotal(),info.getList());
    }

    @Override
    public void delete(LogVo logVo) {
        logMapper.deleteByPrimaryKey(logVo.getId());
    }

    @Override
    public void batchDelete(LogVo logVo) {
        String ids = logVo.getIds();
        List<Long> idList=new ArrayList<>();
        if(ids!=null&&!"".equals(ids)){
            String[] idStrs = ids.split(",");
            for (String idStr : idStrs) {
                idList.add(Long.parseLong(idStr));
            }
        }
        Example o = new Example(LoginLog.class);
        o.createCriteria().andIn("id",idList);
        logMapper.deleteByExample(o);
    }


    @Transactional
    @Override
    public void saveLog(ProceedingJoinPoint point, Method method, HttpServletRequest request, String operation, long start) {
        // 设置 IP地址
        Log systemLog = new Log();
        String ip = IPUtil.getIpAddr(request);
        systemLog.setIp(ip);
        // 设置操作用户
       ActiveUser activeUser= (ActiveUser) SecurityUtils.getSubject().getPrincipal();
       User user=activeUser.getUser();
        if (user != null)
            systemLog.setUserName(user.getUserName());
        // 设置耗时
        systemLog.setTime(System.currentTimeMillis() - start);
        // 设置操作描述
        systemLog.setOperation(operation);
        // 请求的类名
        String className = point.getTarget().getClass().getName();
        // 请求的方法名
        String methodName = method.getName();
        systemLog.setMethod(className + "." + methodName + "()");
        // 请求的方法参数值
        Object[] args = point.getArgs();
        // 请求的方法参数名称
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        if (args != null && paramNames != null) {
            List<String> roles = activeUser.getRoles();
            List<String> permissions = activeUser.getPermissions();
            StringBuilder role=new StringBuilder();
            StringBuilder permission=new StringBuilder();
            if(CollectionUtils.isEmpty(roles)){
                role.append("[草鸡管理员]");
            }else {
                role=new StringBuilder(activeUser.getRoles().toString());
            }
            if(CollectionUtils.isEmpty(permissions)){
                permission.append("[任何权限]");
            }else {
                role=new StringBuilder(activeUser.getRoles().toString());
            }
            systemLog.setParams("role="+role+",permission="+permission+",time=["+new Date()+"]");
        }
        systemLog.setCreateTime(new Date());
        systemLog.setLocation(AddressUtil.getCityInfo(IPUtil.getIpAddr(request)));
        logMapper.insert(systemLog);
    }



}
