package com.example.csl.result;

import lombok.Data;

@Data
public class Result {

    private int status=1;
    private String message="Successful";
    private String token = null;
}
