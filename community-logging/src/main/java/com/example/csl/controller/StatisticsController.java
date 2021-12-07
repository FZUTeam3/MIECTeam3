package com.example.csl.controller;

import com.example.csl.result.Result;
import com.example.csl.service.Impl.FsUserServiceImpl;
import com.example.csl.service.Impl.GlobalServiceImpl;
import com.example.csl.service.Impl.StatisticsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class StatisticsController {

    @Resource
    private GlobalServiceImpl globalService;

    @Resource
    private FsUserServiceImpl fsUserService;

    @Resource
    StatisticsServiceImpl statisticsService;

    /**
     * @Description: 统计最近7天全球疫情情况
     * @return
     */
    @GetMapping("/admin/global")
    public Result recentGlobal(){
        Result result = new Result();
        result.setContent(globalService.statisticsGlobal());
        return result;
    }

    /**
     * @Description: 统计目前在线人数
     * @return
     */
    @GetMapping("/statistics/online")
    public Result recentOnline(){
        Result result = new Result();
        result.setContent(fsUserService.statisticsOnline());
        return result;
    }

    /**
     * @Description: 统计最近7天登录人数
     * @return
     */
    @GetMapping("/statistics/login")
    public Result recentRegister(){
        Result result = new Result();
        result.setContent(fsUserService.statisticsLogin());
        return result;
    }

    /**
     * @Description: 统计最近7天发布文章人数
     * @return
     */
    @GetMapping("/statistics/article")
    public Result recentArticle(){
        Result result = new Result();
        result.setContent(statisticsService.statisticsArticle());
        return result;
    }

    /**
     * @Description: 统计最近7天申请外出的人数
     * @return
     */
    @GetMapping("/statistics/out")
    public Result recentOut(){
        Result result = new Result();
        result.setContent(statisticsService.statisticsOut());
        return result;
    }

}
