package com.example.cjh.service;

import com.example.cjh.vo.CommentVo;
import com.example.cjh.vo.Result;
import com.example.cjh.vo.param.CommentsParams;

import java.util.List;

public interface CommentService {
   List<CommentVo> findByArticleId(int articleId);
   Result publishComment(CommentsParams commentsParams);
}
