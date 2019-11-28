package com.coderman.rbac.job.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * 请假单
 * Created by zhangyukang on 2019/11/27 16:45
 */
@Data
@Table(name = "job_sick_paper")
public class SickPaper {

    /**主键**/
    @Id
    private Long id;

    /**请假单标题**/
    @NotEmpty
    private String title;

    /**请假单内容**/
    @NotEmpty
    private String content;

    /**请假总天数**/
    private Integer days;

    /**请假开始日期**/
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date start;

    /**请假截止日期**/
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date end;

    /**请假人ID**/
    private Long userId;

    /**请假审批状态：0：未提交，1：已提交，2：审批中，3：通过，4：未通过，5：放弃**/
    private Integer status;

    private Date createTime;

    private Date modifiedTime;
}
