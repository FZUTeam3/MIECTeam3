package com.example.wjy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.wjy.mapper.OgInfoMapper;
import com.example.wjy.mapper.OgInfoPassMapper;
import com.example.wjy.mapper.OgInfoTravelMapper;
import com.example.wjy.pojo.OgInfo;
import com.example.wjy.pojo.OgInfoPass;
import com.example.wjy.pojo.OgInfoTravel;
import com.example.wjy.pojo.OgInfoVo;
import com.example.wjy.service.AuditService;
import com.example.wjy.vo.Result;
import com.example.wjy.vo.params.AuditParams;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuditServiceImpl implements AuditService {
    @Autowired
    OgInfoMapper ogInfoMapper;
    @Autowired
    OgInfoPassMapper ogInfoPassMapper;
    @Autowired
    OgInfoTravelMapper ogInfoTravelMapper;

    @Override
    public Result check(AuditParams auditParams) {

        QueryWrapper<OgInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("index_id",auditParams.getIndexId());
        OgInfo ogInfo = ogInfoMapper.selectOne(queryWrapper);
        ogInfo.setFeedback(auditParams.getFeedback());
        ogInfo.setStatus(auditParams.getStatus());
        ogInfoMapper.update(ogInfo,queryWrapper);
        return Result.success("Audit completed");

    }
   @Override
    public    List<OgInfoVo> searchAll(){
       List<OgInfo> ogInfos = ogInfoMapper.selectList(null);
       List<OgInfoVo> ogInfoVos = new ArrayList<>();
       for (OgInfo ogInfo:ogInfos){
           OgInfoVo ogInfoVo = new OgInfoVo();

           QueryWrapper<OgInfoPass> queryWrapper1 = new QueryWrapper<>();
           queryWrapper1.eq("pass_area_id",ogInfo.getPassAreaId());
           OgInfoPass ogInfoPass = ogInfoPassMapper.selectOne(queryWrapper1);

           QueryWrapper<OgInfoTravel> queryWrapper2 = new QueryWrapper<>();
           queryWrapper2.eq("travel_area_id",ogInfo.getTravelAreaId());
           OgInfoTravel ogInfoTravel = ogInfoTravelMapper.selectOne(queryWrapper2);

           BeanUtils.copyProperties(ogInfo,ogInfoVo);
           ogInfoVo.setPassAreaInfo(ogInfoPass.getPassAreaInfo());
           ogInfoVo.setTravelAreaInfo(ogInfoTravel.getTravelAreaInfo());
           if(ogInfo.getStatus()==0){
               ogInfoVo.setStatus("Audit pending");
           }else if(ogInfo.getStatus()==1){
               ogInfoVo.setStatus("Audit failed");
           }else if(ogInfo.getStatus()==2){
               ogInfoVo.setStatus("Audit passed");
           }
           ogInfoVos.add(ogInfoVo);
       }
       return ogInfoVos;
   }
   }

