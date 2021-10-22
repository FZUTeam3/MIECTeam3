package com.example.cjh.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class CommentVo {
    /** 评论id */
    @TableId(type = IdType.AUTO)
    private Integer commentId ;
    /** 评论内容 */
    private String content ;
    /** 评论者名字 */
    private String userName ;
    /** 评论时间 */
    private String createDate ;

}
