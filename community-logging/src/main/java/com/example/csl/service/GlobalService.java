package com.example.csl.service;

import com.example.csl.bean.Global;

public interface GlobalService {

    void insert(Global global);
    Global select();
    void delete();
}
