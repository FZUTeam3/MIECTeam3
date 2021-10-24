package com.example.cjh.vo.param;

import lombok.Data;

@Data
public class CommentsParams {

    /** 评论内容 */
    private String content ;

    /** 评论的文章  */
    private String articleId ;
}
