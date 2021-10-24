package com.example.cjh.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("fs_article")
public class Article {
    public static final int Article_TOP = 1;
    public static final int Article_Common = 0;
    @TableId(type = IdType.AUTO)
    private Integer articleId ;
    /** 标题 */
    private String title ;
    /** 作者id */
    private Integer userId ;
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
    /** 创建时间 */
    private Long createDate;
    /** 点赞数 */
    private Integer thumbCount;


}
