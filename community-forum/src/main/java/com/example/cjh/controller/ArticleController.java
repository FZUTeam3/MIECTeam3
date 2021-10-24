package com.example.cjh.controller;

import com.example.cjh.vo.ArticleVo;
import com.example.cjh.vo.Result;
import com.example.cjh.vo.param.ArticleParams;
import com.example.cjh.vo.param.PageParams;
import com.example.cjh.vo.param.SearchParams;
import com.example.cjh.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value="获取当前分类的所有文章概述")
    @PostMapping("listAll")
    public Result getArticles(@RequestBody PageParams pageParams) {
        List<ArticleVo> articleVos = articleService.getArticles(pageParams);
        return Result.success(articleVos);
    }
    @ApiOperation(value="获取文章正文")
    @GetMapping("findArticleDetails/{articleId}")
    public Result findDetailsByArticleId(@PathVariable("articleId") int articleId) {
        return Result.success(articleService.findDetailsByArticleId(articleId));
    }
    @ApiOperation(value="根据id查文章")
    @GetMapping("findArticle/{articleId}")
    public Result findArticleByArticleId(@PathVariable("articleId") int articleId) {
        return Result.success(articleService.findArticleByArticleId(articleId));
    }
    @ApiOperation(value="发布文章")
    @PostMapping("publish")
    public Result publishArticle(@RequestBody ArticleParams articleParams) {
        return articleService.publish(articleParams);
    }
    @ApiOperation(value="搜索功能")
    @PostMapping("search")
    public Result searchByKeywords(@RequestBody SearchParams searchParams) {
        return articleService.search(searchParams);
    }

}
