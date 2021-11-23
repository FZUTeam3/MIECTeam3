package com.example.cjh.service;

import com.example.cjh.vo.Result;

public interface ArticleThumbService {

    Result addThumb2Redis(int articleId);

    Result cancelThumb2Redis(int articleId);

    Boolean queryIfThumbInRedis(int articleId,int UserId);
}
