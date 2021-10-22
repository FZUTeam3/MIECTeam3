package com.example.csl.Service.Impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.csl.Service.fsUserService;
import com.example.csl.bean.fsUser;
import com.example.csl.mapper.fsUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;

@Service
public class fsUserServiceImpl implements fsUserService {

    @Resource
    private fsUserMapper fsUserMapper;


    @Override
    public void createUser(fsUser user) {

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

        fsUser user = new fsUser();
        user.setStatus(true);
        user.setLastLogin(LocalDate.now());

         UpdateWrapper<fsUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id", userId);
        fsUserMapper.update(user,updateWrapper);

    }


}
