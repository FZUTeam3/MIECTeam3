package com.example.csl.service.Impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.csl.dto.StatisticsGlobalDto;
import com.example.csl.service.GlobalService;
import com.example.csl.bean.Global;
import com.example.csl.mapper.GlobalMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class GlobalServiceImpl implements GlobalService {

    @Resource
    private GlobalMapper globalMapper;
    @Override
    public void insert(Global global) {
        globalMapper.insert(global);
    }

    @Override
    public Global select() {
        List<Global> globalList = (List<Global>) globalMapper.selectList(null);
        Global global = globalList.get(globalList.size()-1);
        return global;
    }

    @Override
    public void delete() {
        globalMapper.delete(null);
    }

    @Override
    public StatisticsGlobalDto statisticsGlobal() {
        QueryWrapper<Global> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("confirmed").last("limit 7");
        List<Global> list = globalMapper.selectList(queryWrapper);
        StatisticsGlobalDto statisticsGlobalDto = new StatisticsGlobalDto();

        List<Long> populationData = new ArrayList<>();
        List<Long> confirmedData = new ArrayList<>();
        List<Long> deathsData = new ArrayList<>();
        for (int i=list.size()-1;i>=0;i--){
            populationData.add(list.get(i).getPopulation());
            confirmedData.add(list.get(i).getConfirmed());
            deathsData.add(list.get(i).getDeaths());
        }
        statisticsGlobalDto.setPopulationData(populationData);
        statisticsGlobalDto.setConfirmedData(confirmedData);
        statisticsGlobalDto.setDeathsData(deathsData);
        return statisticsGlobalDto;
    }



}
