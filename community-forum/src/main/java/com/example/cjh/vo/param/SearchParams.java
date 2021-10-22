package com.example.cjh.vo.param;

import lombok.Data;

@Data
public class SearchParams {
    private int page = 1;
    private int pageSize = 10;
    private String title;
    private int categoryId;
}
