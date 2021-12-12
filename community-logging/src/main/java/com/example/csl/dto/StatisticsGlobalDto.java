package com.example.csl.dto;

import lombok.Data;

import java.util.List;

@Data
public class StatisticsGlobalDto {
    private List<Long> populationData;
    private List<Long> confirmedData;
    private List<Long> deathsData;
}
