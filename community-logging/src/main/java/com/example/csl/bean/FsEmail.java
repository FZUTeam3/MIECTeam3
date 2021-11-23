package com.example.csl.bean;

import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class FsEmail {


    @Email(message = "The Email format is error")
    private String email;
}
