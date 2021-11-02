package com.example.cjh.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
@ApiModel(value = "文章概括",description = "包含文章的标题简介等信息")
@Data
public class ArticleVo {
    private Integer articleId ;
    /** 标题 */
    private String title ;
    /** 作者名字 */
    private String username ;
    /** 评论数量 */
    private Integer commentsCount ;
    /** 阅读数量 */
    private Integer viewsCount ;
    /** 权重 是否置顶 */
    private Integer weight ;
    /** 分类id */
    private Integer categoryId ;
    /** 概述 */
    private String summary;
    /** 点赞数 */
    private Integer thumbCount;
    /** 当前用户是否点赞了 */
    private boolean ifIsThumb;
    /** 创建时间 */
    private Long createDate;
}
