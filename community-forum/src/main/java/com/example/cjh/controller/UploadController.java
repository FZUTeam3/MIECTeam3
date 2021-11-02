package com.example.cjh.controller;

import com.example.cjh.uitls.QiniuUtils;
import com.example.cjh.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.lang3.StringUtils;
import java.util.UUID;

@RestController
public class UploadController {
    @Autowired
    private QiniuUtils qiniuUtils;
    @PostMapping("upload")
    public Result upload(@RequestParam("image") MultipartFile file){
        String fileName = UUID.randomUUID() + "." + StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
        boolean upload = qiniuUtils.upload(file, fileName);
        if (upload){
            return Result.success(QiniuUtils.url + fileName);
        }
        return Result.fail(404,"上传失败");
    }

}
