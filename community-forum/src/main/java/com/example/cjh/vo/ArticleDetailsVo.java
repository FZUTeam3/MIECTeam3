package com.example.cjh.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
@ApiModel(value = "文章正文",description = "文章的正文 每个对应一个文章概括")
@Data
public class ArticleDetailsVo {
    private String contentText;
}
