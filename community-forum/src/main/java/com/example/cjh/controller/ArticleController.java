package com.example.cjh.controller;

import com.example.cjh.vo.ArticleVo;
import com.example.cjh.vo.Result;
import com.example.cjh.vo.param.ArticleParams;
import com.example.cjh.vo.param.PageParams;
import com.example.cjh.vo.param.SearchParams;
import com.example.cjh.service.ArticleService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("all")
@Api(description = "查询文章控制器")
@RestController
@RequestMapping("articles")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @PostMapping("listAll")
    public Result getArticles(@RequestBody PageParams pageParams) {
        List<ArticleVo> articleVos = articleService.getArticles(pageParams);
        return Result.success(articleVos);
    }

    @GetMapping("findArticleDetails/{articleId}")
    public Result findDetailsByArticleId(@PathVariable("articleId") int articleId) {
        return Result.success(articleService.findDetailsByArticleId(articleId));
    }

    @GetMapping("findArticle/{articleId}")
    public Result findArticleByArticleId(@PathVariable("articleId") int articleId) {
        return Result.success(articleService.findArticleByArticleId(articleId));
    }

    @PostMapping("publish")
    public Result publishArticle(@RequestBody ArticleParams articleParams) {
        return articleService.publish(articleParams);
    }

    @PostMapping("search")
    public Result searchByKeywords(@RequestBody SearchParams searchParams) {
        return articleService.search(searchParams);
    }

}
