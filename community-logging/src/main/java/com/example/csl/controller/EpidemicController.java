package com.example.csl.controller;

import com.example.csl.service.AllService;
import com.example.csl.service.CityService;
import com.example.csl.service.GlobalService;
import com.example.csl.utils.EpidemicDataUtil;
import com.example.csl.utils.ResponseUtil;
import com.example.csl.bean.EpidemicData;
import com.example.csl.result.Result;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class EpidemicController {

    @Resource
    private AllService allService;

    @Resource
    private CityService cityService;

    @Resource
    private GlobalService globalService;

    @GetMapping(value = "/user/map")
    public EpidemicData EpidemicData() throws Exception{
        EpidemicData epidemicData = new EpidemicData();
        epidemicData.setAllList(allService.select());
        epidemicData.setCityList(cityService.select());
        epidemicData.setGlobal(globalService.select());
        return epidemicData;
    }
}



