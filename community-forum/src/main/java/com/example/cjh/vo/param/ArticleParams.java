package com.example.cjh.vo.param;

import lombok.Data;

@Data
public class ArticleParams {

    /** 标题 */
    private String tittle ;
    /** 分类id */
    private Integer categoryId ;
    /** 概述 */
    private String summary;
    /** 文章具体内容 */
    private String contentText;
    /** 文章附带的图片 */
    private String contentImages;
}
