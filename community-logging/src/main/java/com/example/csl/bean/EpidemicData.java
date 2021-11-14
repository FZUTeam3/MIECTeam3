package com.example.csl.bean;

import lombok.Data;

import java.util.ArrayList;

@Data
public class EpidemicData {
    private ArrayList<All> allList;
    private ArrayList<City> cityList;
    private Global global;

    public EpidemicData(ArrayList<All> allList,ArrayList<City> cityList,Global global){
        this.allList = allList;
        this.cityList = cityList;
        this.global = global;
    }

    public EpidemicData() {
    }
}
