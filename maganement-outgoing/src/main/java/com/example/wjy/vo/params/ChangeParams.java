package com.example.wjy.vo.params;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
@Data
public class ChangeParams {
    private Integer indexId;
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
}
