package com.coderman.rbac.job.service.impl;

import com.coderman.rbac.job.bean.SickPaper;
import com.coderman.rbac.job.mapper.SickPaperMapper;
import com.coderman.rbac.job.service.SickPaperService;
import com.coderman.rbac.job.vo.SickPaperVo;
import com.coderman.rbac.sys.bean.User;
import com.coderman.rbac.sys.contast.MyConstant;
import com.coderman.rbac.sys.converter.TimeConverter;
import com.coderman.rbac.sys.utils.WebUtil;
import com.coderman.rbac.sys.vo.PageVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangyukang on 2019/11/27 16:54
 */
@Service
public class SickPaperServiceImpl implements SickPaperService {

    @Autowired
    private SickPaperMapper sickPaperMapper;

    @Override
    public PageVo<SickPaper> findPage(SickPaperVo sickPaperVo) {
        PageHelper.startPage(sickPaperVo.getPage(),sickPaperVo.getLimit());
        Example example = new Example(SickPaper.class);
        Example.Criteria criteria = example.createCriteria();
        User user = (User) WebUtil.getSession().getAttribute(MyConstant.USER);
        if(sickPaperVo!=null){
            if(sickPaperVo.getTitle()!=null&&!"".equals(sickPaperVo.getTitle())){
                criteria.andLike("title","%"+sickPaperVo.getTitle()+"%");
            }
            if(sickPaperVo.getId()!=null&&!"".equals(sickPaperVo.getId())){
                criteria.andEqualTo("id",sickPaperVo.getId());
            }
            if(sickPaperVo.getRange()!=null&&!"".equals(sickPaperVo.getRange())){
                Map<String, Object> timeListByRange = TimeConverter.getTimeListByRange(sickPaperVo.getRange());
                criteria.andGreaterThanOrEqualTo("start",timeListByRange.get(MyConstant.START_TIME));
                criteria.andLessThanOrEqualTo("end",timeListByRange.get(MyConstant.END_TIME));
            }
        }
        criteria.andEqualTo("userId",user.getId());
        List<SickPaper> sickPapers = sickPaperMapper.selectByExample(example);
        PageInfo pageInfo=new PageInfo(sickPapers);
        return new PageVo<>(pageInfo.getTotal(),pageInfo.getList());
    }

    private static  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void add(SickPaperVo sickPaperVo) throws ParseException {
        SickPaper sickPaper = new SickPaper();
        BeanUtils.copyProperties(sickPaperVo,sickPaper);
        sickPaper.setCreateTime(new Date());
        sickPaper.setModifiedTime(new Date());
        User user = (User) WebUtil.getSession().getAttribute(MyConstant.USER);
        sickPaper.setUserId(user.getId());
        Map<String, Object> timeListByRange = TimeConverter.getTimeListByRange(sickPaperVo.getRange());
        Date start = sdf.parse((String) timeListByRange.get(MyConstant.START_TIME));
        Date end = sdf.parse((String) timeListByRange.get(MyConstant.END_TIME));
        sickPaper.setStart(start);
        sickPaper.setEnd(end);
        sickPaper.setDays(daysBetween(start,end));
        sickPaper.setStatus(0);//默认未提交
        sickPaperMapper.insertSelective(sickPaper);
    }

    /**
     * 计算两个日期之间相差的天数
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate,Date bdate) throws ParseException
    {
        smdate=sdf.parse(sdf.format(smdate));
        bdate=sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));
    }


    @Override
    public void delete(SickPaperVo sickPaperVo) {
        sickPaperMapper.deleteByPrimaryKey(sickPaperVo.getId());
    }

    @Override
    public void update(SickPaperVo sickPaperVo) throws ParseException {
        SickPaper sickPaper = new SickPaper();
        BeanUtils.copyProperties(sickPaperVo,sickPaper);
        sickPaper.setModifiedTime(new Date());
        Map<String, Object> timeListByRange = TimeConverter.getTimeListByRange(sickPaperVo.getRange());
        Date start = sdf.parse((String) timeListByRange.get(MyConstant.START_TIME));
        Date end = sdf.parse((String) timeListByRange.get(MyConstant.END_TIME));
        sickPaper.setDays(daysBetween(start,end));
        sickPaper.setStart(start);
        sickPaper.setEnd(end);
        sickPaper.setCreateTime(new Date());
        sickPaper.setDays(daysBetween(start,end));
        sickPaperMapper.updateByPrimaryKey(sickPaper);
    }
}
