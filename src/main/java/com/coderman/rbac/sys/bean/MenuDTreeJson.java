package com.coderman.rent.sys.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by zhangyukang on 2019/11/11 18:11
 */
@Data
@NoArgsConstructor
public class MenuDTreeJson {
    private Long id;
    private String title;
    Boolean spread;
    Long parentId;
    String checkArr;

    public MenuDTreeJson(Long id, String title, Boolean spread, Long parentId, String checkArr) {
        this.id = id;
        this.title = title;
        this.spread = spread;
        this.parentId = parentId;
        this.checkArr = checkArr;
    }

}
