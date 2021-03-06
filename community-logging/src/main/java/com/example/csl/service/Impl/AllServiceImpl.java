package com.example.csl.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.csl.bean.FsUser;
import com.example.csl.service.AllService;
import com.example.csl.bean.All;
import com.example.csl.mapper.AllMapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
public class AllServiceImpl implements AllService {

    @Resource
    private AllMapper allMapper;

    @Override
    public void insert(ArrayList<All> allArrayLists) {
        for (All all:allArrayLists){
            allMapper.insert(all);
        }
    }

    @Override
    public ArrayList<All> select() {
        ArrayList<All> list = (ArrayList<All>) allMapper.selectList(null);
        return list;
    }

    @Override
    public void delete() {
        allMapper.delete(null);
    }

    @Override
    public void update(ArrayList<All> allArrayLists) {
        for (All all:allArrayLists){
//            All oldAll = allMapper.selectById(all.getCountry());
//            all.setConfirmed(all.getConfirmed()-oldAll.getConfirmed());
            allMapper.updateById(all);
        }
    }

}
