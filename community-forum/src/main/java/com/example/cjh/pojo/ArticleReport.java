package com.example.cjh.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("fs_article_report")
public class ArticleReport {
    /** 主键 */
    @TableId(type = IdType.AUTO)
    private Integer reportId ;
    /** 被举报的文章id */
    private Integer articleId ;
    /** 举报人id */
    private Integer user_id ;
    /** 举报的原因*/
    private String reason;

}
