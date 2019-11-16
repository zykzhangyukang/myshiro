package com.coderman.rbac.sys.test;

import com.alibaba.fastjson.JSON;
import com.coderman.rbac.sys.dto.HotMovieDTO;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by zhangyukang on 2019/11/12 22:04
 */
public class TestOKHttp {
    @Test
    public void test(){
        String url = "https://api-m.mtime.cn/Showtime/LocationMovies.api?locationId=328";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            String string=response.body().string();
            HotMovieDTO hotMovieDTO = JSON.parseObject(string, HotMovieDTO.class);
            System.out.println(hotMovieDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
