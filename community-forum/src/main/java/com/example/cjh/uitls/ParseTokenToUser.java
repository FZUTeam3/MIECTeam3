package com.example.cjh.uitls;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.csl.Utils.UserThreadLocal;
import com.example.csl.bean.FsUser;
import com.example.csl.config.JwtConfig;
import com.example.csl.mapper.FsUserMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class ParseTokenToUser {
    @Autowired
    JwtConfig jwtConfig;
    @Autowired
    FsUserMapper fsUserMapper;

    public  void parseToken(String token) {
        String usernameFromToken = jwtConfig.getUsernameFromToken(token);
        QueryWrapper<FsUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("nickname", usernameFromToken);
        FsUser currentUser = fsUserMapper.selectOne(queryWrapper);
        UserThreadLocal.put(currentUser);
        System.out.println(currentUser.toString());
    }
}
