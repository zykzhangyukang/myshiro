package com.coderman.rent.sys.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by zhangyukang on 2019/11/11 18:11
 */
@Data
@NoArgsConstructor
public class DeptDTreeJson {
    private Long id;
    private String title;
    Boolean spread;
    Long parentId;

    public DeptDTreeJson(Long id, String title, Boolean spread, Long parentId) {
        this.id = id;
        this.title = title;
        this.spread = spread;
        this.parentId = parentId;
    }

}
