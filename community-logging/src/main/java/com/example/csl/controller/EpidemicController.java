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
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
@Slf4j
@Configuration
@EnableScheduling
public class EpidemicController {

    @Resource
    private AllService allService;

    @Resource
    private CityService cityService;

    @Resource
    private GlobalService globalService;

    @GetMapping(value = "/user/map")
    public EpidemicData EpidemicData() {
        EpidemicData epidemicData = new EpidemicData();
        epidemicData.setAllList(allService.select());
        epidemicData.setCityList(cityService.select());
        epidemicData.setGlobal(globalService.select());
        return epidemicData;
    }

    @Scheduled(cron = "0 0 1 * * ?")
    @GetMapping(value = "/user/update")
    public void Insert() throws IOException {
        EpidemicDataUtil epidemicDataUtil = new EpidemicDataUtil();
        EpidemicData epidemicData = epidemicDataUtil.getData();
        if (epidemicData!=null){
            allService.update(epidemicData.getAllList());
            cityService.update(epidemicData.getCityList());
            //globalService.insert(epidemicData.getGlobal());
        }

    }
}



