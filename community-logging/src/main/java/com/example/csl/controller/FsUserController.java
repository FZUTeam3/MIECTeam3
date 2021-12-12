package com.example.csl.controller;


import cn.hutool.crypto.SecureUtil;

import com.example.csl.bean.Token;
import com.example.csl.dto.FsUserDto;
import com.example.csl.service.Impl.FsUserServiceImpl;
import com.example.csl.service.Impl.RedisServiceImpl;
import com.example.csl.service.FsUserService;
import com.example.csl.bean.FsUser;
import com.example.csl.config.JwtConfig;
import com.example.csl.mapper.FsUserMapper;
import com.example.csl.result.Result;
import com.example.csl.utils.CopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;


@RestController
public class FsUserController {

    @Resource
    private FsUserService userService;

    @Resource
    private RedisServiceImpl redisService;

    @Resource
    JwtConfig jwtConfig;

    @Autowired
    private FsUserMapper fsUserMapper1;

    @GetMapping(value = "/test")
    public String test() {
        String a = jwtConfig.createToken("123456");
        //System.out.println(jwtConfig.getExpirationDateFromToken(a));
        //System.out.println(jwtConfig.getIssuedAtDateFromToken(a));
        String b = "生成token: "+a+"\n获取token失效时间:"+jwtConfig.getExpirationDateFromToken(a)+"\n获取token中注册信息:"+jwtConfig.getTokenClaim(a);
        return b;

    }

    @PostMapping(value = "/user/message")
    public Result message(@RequestBody Token token){
        Result result = new Result();
        Long userId = Long.parseLong(jwtConfig.getUsernameFromToken(token.getToken()));
        if (userId!=null){
            FsUser user = userService.findMessage(userId);
            if (user.isStatus()==true){
                FsUserDto userDto = CopyUtil.copy(user,FsUserDto.class);
                result.setContent(userDto);
            }else {
                result.setStatus(0);
                result.setMessage("the user logins out");
            }

        }else {
            result.setStatus(0);
            result.setMessage("the token is disabled");
        }
        return result;
    }


    @PostMapping(value = "/user/register")
    public Result register(@RequestBody @Valid FsUser user, BindingResult results) throws IOException {

        Result result = new Result();
        if (!user.getCode().equals(redisService.getValue(user.getEmail()))) {
            System.out.println(user.getCode());
            System.out.println(redisService.getValue(user.getEmail()));
            result.setStatus(0);
            result.setMessage("Identify code isn't correct!");
            return result;
        }
        if (userService.findEmail(user.getEmail()) != null) {
            synchronized(FsUserServiceImpl.class){
                if (userService.findEmail(user.getEmail()) != null){
            result.setStatus(0);
            result.setMessage("The email has been registered.");
            return result;
            }
        }
        }

        if (results.hasErrors()) {
            result.setStatus(0);
            result.setMessage(results.getFieldError().getDefaultMessage());
            return result;
        }
        userService.createUser(user);
        return result;
    }

    @PostMapping(value = "/user/login")
    public Result login(@RequestBody FsUser user) {
        Result result = new Result();
        String email = user.getEmail();
        String password = SecureUtil.md5(user.getPassword());
        Long userId = userService.loginUser(email, password);
        if (userId == null) {
            result.setStatus(0);
            result.setMessage("The email and password are incorrect.");
            return result;
        }
        userService.update(userId);
        String token = jwtConfig.createToken(userId.toString());
        result.setToken(token);
        return result;
    }

    @PostMapping(value = "/user/delete")
    public Result delete(@RequestBody Token token){
        Result result = new Result();
        Long userId = Long.parseLong(jwtConfig.getUsernameFromToken(token.getToken()));
        if (userId != null){
            userService.delete(userId);
        }else{
            result.setStatus(0);
            result.setMessage("fail to login out.");
        }
        return result;
    }
}
