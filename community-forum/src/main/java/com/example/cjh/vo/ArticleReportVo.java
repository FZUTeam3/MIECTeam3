package com.example.cjh.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value = "被举报的文章")
@Data
public class ArticleReportVo {
    private Integer articleId;
    private String Title;
    private String summary;
    private String reportUser;
    private String reason;
}
