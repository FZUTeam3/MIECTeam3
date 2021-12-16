package com.example.wjy.service;

import com.example.wjy.pojo.OgInfoVo;
import com.example.wjy.vo.Result;
import com.example.wjy.vo.params.AuditParams;

import java.util.List;

public interface AuditService {
    Result check(AuditParams auditParams);
    List<OgInfoVo> searchAll();

}
