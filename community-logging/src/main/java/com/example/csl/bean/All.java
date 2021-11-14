package com.example.csl.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;



@Data
@TableName(value = "map_country")
public class All implements Serializable{

    @TableId(type = IdType.AUTO)
    private Long countryId;

    private Long Confirmed;
    private Long Recovered;
    private Long Death;
    private String Country;
    private Long Population;
    private Long SqKmArea;
    private String LifeExpectancy;
    private Long ElevationInMeters;
    private String Continent;
    private String Abbreviation;
    private String Location;
    private Integer Iso;
    private String CapitalCity;
    private String Lat;
    private String MapLong;
    private String Updated;

}



