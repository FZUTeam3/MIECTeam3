package com.example.csl.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Data
public class fsUser {

    @TableId(type = IdType.AUTO)
    private Long userId;

    @NotNull(message = "Account can't be null")
    private String account;

    @NotNull(message = "password can't be null")
    private String password;

    @NotNull(message = "nick Name can't be null")
    private String nickname;

    @NotNull(message = "phone Number can't be null")
    private String phoneNumber;

    @NotNull(message = "email can't be null")
    @Email(message = "The Email format is error")
    private String email;


    private LocalDate createDate;

    private LocalDate lastLogin;

    private boolean status;

    @TableField(exist = false)
    private String code;

}
