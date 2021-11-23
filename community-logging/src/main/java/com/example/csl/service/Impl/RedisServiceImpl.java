package com.example.csl.service.Impl;


import com.example.csl.service.IRedisService;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements IRedisService {

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public void setKey(String key, String value) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(key, value, 900, TimeUnit.SECONDS);//5分钟过期
    }

    @Override
    public String getValue(String key) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        return ops.get(key);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

}
