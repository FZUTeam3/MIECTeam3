package com.example.cjh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.cjh.mapper.ArticleMapper;
import com.example.cjh.mapper.CommentsMapper;
import com.example.cjh.pojo.Article;
import com.example.cjh.pojo.Comments;
import com.example.cjh.service.CommentService;
import com.example.cjh.uitls.UserThreadLocal;
import com.example.cjh.vo.CommentVo;
import com.example.cjh.vo.Result;
import com.example.cjh.vo.param.CommentsParams;
import com.example.csl.bean.FsUser;
import com.example.csl.mapper.FsUserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentsMapper commentsMapper;
    @Autowired
    FsUserMapper fsUserMapper;
    @Autowired
    ArticleMapper articleMapper;

    @Override
    public List<CommentVo> findByArticleId(int articleId) {
        QueryWrapper<Comments> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_id", articleId);
        List<Comments> comments = commentsMapper.selectList(queryWrapper);
        return copyList(comments);
    }

    @Override
    @Transactional
    public Result publishComment(CommentsParams commentsParams) {
        Comments comments = new Comments();
        //获取当前id
        FsUser fsUser = UserThreadLocal.get();
        int userId = Math.toIntExact(fsUser.getUserId());
        BeanUtils.copyProperties(commentsParams, comments);
        comments.setUserId(userId);
        comments.setCreateDate(System.currentTimeMillis());
        commentsMapper.insert(comments);
        //更新评论数
        QueryWrapper<Comments> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_id", commentsParams.getArticleId());
        int count = commentsMapper.selectCount(queryWrapper);
        LambdaUpdateWrapper<Article> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        Article article = new Article();
        article.setCommentsCount(count);
        lambdaUpdateWrapper.eq(Article::getArticleId,commentsParams.getArticleId());
        articleMapper.update(article,lambdaUpdateWrapper);

        return Result.success("Success");
    }

    public CommentVo copy(Comments comments) {
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comments, commentVo);
        String username = fsUserMapper.selectById(comments.getUserId()).getNickname();
        commentVo.setUserName(username);
        return commentVo;
    }

    public List<CommentVo> copyList(List<Comments> commentsList) {
        List<CommentVo> commentVoList = new ArrayList<>();
        for (Comments comments : commentsList) {
            commentVoList.add(copy(comments));
        }
        return commentVoList;
    }
}
