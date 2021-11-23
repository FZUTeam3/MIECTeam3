package com.example.cjh.controller;

import com.example.cjh.service.SignInService;
import com.example.cjh.vo.Result;
import com.example.cjh.vo.param.SignInParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"sign"})
public class SignInController {
    @Autowired
    private SignInService signInService;

    public SignInController() {
    }

    @PostMapping({"signToday"})
    public Result signToday(@RequestBody SignInParam signInParam) {
        return this.signInService.signToday(signInParam);
    }

    @GetMapping({"ifSignToday"})
    public Result ifSignToday() {
        return this.signInService.ifSignToday();
    }
}
