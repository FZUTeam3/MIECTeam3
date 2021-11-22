package com.example.csl.service;

import com.example.csl.bean.All;

import java.util.ArrayList;

public interface AllService {

    void insert(ArrayList<All> allArrayLists);
    ArrayList<All> select();
    void delete();
    void update(ArrayList<All> allArrayLists);
}
