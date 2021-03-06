package com.example.csl.controller;

import com.example.csl.service.Impl.RedisServiceImpl;
import com.example.csl.utils.IdentifyCodeUtil;
import com.example.csl.bean.FsEmail;
import com.example.csl.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @description: 发送邮件获取验证码
 * @author: liuchuanfeng
 * @time: 2020/12/8 15:52
 */
@RestController
@Slf4j
public class GetEmailCodeController {
    @Autowired
    private JavaMailSender javaMailSender;

    @Resource
    private RedisServiceImpl redisService;



    //发送普通文本邮件
    @PostMapping(value = "/user/email")
    public Result sendMail(@RequestBody @Valid FsEmail fsEmail,BindingResult results) {
        Result result = new Result();
        if (results.hasErrors()) {
            result.setStatus(0);
            result.setMessage(results.getFieldError().getDefaultMessage());
            return result;
        }
        String email = fsEmail.getEmail();


        System.out.println(email);
        if (redisService.getValue(email)!=null){
            redisService.delete(email);
        }

        SimpleMailMessage message = new SimpleMailMessage();


        String code = IdentifyCodeUtil.getRandom();
        message.setFrom("279126594@qq.com"); //发送者
        message.setTo(email);  //接受者
        message.setCc("279126594@qq.com"); //抄送，填发送者的邮箱即可
        message.setSubject("[Campus Management]Please verify your device");	//主题
        message.setText(" Your Identify Code is : "+ code+". The verification code will expire in 5 minutes.");	//内容
        try {
            javaMailSender.send(message);
            redisService.setKey(email,code);
        } catch (Exception e) {
            result.setStatus(0);
            result.setMessage("Failed to get the verification code.");
        }



        return result;
    }


}
