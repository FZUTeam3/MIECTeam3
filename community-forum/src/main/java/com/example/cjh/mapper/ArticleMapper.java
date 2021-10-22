package com.example.cjh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.cjh.pojo.Article;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
}
