package com.example.cjh.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("fs_article_thumb")
public class ArticleThumb {
    /** 文章详细主键 */
    @TableId(type = IdType.AUTO)
    private Integer id ;
    /** 文章文字内容 */
    private Integer articleId ;
    /** 文章id */
    private Integer user_id ;
}
