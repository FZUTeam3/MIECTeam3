package com.example.csl.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "map_city")
public class City {

    @TableId
    private String Name;
    private String Lat;
    private String MapLong;
    private Long Value;
    private Long Recovered;
    private String Deaths;
    private String Updated;

}
