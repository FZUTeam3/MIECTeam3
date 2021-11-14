package com.example.wjy.service;

import com.example.wjy.pojo.OgInfo;
import com.example.wjy.vo.Result;
import com.example.wjy.vo.params.ChangeParams;
import com.example.wjy.vo.params.OgInfoParams;

import java.util.List;

public interface OgInfoService {
     Result apply(OgInfoParams ogInfoParams, int userId);

    List<OgInfo> search(int userId);

    Result change(ChangeParams changeParams,int userId);

    Result delete(Integer indexId);
}
