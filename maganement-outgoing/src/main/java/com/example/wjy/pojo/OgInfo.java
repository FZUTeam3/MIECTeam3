package com.example.wjy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("og_information")
public class OgInfo {

    @TableId(type = IdType.AUTO)
    private Integer indexId;
    /** 用户 */
    private Integer userId;
    /** 所在区域 */
    private Integer passAreaId;
    /** 外出区域 */
    private Integer travelAreaId;
    /** 起始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai")
    private Date startTime;
    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai")
    private Date lastTime;
    /** 外出原因 */
    private String reasonContent;
    /** 申请状态 */
    private Integer status;
    /** 拒绝申请时的反馈 */
    private String feedback;


}
