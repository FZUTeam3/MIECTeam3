package com.example.csl.service.Impl;

import com.example.csl.service.CityService;
import com.example.csl.bean.City;
import com.example.csl.mapper.CityMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
public class CityServiceImpl implements CityService {

    @Resource
    private CityMapper cityMapper;
    @Override
    public void insert(ArrayList<City> cityArrayLists) {
        for (City cityList:cityArrayLists){
            cityMapper.insert(cityList);
        }
    }

    @Override
    public ArrayList<City> select() {
        ArrayList<City> cityList = (ArrayList<City>) cityMapper.selectList(null);
        return cityList;
    }

    @Override
    public void delete() {
        cityMapper.delete(null);
    }

    @Override
    public void update(ArrayList<City> cityArrayLists) {
        for (City cityList:cityArrayLists){
//            City oldCity = cityMapper.selectById(cityList.getName());
//            cityList.setValue(cityList.getValue()-oldCity.getValue());
            cityMapper.updateById(cityList);
        }
    }
}
