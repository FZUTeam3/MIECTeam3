package com.example.csl.controller;


import cn.hutool.crypto.SecureUtil;
import com.example.csl.Service.Impl.RedisServiceImpl;
import com.example.csl.Service.fsUserService;
import com.example.csl.bean.fsUser;
import com.example.csl.result.Result;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;


@RestController
public class fsUserController {

    @Resource
    private fsUserService userService;

    @Resource
    private RedisServiceImpl redisService;


    @GetMapping(value = "/test")
    public void test(){
        userService.update(1L);
    }


    @PostMapping(value = "/register")
    public Result register(@RequestBody @Valid fsUser user, BindingResult results) throws IOException {

        Result result = new Result();
        if (!user.getCode().equals(redisService.getValue(user.getEmail()))){
            System.out.println(user.getCode());
            System.out.println(redisService.getValue(user.getEmail()));
            result.setStatus(0);
            result.setMessage("Identify code isn't correct!");
            return result;
        }
        if (userService.findEmail(user.getEmail())!=null){
            result.setStatus(0);
            result.setMessage("The email has been registered.");
            return result;
        }
        userService.createUser(user);
        if (results.hasErrors()){
            result.setStatus(0);
            result.setMessage(results.getFieldError().getDefaultMessage());
        }

        return result;
    }

    @PostMapping(value = "/login")
    public Result login(@RequestBody fsUser user){
        Result result = new Result();
        String email = user.getEmail();
        String password = SecureUtil.md5(user.getPassword());
        Long userId = userService.loginUser(email,password);

        if (userId==null){
            result.setStatus(0);
            result.setMessage("The email and password are incorrect.");
        }
        //更新登录状态
        userService.update(userId);
        //userService.updateLoginUser("1",LocalDate.now(),userId);
        return result;
    }
}
