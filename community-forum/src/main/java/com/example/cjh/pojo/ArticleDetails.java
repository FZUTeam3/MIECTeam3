package com.example.cjh.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("fs_article_details")
public class ArticleDetails {
    /** 文章详细主键 */
    @TableId(type = IdType.AUTO)
    private Integer bodyId ;
    /** 文章文字内容 */
    private String contentText ;
    /** 文章id */
    private Integer articleId ;
}
