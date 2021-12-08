package com.example.csl.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

public interface StatisticsService {

    Map<Date,Integer> statisticsArticle();
    Map<LocalDate,Integer> statisticsOut();
}
