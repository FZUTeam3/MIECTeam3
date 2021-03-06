package com.example.csl.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.csl.bean.ArticleStatistics;
import com.example.csl.bean.OgInfoStatistics;
import com.example.csl.mapper.ArticleStatisticsMapper;
import com.example.csl.mapper.OgInfoStatisticsMapper;
import com.example.csl.service.StatisticsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Resource
    private ArticleStatisticsMapper articleStatisticsMapper;

    @Resource
    private OgInfoStatisticsMapper ogInfoStatisticsMapper;


    @Override
    public ArrayList<Integer> statisticsArticle() {
        Long date = System.currentTimeMillis();
        ArrayList list = new ArrayList<Integer>();
        for (int i=0;i<7;i++){
            QueryWrapper<ArticleStatistics> queryWrapper = new QueryWrapper<>();
            Long start = date- 3600L *1000*24*i;
            Long end =start-3600*1000*24;
            queryWrapper.between("create_date",end,start);
            Integer num = articleStatisticsMapper.selectCount(queryWrapper);
            list.add(num);
        }
        return list;
    }

    @Override
    public ArrayList<Integer> statisticsOut() {
        LocalDate date = LocalDate.now();

        ArrayList list = new ArrayList<Integer>();
        for (int i=0;i<7;i++){
            QueryWrapper<OgInfoStatistics> queryWrapper = new QueryWrapper<>();
        LocalDate start = date.minus(i+1, ChronoUnit.DAYS);
        LocalDate end =date.minus(i,ChronoUnit.DAYS);
            queryWrapper.between("start_time",start,end);
            Integer num = ogInfoStatisticsMapper.selectCount(queryWrapper);
            list.add(num);
        }
        return list;
    }


}
