package com.example.cjh.service;

import com.example.cjh.vo.ArticleDetailsVo;
import com.example.cjh.vo.ArticleVo;
import com.example.cjh.vo.Result;
import com.example.cjh.vo.param.ArticleParams;
import com.example.cjh.vo.param.PageParams;
import com.example.cjh.vo.param.ReportParam;
import com.example.cjh.vo.param.SearchParams;

import java.util.List;

public interface ArticleService {


    Result publish(ArticleParams articleParams,int userId);

    List<ArticleVo> getArticles(PageParams pageParams,int lookId);

    ArticleDetailsVo findDetailsByArticleId(int articleId);

    ArticleVo findArticleByArticleId(int articleId,int lookId);

    Result search(SearchParams searchParams,int lookID);

    Result report(ReportParam reportParam, int userId);

    Result reportQuery();

    Result reportDelete(int articleId);
}
