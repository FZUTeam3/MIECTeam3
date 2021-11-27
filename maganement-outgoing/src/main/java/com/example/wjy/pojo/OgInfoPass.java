package com.example.wjy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("og_information_pass")
public class OgInfoPass {
    @TableId(type = IdType.AUTO)
    private Integer passAreaId;

    private String passAreaInfo;

}
