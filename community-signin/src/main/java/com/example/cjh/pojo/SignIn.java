
package com.example.cjh.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;
@Data
@TableName("em_sign_in")
public class SignIn {
    @TableId(type = IdType.AUTO)
    private Integer signId;
    private Integer userId;
    private Timestamp signTime;
    private Integer status;
    private String description;
    }