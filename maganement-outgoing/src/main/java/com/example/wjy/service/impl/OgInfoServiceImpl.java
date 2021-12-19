package com.example.wjy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.wjy.mapper.OgInfoMapper;
import com.example.wjy.mapper.OgInfoPassMapper;
import com.example.wjy.mapper.OgInfoTravelMapper;
import com.example.wjy.pojo.OgInfo;
import com.example.wjy.pojo.OgInfoPass;
import com.example.wjy.pojo.OgInfoTravel;
import com.example.wjy.pojo.OgInfoVo;
import com.example.wjy.service.OgInfoService;
import com.example.wjy.vo.Result;
import com.example.wjy.vo.params.ChangeParams;
import com.example.wjy.vo.params.OgInfoParams;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OgInfoServiceImpl implements OgInfoService {
    @Autowired
    OgInfoMapper ogInfoMapper;
    @Autowired
    OgInfoPassMapper ogInfoPassMapper;
    @Autowired
    OgInfoTravelMapper ogInfoTravelMapper;

    @Override
    public Result apply(OgInfoParams ogInfoParams, int userId) {
        OgInfo ogInfo = new OgInfo();
        ogInfo.setUserId(userId);
        ogInfo.setPassAreaId(ogInfoParams.getPassAreaId());
        ogInfo.setTravelAreaId(ogInfoParams.getTravelAreaId());
        ogInfo.setStartTime(ogInfoParams.getStartTime());
        ogInfo.setLastTime(ogInfoParams.getLastTime());
        ogInfo.setReasonContent(ogInfoParams.getReasonContent());
        ogInfo.setStatus(1);
        ogInfoMapper.insert(ogInfo);
        return Result.success("Application approved");
    }

    @Override
    public List<OgInfoVo> search(int userId) {
        QueryWrapper<OgInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<OgInfo> ogInfos = new ArrayList<>();
        ogInfos = ogInfoMapper.selectList(queryWrapper);
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

    @Override
    public Result change(ChangeParams changeParams,int userId) {
        OgInfo ogInfo = new OgInfo();
        ogInfo.setUserId(userId);
        ogInfo.setPassAreaId(changeParams.getPassAreaId());
        ogInfo.setTravelAreaId(changeParams.getTravelAreaId());
        ogInfo.setStartTime(changeParams.getStartTime());
        ogInfo.setLastTime(changeParams.getLastTime());
        ogInfo.setReasonContent(changeParams.getReasonContent());
        QueryWrapper<OgInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("index_id",changeParams.getIndexId());
        ogInfoMapper.update(ogInfo,queryWrapper);
        return Result.success("Modify successfully");
    }

    @Override
    public Result delete(Integer indexId) {
        QueryWrapper<OgInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("index_id",indexId);
        ogInfoMapper.delete(queryWrapper);
        return Result.success("Deleted successfully");
    }

}
