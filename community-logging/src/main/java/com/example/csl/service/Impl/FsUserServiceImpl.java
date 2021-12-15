package com.example.csl.service.Impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.csl.service.FsUserService;
import com.example.csl.bean.FsUser;
import com.example.csl.mapper.FsUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class FsUserServiceImpl implements FsUserService {

    @Resource
    private FsUserMapper fsUserMapper;


    @Override
    public void createUser(FsUser user) {

        //账号创建时间
        user.setCreateDate(LocalDate.now());

        user.setStatus(false);

        //密码加盐
        user.setPassword(SecureUtil.md5(user.getPassword()));

        fsUserMapper.insert(user);

    }

    @Override
    public Long findEmail(String email) {
        return fsUserMapper.findEmail(email);
    }

    @Override
    public Long loginUser(String phone, String password) {
        return fsUserMapper.loginUser(phone,password);
    }

    @Override
    public void update(Long userId) {

        FsUser user = new FsUser();
        user.setStatus(true);
        user.setLastLogin(LocalDate.now());

         UpdateWrapper<FsUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id", userId);
        fsUserMapper.update(user,updateWrapper);

    }

    @Override
    public FsUser findMessage(Long userId) {
        return fsUserMapper.selectById(userId);
    }

    @Override
    public void delete(Long userId) {
        FsUser user = new FsUser();
        user.setStatus(false);
        UpdateWrapper<FsUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id", userId);
        fsUserMapper.update(user,updateWrapper);
    }

    @Override
    public Integer statisticsOnline() {
        QueryWrapper<FsUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",true);
        Integer num = fsUserMapper.selectCount(queryWrapper);
        return num;
    }

    @Override
    public ArrayList<Integer> statisticsLogin() {

        LocalDate today = LocalDate.now();
        Map<LocalDate,Integer> loginList = new HashMap<>();
        ArrayList list = new ArrayList<Integer>();
        for (int i=0;i<7;i++){
            QueryWrapper<FsUser> queryWrapper = new QueryWrapper<>();
            LocalDate start = today.minus(i, ChronoUnit.DAYS);
            queryWrapper.eq("last_login",start);
            Integer num = fsUserMapper.selectCount(queryWrapper);
            list.add(num);
        }



        return list;
    }


}
