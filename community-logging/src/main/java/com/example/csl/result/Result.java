package com.example.csl.result;

import lombok.Data;

@Data
public class Result<T> {

    private int status=1;
    private String message="Successful";
    private String token = null;
    private T content;
}
