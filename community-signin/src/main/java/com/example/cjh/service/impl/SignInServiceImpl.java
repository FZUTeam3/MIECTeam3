package com.example.cjh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.cjh.mapper.SignInMapper;
import com.example.cjh.pojo.SignIn;
import com.example.cjh.service.SignInService;
import com.example.cjh.utils.JudgeIfIn;
import com.example.cjh.vo.Result;
import com.example.cjh.vo.SignInVo;
import com.example.cjh.vo.param.SignInParam;

import java.sql.Timestamp;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class SignInServiceImpl implements SignInService {
    @Autowired
    private SignInMapper signInMapper;

    public Result signToday(SignInParam signInParam) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String identityId = (String) request.getAttribute("identityId");
        int userId = Integer.parseInt(identityId);
        Timestamp nowTime = new Timestamp(Calendar.getInstance().get(1) - 1900, Calendar.getInstance().get(2), Calendar.getInstance().get(5), Calendar.getInstance().get(10), Calendar.getInstance().get(12), Calendar.getInstance().get(13), 0);
        Timestamp startTime = new Timestamp(Calendar.getInstance().get(1) - 1900, Calendar.getInstance().get(2), Calendar.getInstance().get(5), 0, 0, 0, 0);
        QueryWrapper<SignIn> queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", userId);
        queryWrapper.between("sign_time", startTime, nowTime);
        Integer signIn = this.signInMapper.selectCount(queryWrapper);
        if (signIn == 0) {
            try {
                if (JudgeIfIn.ifIn(signInParam.getLocation())) {
                    SignIn signInNow = new SignIn();
                    signInNow.setSignTime(nowTime);
                    signInNow.setDescription(signInParam.getDescription());
                    signInNow.setStatus(signInParam.getStatus());
                    signInNow.setUserId(userId);
                    this.signInMapper.insert(signInNow);
                    return Result.success("Succeed to sign in");
                }

                return Result.fail(0, "You are not in the legal area");
            } catch (Exception var10) {
                System.out.println("Judge ERROR");
                var10.printStackTrace();
            }
        }

        return Result.fail(0, "You have signed in today");
    }

    public Result ifSignToday() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String identityId = (String) request.getAttribute("identityId");
        int userId = Integer.parseInt(identityId);
        Timestamp nowTime = new Timestamp(Calendar.getInstance().get(1) - 1900, Calendar.getInstance().get(2), Calendar.getInstance().get(5), Calendar.getInstance().get(10), Calendar.getInstance().get(12), Calendar.getInstance().get(13), 0);
        Timestamp startTime = new Timestamp(Calendar.getInstance().get(1) - 1900, Calendar.getInstance().get(2), Calendar.getInstance().get(5), 0, 0, 0, 0);
        QueryWrapper<SignIn> queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", userId);
        queryWrapper.between("sign_time", startTime, nowTime);
        SignIn signIn = (SignIn) this.signInMapper.selectOne(queryWrapper);
        if (signIn == null) {
            return Result.fail(0, "You still haven't signed in today");
        } else {
            SignInVo signInVo = this.toVo(signIn);
            return Result.success(signInVo);
        }
    }

    public SignInVo toVo(SignIn signIn) {
        SignInVo signInVo = new SignInVo();
        BeanUtils.copyProperties(signIn, signInVo);
        signInVo.setSignTime(signIn.getSignTime().toString());
        return signInVo;
    }
}
