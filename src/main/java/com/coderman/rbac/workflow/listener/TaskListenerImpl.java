package com.coderman.rbac.workflow.listener;

import com.coderman.rbac.job.bean.SickPaper;
import com.coderman.rbac.job.mapper.SickPaperMapper;
import com.coderman.rbac.sys.bean.User;
import com.coderman.rbac.sys.contast.MyConstant;
import com.coderman.rbac.sys.mapper.UserMapper;
import com.coderman.rbac.sys.utils.WebUtil;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;

/**
 * Created by zhangyukang on 2019/11/30 11:01
 */
@Component
public class TaskListenerImpl implements TaskListener {


    @Override
    public void notify(DelegateTask delegateTask) {
        ServletContext servletContext = WebUtil.getRequest().getServletContext();
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
        //修改请假单状态
        SickPaperMapper sickPaperMapper = context.getBean(SickPaperMapper.class);
        SickPaper sickPaper = new SickPaper();
        String sickPaperId = (String) WebUtil.getSession().getAttribute(MyConstant.STRING_SICK_PAPER_ID);
        sickPaper.setId(Long.valueOf(sickPaperId));
        sickPaper.setStatus(MyConstant.SICK_STATUS_DEALING);
        sickPaperMapper.updateByPrimaryKeySelective(sickPaper);

        UserMapper userMapper = context.getBean(UserMapper.class);
        User user = (User) WebUtil.getSession().getAttribute(MyConstant.USER);
        Long mgrId = user.getMgrId();
        User manager = userMapper.selectByPrimaryKey(mgrId);
        delegateTask.setAssignee(manager.getUserName());
    }

}
