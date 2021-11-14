package com.example.csl.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "map_city")
public class City {

    @TableId(type = IdType.AUTO)
    private Long cityId;

    private String Province;
    private String Lat;
    private String MapLong;
    private Long Confirmed;
    private Long Recovered;
    private String Deaths;
    private String Updated;

}
