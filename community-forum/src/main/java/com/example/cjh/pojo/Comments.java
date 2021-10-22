package com.example.cjh.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("fs_comments")
public class Comments {
    /** 评论id */
    @TableId(type = IdType.AUTO)
    private Integer commentId ;
    /** 评论内容 */
    private String content ;
    /** 评论者id */
    private Integer userId ;
    /** 评论时间 */
    private Long createDate ;
    /**  */
    private String articleId ;
}
