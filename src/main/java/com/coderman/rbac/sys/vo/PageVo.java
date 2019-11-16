package com.coderman.rbac.sys.vo;

import lombok.Data;

import java.util.List;

/**
 * Created by zhangyukang on 2019/11/10 15:10
 */
@Data
public class PageVo<T> {
    private Integer code=0;
    private long count;
    private List<T> data;


    public PageVo(long count, List<T> data) {
        this.count = count;
        this.data = data;
    }

    public PageVo(List<T> data) {
        this.count = 0;
        this.data = data;
    }
}
