package com.example.cjh.controller;

import com.example.cjh.vo.ArticleVo;
import com.example.cjh.vo.Result;
import com.example.cjh.vo.param.ArticleParams;
import com.example.cjh.vo.param.PageParams;
import com.example.cjh.vo.param.ReportParam;
import com.example.cjh.vo.param.SearchParams;
import com.example.cjh.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@SuppressWarnings("all")
@Api(description = "查询文章控制器")
@RestController
@RequestMapping("articles")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @ApiOperation(value = "获取当前分类的所有文章概述")
    @PostMapping("listAll")
    public Result getArticles(@RequestBody PageParams pageParams) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String identityId = (String) request.getAttribute("identityId");
        int lookId=Integer.parseInt(identityId);
        List<ArticleVo> articleVos = articleService.getArticles(pageParams, lookId);
        return Result.success(articleVos);
    }
    @ApiOperation(value = "获取文章正文")
    @GetMapping("findArticleDetails/{articleId}")
    public Result findDetailsByArticleId(@PathVariable("articleId") int articleId) {
        return Result.success(articleService.findDetailsByArticleId(articleId));
    }

    @ApiOperation(value = "根据id查文章")
    @GetMapping("findArticle/{articleId}")
    public Result findArticleByArticleId(@PathVariable("articleId") int articleId) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String identityId = (String) request.getAttribute("identityId");
        int lookId=Integer.parseInt(identityId);
        return Result.success(articleService.findArticleByArticleId(articleId, lookId));
    }

    @ApiOperation(value = "发布文章")
    @PostMapping("publish")
    public Result publishArticle(@RequestBody ArticleParams articleParams) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String identityId = (String) request.getAttribute("identityId");
        int userId = Integer.parseInt(identityId);
        return articleService.publish(articleParams, userId);
    }

    @ApiOperation(value = "搜索功能")
    @PostMapping("search")
    public Result searchByKeywords(@RequestBody SearchParams searchParams) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String identityId = (String) request.getAttribute("identityId");
        int lookId=Integer.parseInt(identityId);
        return articleService.search(searchParams, lookId);
    }

    @ApiOperation(value = "举报文章" )
    @PostMapping("report")
    public Result reportThisArticle(@RequestBody ReportParam reportParam){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String identityId = (String) request.getAttribute("identityId");
        int userId=Integer.parseInt(identityId);
        return articleService.report(reportParam,userId);
    }

    @ApiOperation(value = "查询哪些文章被举报了")
    @GetMapping("manage/report/query")
    public Result reportQuery(){
        return articleService.reportQuery();
    }

    @ApiOperation(value = "删除违规文章")
    @GetMapping("manage/report/delete/{articleId}")
    public Result reportDelete(@PathVariable("articleId") int articleId){
        return articleService.reportDelete(articleId);
    }

}
