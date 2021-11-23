package com.example.csl.service.Impl;


import com.example.csl.service.GlobalService;
import com.example.csl.bean.Global;
import com.example.csl.mapper.GlobalMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

}
