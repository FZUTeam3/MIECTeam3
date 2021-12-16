package com.example.wjy.control;

import com.example.wjy.pojo.OgInfoVo;
import com.example.wjy.service.AuditService;
import com.example.wjy.vo.Result;
import com.example.wjy.vo.params.AuditParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("Audit")
public class AuditController {
    @Autowired
    AuditService auditService;


    @PostMapping("check")
    public Result check (@RequestBody AuditParams auditParams){
        return auditService.check(auditParams);
    }
    @PostMapping("searchAll")
    public Result searchAll(){
        List<OgInfoVo> ogInfoVos =auditService.searchAll();
        return Result.success(ogInfoVos);
    }
}
