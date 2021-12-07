package com.example.wjy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.wjy.mapper.OgInfoMapper;
import com.example.wjy.pojo.OgInfo;
import com.example.wjy.service.OgInfoService;
import com.example.wjy.vo.Result;
import com.example.wjy.vo.params.ChangeParams;
import com.example.wjy.vo.params.OgInfoParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OgInfoServiceImpl implements OgInfoService {
    @Autowired
    OgInfoMapper ogInfoMapper;
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
        return Result.success("申请成功");
    }

    @Override
    public List<OgInfo> search(int userId) {
        QueryWrapper<OgInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<OgInfo> ogInfos = new ArrayList<>();
        ogInfos = ogInfoMapper.selectList(queryWrapper);
        return ogInfos;
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
        return Result.success("修改成功");
    }

    @Override
    public Result delete(Integer indexId) {
        QueryWrapper<OgInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("index_id",indexId);
        ogInfoMapper.delete(queryWrapper);
        return Result.success("删除成功");
    }

}
