package com.example.csl.utils;

import com.example.csl.bean.All;
import com.example.csl.bean.City;
import com.example.csl.bean.EpidemicData;
import com.example.csl.bean.Global;
import com.example.csl.result.Result;
import com.example.csl.service.AllService;
import com.example.csl.service.CityService;
import com.example.csl.service.GlobalService;
import net.sf.json.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

@Configuration
@EnableScheduling
public class EpidemicDataUtil {

    @Resource
    private AllService allService;

    @Resource
    private CityService cityService;

    @Resource
    private GlobalService globalService;

    @Scheduled(cron = "0 0 1 * * ?")
    public void getData() throws IOException {
        EpidemicData epidemicData;
        String URI = "https://covid-api.mmediagroup.fr/v1/cases";
        Result result = ResponseUtil.Response(URI);
        if (result.getStatus()==1){
            String content = result.getMessage();
            JSONObject jsonobject = JSONObject.fromObject(content);
            epidemicData = EpidemicDataUtil.StrToArray(jsonobject);

            try {
                allService.insert(epidemicData.getAllList());
                cityService.insert(epidemicData.getCityList());
                globalService.insert(epidemicData.getGlobal());
            }catch (Exception e){
                e.printStackTrace();

            }
        }
    }

    public static EpidemicData StrToArray(JSONObject jsonobject){
        ArrayList<All> countryList = new ArrayList();
        Global globalData = new Global();
        ArrayList<City> cityArrayList = new ArrayList();
        Map<String,Object> countries = JSONUtil.parseJSON2Map(jsonobject);
        Set<String> countryKeys = countries.keySet();

        for (String countryKey:countryKeys){
            if (countryKey.equals("China")){
                cityArrayList.addAll(city(countries,countryKey));
                continue;
            }

            Map<String,Object> alls = (Map<String, Object>) countries.get(countryKey);
            Set<String> allKeys = alls.keySet();
            for (String allKey:allKeys){
                if (allKey.equals("All")){
                    Map<String, Object> all= (Map<String, Object>) alls.get(allKey);
                    Set<String> lists = all.keySet();
                    if (countryKey.equals("Global")){
                        globalData = global(all,lists);
                        continue;
                    }

                    countryList.addAll(all(all,lists));
                }else continue;
            }

        }

        return new EpidemicData(countryList,cityArrayList,globalData);
    }

    private static ArrayList<City> city(Map<String, Object> countries, String countryKey){
        ArrayList<City> cityArrayList = new ArrayList();
        Map<String,Object> cityMap = (Map<String, Object>) countries.get(countryKey);
        Set<String> cityKeys = cityMap.keySet();
        for (String cityKey:cityKeys){
            if (cityKey.equals("All")) continue;
            else {
                City city = new City();
                Map<String,Object> cityProperty = (Map<String, Object>) cityMap.get(cityKey);
                Set<String> cityPropertyList = cityProperty.keySet();
                city.setProvince(cityKey);
                for (String list : cityPropertyList){
                    Object item = cityProperty.get(list);
                    if (item==null) continue;
                    switch (list){
                        case "lat" :
                            city.setLat(item.toString());
                            break;
                        case "long" :
                            city.setMapLong(item.toString());
                            break;
                        case "confirmed" :
                            city.setConfirmed(Long.parseLong(item.toString()));
                            break;
                        case "recovered" :
                            city.setRecovered(Long.parseLong(item.toString()));
                            break;
                        case "deaths" :
                            city.setDeaths(item.toString());
                            break;
                        case "updated" :
                            city.setUpdated(item.toString());
                            break;

                    }
                }
                cityArrayList.add(city);
            }
        }
        return cityArrayList;
    }

    private static Global global(Map<String, Object> all,Set<String> lists){
        Global globalData = new Global();
        for (String list:lists){
            Object item = all.get(list);
            if (item==null) continue;
            switch (list){
                case "population" :
                    globalData.setPopulation(Long.parseLong(item.toString()));
                    break;
                case "confirmed" :
                    globalData.setConfirmed(Long.parseLong(item.toString()));
                    break;
                case "recovered" :
                    globalData.setRecovered(Long.parseLong(item.toString()));
                    break;
                case "deaths" :
                    globalData.setDeaths(Long.parseLong(item.toString()));
                    break;
                default:
                    break;
            }
        }
        return globalData;
    }

    private static ArrayList<All> all(Map<String, Object> all,Set<String> lists){
        ArrayList<All> countryList = new ArrayList();
        All country = new All();
        for (String list:lists){
            Object item = all.get(list);
            if (item==null) continue;
            switch (list){
                case "continent" :
                    country.setContinent(item.toString());
                    break;
                case "country" :
                    country.setCountry(item.toString());
                    break;
                case "iso" :
                    if (item.toString().equals("{}")) country.setIso(null);
                    else country.setIso(Integer.parseInt(item.toString()));
                    break;
                case "capital_city" :
                    country.setCapitalCity(item.toString());
                    break;
                case "life_expectancy" :
                    country.setLifeExpectancy(item.toString());
                    break;
                case "abbreviation" :
                    country.setAbbreviation(item.toString());
                    break;
                case "confirmed" :
                    country.setConfirmed(Long.parseLong(item.toString()));

                    break;
                case "long" :
                    country.setMapLong(item.toString());
                    break;
                case "population" :
                    try {
                        country.setPopulation(Long.parseLong(item.toString()));
                    }catch (Exception e){
                        country.setPopulation(null);
                    }
                    break;
                case "sq_km_area" :
                    try {
                        country.setSqKmArea(Long.parseLong(item.toString()));
                    }catch (Exception e){
                        country.setSqKmArea(null);
                    }
                    break;
                case "recovered" :
                    try {
                        country.setRecovered(Long.parseLong(item.toString()));
                    }catch (Exception e){
                        country.setRecovered(null);
                    }
                    break;
                case "elevation_in_meters" :
                    try {
                        country.setElevationInMeters(Long.parseLong(item.toString()));
                    }catch (Exception e){
                        country.setElevationInMeters(null);
                    }
                    break;
                case "location" :
                    country.setLocation(item.toString());
                    break;
                case "updated" :
                    country.setUpdated(item.toString());
                    break;
                case "deaths" :
                    try {
                        country.setDeath(Long.parseLong(item.toString()));
                    }catch (Exception e){
                        country.setDeath(null);
                    }
                    break;
                case "lat" :
                    country.setLat(item.toString());
                    break;
                default:
                    break;
            }
        }
        countryList.add(country);
        return countryList;
    }
}
