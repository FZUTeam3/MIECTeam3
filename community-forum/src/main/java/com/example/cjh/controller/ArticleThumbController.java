package com.example.cjh.controller;

import com.example.cjh.service.ArticleThumbService;
import com.example.cjh.vo.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "点赞查询")
@RestController
@RequestMapping("thumb")
public class ArticleThumbController {
    @Autowired
    ArticleThumbService articleThumbService;

    @GetMapping("addThumb/{articleId}")
    public Result addThumb(@PathVariable("articleId") int articleId) {
        return articleThumbService.addThumb2Redis(articleId);
    }

    @GetMapping("cancelThumb/{articleId}")
    public Result cancelThumb(@PathVariable("articleId") int articleId) {
        return articleThumbService.cancelThumb2Redis(articleId);
    }

}
