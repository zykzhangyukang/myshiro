package com.coderman.rbac.base.controller;

import com.coderman.rbac.base.utils.OssUploadImgProvider;
import com.coderman.rbac.base.vo.ResultFileVo;
import com.coderman.rbac.sys.enums.ResultEnum;
import com.coderman.rbac.sys.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 图片上传接口
 * Created by zhangyukang on 2019/11/23 19:34
 */
@RestController
@Slf4j
@RequestMapping("/file")
public class FileController {


    @Autowired
    private OssUploadImgProvider ossUploadImgProvider;

    /**
     * 留言板图片上传
     * @return
     */
    @PostMapping("/uploadImage")
    public ResultFileVo uploadImage(MultipartFile file){
        String src;
        try {
           src= ossUploadImgProvider.UploadFile(file.getInputStream(), file.getContentType(), file.getOriginalFilename());
            Map map=new HashMap();
            map.put("src",src);
            return ResultFileVo.SUCCESS(map);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("【上传失败】");
            return ResultFileVo.ERROR();
        }
    }

}
