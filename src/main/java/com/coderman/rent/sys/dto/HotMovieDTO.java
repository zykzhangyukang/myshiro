package com.coderman.rent.sys.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by zhangyukang on 2019/11/12 22:13
 */
@Data
public class HotMovieDTO {
   private String bImg;
    private String date;
    private Boolean hasPromo;
     private Integer lid;
     private List<Movie> ms;
}
