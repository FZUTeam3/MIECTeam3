package com.example.cjh.controller;

import com.example.cjh.vo.Result;
import com.example.cjh.vo.param.CommentsParams;
import com.example.cjh.service.CommentService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("all")
@Api(description = "评论控制器")
@RestController
@RequestMapping("comments")
public class CommentController {
    @Autowired
    CommentService commentService;

    @GetMapping("find/{articleId}")
    public Result findCommentsByArticleId(@PathVariable("articleId") int articleId) {
        return Result.success(commentService.findByArticleId(articleId));
    }


    @PostMapping("publish")
    public Result publishComment(@RequestBody CommentsParams commentsParams){
        return commentService.publishComment(commentsParams);
    }
}
