package com.example.csl.service;

import com.example.csl.bean.City;

import java.util.ArrayList;

public interface CityService {

    void insert(ArrayList<City> cityArrayLists);

    ArrayList<City> select();
}
