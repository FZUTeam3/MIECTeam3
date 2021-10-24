package com.example.cjh.uitls;

import lombok.Getter;

@Getter
public enum ThumbStatusEnum {
    LIKE(1, "点赞"),
    UNLIKE(0, "取消点赞/未点赞");
    private Integer code;
    private String msg;

    ThumbStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
