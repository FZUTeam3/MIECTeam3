package com.example.cjh.service;

import com.example.cjh.vo.ArticleDetailsVo;
import com.example.cjh.vo.ArticleVo;
import com.example.cjh.vo.Result;
import com.example.cjh.vo.param.ArticleParams;
import com.example.cjh.vo.param.PageParams;
import com.example.cjh.vo.param.SearchParams;

import java.util.List;

public interface ArticleService {


    Result publish(ArticleParams articleParams);

    List<ArticleVo> getArticles(PageParams pageParams);

    ArticleDetailsVo findDetailsByArticleId(int articleId);

    ArticleVo findArticleByArticleId(int articleId);

    Result search(SearchParams searchParams);
}
