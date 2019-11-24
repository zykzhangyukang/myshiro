package com.coderman.rbac.base.service.impl;

import com.coderman.rbac.base.bean.Message;
import com.coderman.rbac.base.mapper.MessageMapper;
import com.coderman.rbac.base.service.MessageService;
import com.coderman.rbac.base.vo.MessageVo;
import com.coderman.rbac.sys.bean.User;
import com.coderman.rbac.sys.contast.MyConstant;
import com.coderman.rbac.sys.converter.TimeConverter;
import com.coderman.rbac.sys.utils.AddressUtil;
import com.coderman.rbac.sys.utils.IPUtil;
import com.coderman.rbac.sys.utils.WebUtil;
import com.coderman.rbac.sys.vo.PageVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangyukang on 2019/11/23 17:34
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public PageVo<Message> findPage(MessageVo messageVo) {
        PageHelper.startPage(messageVo.getPage(),messageVo.getLimit());
        Example o = new Example(Message.class);
        Example.Criteria criteria = o.createCriteria();
        o.setOrderByClause("create_time desc");
        if(messageVo!=null){
            if(messageVo.getTitle()!=null&&!"".equals(messageVo.getTitle())){
                criteria.andLike("title","%"+messageVo.getTitle()+"%");
            }
            if(messageVo.getId()!=null&&!"".equals(messageVo.getId())){
                criteria.andEqualTo("id",messageVo.getId());
            }
            if(messageVo.getRange()!=null&&!"".equals(messageVo.getRange())){
                Map<String, Object> timeListByRange = TimeConverter.getTimeListByRange(messageVo.getRange());
                criteria.andGreaterThanOrEqualTo("createTime",timeListByRange.get(MyConstant.START_TIME));
                criteria.andLessThanOrEqualTo("createTime",timeListByRange.get(MyConstant.END_TIME));
            }
        }
        List<Message> messages = messageMapper.selectByExample(o);
        PageInfo info=new PageInfo(messages);
        return new PageVo<>(info.getTotal(),info.getList());
    }

    @Override
    public void publish(MessageVo messageVo) {
        HttpServletRequest request=WebUtil.getRequest();
        Message message = new Message();
        BeanUtils.copyProperties(messageVo,message);
        User user = (User) WebUtil.getSession().getAttribute(MyConstant.USER);
        message.setCreateTime(new Date());
        message.setCreator(user.getUserName());
        message.setIp(IPUtil.getIpAddr(request));
        message.setLocation(AddressUtil.getCityInfo(IPUtil.getIpAddr(request)));
        messageMapper.insertSelective(message);
    }

    @Override
    public void delete(MessageVo messageVo) {
        messageMapper.deleteByPrimaryKey(messageVo.getId());
    }

    @Override
    public void update(MessageVo messageVo) {
        Message t = new Message();
        BeanUtils.copyProperties(messageVo,t);
        messageMapper.updateByPrimaryKeySelective(t);
    }
}
