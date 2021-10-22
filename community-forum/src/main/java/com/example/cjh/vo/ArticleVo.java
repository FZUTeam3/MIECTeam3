package com.example.cjh.vo;

import lombok.Data;

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
}
