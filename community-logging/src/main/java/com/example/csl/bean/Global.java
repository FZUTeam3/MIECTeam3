package com.example.csl.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "map_global")
public class Global {
    private Long Population;
    private Long Confirmed;
    private Long Recovered;
    private Long Deaths;
}
