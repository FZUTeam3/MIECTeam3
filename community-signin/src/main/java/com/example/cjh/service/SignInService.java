

package com.example.cjh.service;

import com.example.cjh.vo.Result;
import com.example.cjh.vo.param.SignInParam;

public interface SignInService {
    Result signToday(SignInParam signInParam);

    Result ifSignToday();
}
