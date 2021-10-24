package com.example.cjh.controller;

import com.example.cjh.vo.Result;
import com.example.cjh.vo.param.CommentsParams;
import com.example.cjh.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("all")
@Api(description = "评论控制器")
@RestController
@RequestMapping("comments")
public class CommentController {
    @Autowired
    CommentService commentService;
    @ApiOperation(value="根据文章id查它的所有评论")
    @GetMapping("find/{articleId}")
    public Result findCommentsByArticleId(@PathVariable("articleId") int articleId) {
        return Result.success(commentService.findByArticleId(articleId));
    }

    @ApiOperation(value="发布评论")
    @PostMapping("publish")
    public Result publishComment(@RequestBody CommentsParams commentsParams){
        return commentService.publishComment(commentsParams);
    }
}
