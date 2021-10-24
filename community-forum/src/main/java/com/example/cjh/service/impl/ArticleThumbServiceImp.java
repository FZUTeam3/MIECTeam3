package com.example.cjh.service.impl;

import com.example.cjh.mapper.ArticleThumbMapper;
import com.example.cjh.service.ArticleThumbService;
import com.example.cjh.uitls.ThumbStatusEnum;
import com.example.cjh.uitls.ThumbUtils;
import com.example.cjh.vo.Result;
import com.example.cjh.uitls.UserThreadLocal;
import com.example.csl.bean.FsUser;
import com.example.csl.mapper.FsUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@SuppressWarnings("all")
@Service
public class ArticleThumbServiceImp implements ArticleThumbService {
    @Autowired
    private FsUserMapper fsUserMapper1;
    @Autowired
    private ArticleThumbMapper articleThumbMapper;
    @Autowired
    private RedisTemplate redisTemplate;



    @Override
    public Result addThumb2Redis(int articleId) {
        FsUser fsUser = UserThreadLocal.get();
        System.out.println(fsUser.toString());
        String key = ThumbUtils.getThumbKey(articleId, Math.toIntExact(fsUser.getUserId()));
        redisTemplate.opsForHash().put(ThumbUtils.Key_Thumb, key, ThumbStatusEnum.LIKE.getCode().toString());
        return Result.success("成功点赞");
    }


    @Override
    public Result cancelThumb2Redis(int articleId) {
        FsUser fsUser = UserThreadLocal.get();
        String key = ThumbUtils.getThumbKey(articleId, Math.toIntExact(fsUser.getUserId()));
        redisTemplate.opsForHash().delete(ThumbUtils.Key_Thumb, key);
        return Result.success("成功取消点赞");
    }


}
