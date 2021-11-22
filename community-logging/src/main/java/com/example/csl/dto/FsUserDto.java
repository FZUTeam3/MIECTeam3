package com.example.csl.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class FsUserDto {

    private Long userId;

    private String account;

    private String nickname;

    private String phoneNumber;

    private String email;


}
