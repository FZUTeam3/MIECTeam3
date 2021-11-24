package com.example.cjh.service.impl;

import com.example.cjh.mapper.ArticleThumbMapper;
import com.example.cjh.service.ArticleThumbService;
import com.example.cjh.uitls.ThumbStatusEnum;
import com.example.cjh.uitls.ThumbUtils;
import com.example.cjh.vo.Result;
import com.example.csl.bean.FsUser;
import com.example.csl.mapper.FsUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

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
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String identityId = (String) request.getAttribute("identityId");
        int userId = Integer.parseInt(identityId);
        String key = ThumbUtils.getThumbKey(articleId, Math.toIntExact(userId));
        redisTemplate.opsForHash().put(ThumbUtils.Key_Thumb, key, ThumbStatusEnum.LIKE.getCode().toString());
        return Result.success("成功点赞");
    }


    @Override
    public Result cancelThumb2Redis(int articleId) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String identityId = (String) request.getAttribute("identityId");
        int userId = Integer.parseInt(identityId);
        String key = ThumbUtils.getThumbKey(articleId, Math.toIntExact(userId));
//        redisTemplate.opsForHash().delete(ThumbUtils.Key_Thumb, key);
        redisTemplate.opsForHash().put(ThumbUtils.Key_Thumb, key, ThumbStatusEnum.UNLIKE.getCode().toString());
        return Result.success("成功取消点赞");
    }

    @Override
    public Boolean queryIfThumbInRedis(int articleId, int UserId) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String identityId = (String) request.getAttribute("identityId");
        int userId = Integer.parseInt(identityId);
        String key = ThumbUtils.getThumbKey(articleId, Math.toIntExact(userId));
        Object obj = redisTemplate.opsForHash().get(ThumbUtils.Key_Thumb, key);
        if (obj == null) {
            return null;
        } else {
            if (obj.toString().equals("1")) {
                System.out.println(obj.toString());
                return true;
            } else return false;
        }
    }


}
