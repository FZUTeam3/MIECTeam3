package com.example.cjh.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.cjh.mapper.*;
import com.example.cjh.pojo.*;
import com.example.cjh.service.ArticleService;
import com.example.cjh.service.ArticleThumbService;
import com.example.cjh.vo.ArticleReportVo;
import com.example.cjh.vo.param.ReportParam;
import com.example.cjh.vo.ArticleDetailsVo;
import com.example.cjh.vo.ArticleVo;
import com.example.cjh.vo.Result;
import com.example.cjh.vo.param.ArticleParams;
import com.example.cjh.vo.param.PageParams;
import com.example.cjh.vo.param.SearchParams;
import com.example.csl.bean.FsUser;
import com.example.csl.mapper.FsUserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
@Service
public class ArticleServiceImp implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    FsUserMapper fsUserMapper;
    @Autowired
    ArticleDetailsMapper articleDetailsMapper;
    @Autowired
    ArticleThumbMapper articleThumbMapper;
    @Autowired
    ArticleReportMapper articleReportMapper;
    @Autowired
    ArticleThumbService articleThumbService;
    @Autowired
    CommentsMapper commentsMapper;

    @Override
    @Transactional
    public Result publish(ArticleParams articleParams, int userId) {
        Article article = new Article();
        article.setCategoryId(articleParams.getCategoryId());
        article.setSummary(articleParams.getSummary());
        article.setTitle(articleParams.getTittle());
        article.setUserId(userId);
        article.setViewsCount(0);
        article.setCommentsCount(0);
        article.setThumbCount(0);
        article.setCreateDate(System.currentTimeMillis());
        article.setWeight(Article.Article_Common);
        articleMapper.insert(article);
        //_________??????????????????__________
        ArticleDetails articleDetails = new ArticleDetails();
        articleDetails.setArticleId(article.getArticleId());
        articleDetails.setContentText(articleParams.getContentText());
        articleDetails.setContentImages(JSON.toJSONString(articleParams.getContentImages()));
        articleDetailsMapper.insert(articleDetails);
        return Result.success("????????????");
    }

    @Override
    public List<ArticleVo> getArticles(PageParams pageParams, int lookId) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_id", pageParams.getCategoryId());
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        IPage<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        System.out.println(articlePage.getRecords().toString());
        List<ArticleVo> articleVos = copyList(articlePage.getRecords(), lookId);
        return articleVos;
    }

    @Override
    public ArticleDetailsVo findDetailsByArticleId(int articleId) {
        QueryWrapper<ArticleDetails> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_id", articleId);
        queryWrapper.last("limit " + 1);
        ArticleDetails articleDetails = articleDetailsMapper.selectOne(queryWrapper);
        //??????+1
        Integer articleId1 = articleDetails.getArticleId();
        Article article = articleMapper.selectById(articleId1);
        Integer viewsCount = article.getViewsCount();
        UpdateWrapper<Article> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("article_id", articleId1);
        article.setViewsCount(viewsCount + 1);
        articleMapper.update(article, updateWrapper);
        ArticleDetailsVo articleDetailsVo = new ArticleDetailsVo();
        BeanUtils.copyProperties(articleDetails, articleDetailsVo);
        String[] images = JSON.parseObject(articleDetails.getContentImages(), String[].class);
        articleDetailsVo.setContentImages(images);
        return articleDetailsVo;
    }

    @Override
    public ArticleVo findArticleByArticleId(int articleId, int lookId) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_id", articleId);
        Article article = articleMapper.selectOne(queryWrapper);
        //??????????????????????????????
        LambdaUpdateWrapper<Article> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        int viewsCount = article.getViewsCount();
        article.setViewsCount(viewsCount + 1);
        lambdaUpdateWrapper.eq(Article::getArticleId, article.getArticleId());
        articleMapper.update(article, lambdaUpdateWrapper);
        return copy(article, lookId);
    }


    //______?????????vo________________
    public ArticleVo copy(Article article, int lookId) {
        ArticleVo articleVo = new ArticleVo();
        int userId = article.getUserId();
        FsUser fsUser = fsUserMapper.selectById(userId);
        String userName = fsUser.getNickname();
        BeanUtils.copyProperties(article, articleVo);
        articleVo.setUsername(userName);
        QueryWrapper<ArticleThumb> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_id", article.getArticleId());
        queryWrapper.eq("user_id", lookId);
        Boolean ifThubmb = articleThumbService.queryIfThumbInRedis(article.getArticleId(), lookId);
        ;
        if (ifThubmb == null) {
            if (articleThumbMapper.selectCount(queryWrapper) == 1) {
                articleVo.setIfIsThumb(true);
            } else {
                articleVo.setIfIsThumb(false);
            }
        } else {
            articleVo.setIfIsThumb(ifThubmb);
        }
        return articleVo;
    }

    public List<ArticleVo> copyList(List<Article> articles, int lookid) {
        List<ArticleVo> articleVos = new ArrayList<>();
        for (Article article : articles) {
            articleVos.add(copy(article, lookid));
        }
        return articleVos;
    }

    @Override
    public Result search(SearchParams searchParams, int lookId) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_id", searchParams.getCategoryId());
        queryWrapper.like("title", searchParams.getTitle());
        Page<Article> page = new Page<>(searchParams.getPage(), searchParams.getPageSize());
        IPage<Article> articleIPage = articleMapper.selectPage(page, queryWrapper);
        return Result.success(copyList(articleIPage.getRecords(), lookId));
    }

    @Override
    public Result report(ReportParam reportParam, int userId) {
        QueryWrapper<ArticleReport> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("article_id", reportParam.getArticleId());
        ArticleReport articleReport1 = articleReportMapper.selectOne(queryWrapper);
        if (articleReport1 != null) {
            return Result.fail(404, "??????????????????????????????");
        }
        ArticleReport articleReport = new ArticleReport();
        articleReport.setUserId(userId);
        articleReport.setArticleId(reportParam.getArticleId());
        articleReport.setReason(reportParam.getReason());
        articleReportMapper.insert(articleReport);
        return Result.success("????????????");
    }

    @Override
    public Result reportQuery() {
        List<ArticleReport> articleReport = articleReportMapper.selectList(null);
        if (articleReport.isEmpty()) {
            return Result.fail(404, "No reported article now");
        }
        List<ArticleReportVo> articleReportVos = new ArrayList<>();
        for (ArticleReport rpt : articleReport) {
            ArticleReportVo avo = new ArticleReportVo();
            avo.setArticleId(rpt.getArticleId());
            avo.setSummary(articleMapper.selectById(rpt.getArticleId()).getSummary());
            avo.setTitle(articleMapper.selectById(rpt.getArticleId()).getSummary());
            avo.setReason(rpt.getReason());
            avo.setReportUser(fsUserMapper.selectById(rpt.getUserId()).getNickname());
            articleReportVos.add(avo);
        }
        return Result.success(articleReportVos);
    }

    @Override
    public Result reportDelete(int articleId) {
        //???????????????
        QueryWrapper<ArticleReport> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_id", articleId);
        int i = articleReportMapper.delete(queryWrapper);
        if (i == 0) {
            return Result.fail(404, "Fail to delete, maybe this article has been deleted");
        }
        //??????????????????
        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
        articleQueryWrapper.eq("article_id", articleId);
        articleMapper.delete(articleQueryWrapper);
        //??????????????????
        QueryWrapper<ArticleDetails> articleDetailsQueryWrapper = new QueryWrapper<>();
        articleDetailsQueryWrapper.eq("article_id", articleId);
        articleDetailsMapper.delete(articleDetailsQueryWrapper);
        //??????????????????
        QueryWrapper<ArticleThumb> articleThumbQueryWrapper = new QueryWrapper<>();
        articleThumbQueryWrapper.eq("article_id", articleId);
        articleThumbMapper.delete(articleThumbQueryWrapper);
        //??????????????????
        QueryWrapper<Comments> commentsQueryWrapper = new QueryWrapper<>();
        commentsQueryWrapper.eq("article_id", articleId);
        commentsMapper.delete(commentsQueryWrapper);
        return Result.success("Succeed to delete this article");
    }


}
