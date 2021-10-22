package com.example.cjh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.cjh.mapper.ArticleDetailsMapper;
import com.example.cjh.mapper.ArticleMapper;
import com.example.cjh.mapper.UserMapper;
import com.example.cjh.pojo.Article;
import com.example.cjh.pojo.ArticleDetails;
import com.example.cjh.pojo.User;
import com.example.cjh.service.ArticleService;
import com.example.cjh.vo.ArticleDetailsVo;
import com.example.cjh.vo.ArticleVo;
import com.example.cjh.vo.Result;
import com.example.cjh.vo.param.ArticleParams;
import com.example.cjh.vo.param.PageParams;
import com.example.cjh.vo.param.SearchParams;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
@Service
public class ArticleServiceImp implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ArticleDetailsMapper articleDetailsMapper;

    @Override
    @Transactional
    public Result publish(ArticleParams articleParams) {
        //_______插入文章__________
        Article article = new Article();
        article.setCategoryId(articleParams.getCategoryId());
        article.setSummary(articleParams.getSummary());
        article.setTitle(articleParams.getTittle());
        article.setUserId(articleParams.getUserId());
        article.setViewsCount(0);
        article.setCommentsCount(0);
        article.setCreateDate(System.currentTimeMillis());
        article.setWeight(Article.Article_Common);
        articleMapper.insert(article);
        //_________插入文章内容__________
        ArticleDetails articleDetails = new ArticleDetails();
        articleDetails.setArticleId(article.getArticleId());
        articleDetails.setContentText(articleParams.getContentText());
        articleDetailsMapper.insert(articleDetails);
        return Result.success("成功发布");
    }

    @Override
    public List<ArticleVo> getArticles(PageParams pageParams) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_id", pageParams.getCategoryId());
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        IPage<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        List<ArticleVo> articleVos = copyList(articlePage.getRecords());
        return articleVos;
    }

    @Override
    public ArticleDetailsVo findDetailsByArticleId(int articleId) {
        System.out.println("zoudaozh");
        QueryWrapper<ArticleDetails> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_id", articleId);
        queryWrapper.last("limit " + 1);
        ArticleDetails articleDetails = articleDetailsMapper.selectOne(queryWrapper);
        ArticleDetailsVo articleDetailsVo = new ArticleDetailsVo();
        BeanUtils.copyProperties(articleDetails, articleDetailsVo);
        return articleDetailsVo;
    }

    @Override
    public ArticleVo findArticleByArticleId(int articleId) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_id", articleId);
        Article article = articleMapper.selectOne(queryWrapper);
       //查询文章后浏览量增加
        LambdaUpdateWrapper<Article> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        int viewsCount = article.getViewsCount();
        article.setViewsCount(viewsCount+1);
        lambdaUpdateWrapper.eq(Article::getArticleId,article.getArticleId());
        articleMapper.update(article,lambdaUpdateWrapper);
        return copy(article);
    }



    //______转换到vo________________
    public ArticleVo copy(Article article) {
        ArticleVo articleVo = new ArticleVo();
        int userId = article.getUserId();
        User user = userMapper.selectById(userId);
        String userName = user.getNickname();
        BeanUtils.copyProperties(article, articleVo);
        articleVo.setUsername(userName);
        return articleVo;
    }

    public List<ArticleVo> copyList(List<Article> articles) {
        List<ArticleVo> articleVos = new ArrayList<>();
        for (Article article : articles) {
            articleVos.add(copy(article));
        }
        return articleVos;
    }

    @Override
    public Result search(SearchParams searchParams) {
        QueryWrapper<Article> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("category_id",searchParams.getCategoryId());
        queryWrapper.like("title",searchParams.getTitle());
        Page<Article> page = new Page<>(searchParams.getPage(), searchParams.getPageSize());
        IPage<Article> articleIPage=articleMapper.selectPage(page,queryWrapper);
        return Result.success(copyList(articleIPage.getRecords()));
    }



}
