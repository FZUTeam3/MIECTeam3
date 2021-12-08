package com.example.csl.service;

import com.example.csl.bean.Global;
import com.example.csl.dto.StatisticsGlobalDto;

import java.util.List;

public interface GlobalService {

    void insert(Global global);
    Global select();
    void delete();
    StatisticsGlobalDto statisticsGlobal();

}
