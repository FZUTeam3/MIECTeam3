package com.example.csl.service.Impl;


import com.example.csl.service.GlobalService;
import com.example.csl.bean.Global;
import com.example.csl.mapper.GlobalMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
        Global global =globalMapper.selectOne(null);
        return global;
    }
}
