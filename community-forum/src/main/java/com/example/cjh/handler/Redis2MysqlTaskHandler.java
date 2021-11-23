package com.example.cjh.handler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.cjh.mapper.ArticleMapper;
import com.example.cjh.mapper.ArticleThumbMapper;
import com.example.cjh.pojo.Article;
import com.example.cjh.pojo.ArticleThumb;
import com.example.cjh.uitls.ThumbUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Map;

@Slf4j
@Component
public class Redis2MysqlTaskHandler {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ArticleThumbMapper articleThumbMapper;
    @Autowired
    private ArticleMapper articleMapper;

    @Scheduled(cron = "0 0/2 * * * ?")
    public void redis2Mysql() {
//        log.info("数据正在从redis转入mysql");
        Map<String, String> entries = redisTemplate.boundHashOps(ThumbUtils.Key_Thumb).entries();
        HashSet<Integer> updateArticleId = new HashSet<>();
        redisTemplate.delete(ThumbUtils.Key_Thumb);
        ArticleThumb articleThumb = new ArticleThumb();
        if (entries.isEmpty()) {
//            log.info("无需转入mysql,因为redis里是空的");
        } else {
            for (String key : entries.keySet()) {
                int index = key.indexOf(":");
                int articleId = Integer.parseInt(key.substring(0, index));
                int userId = Integer.parseInt(key.substring(index + 2, key.length()));
                int status = Integer.parseInt(entries.get(key));
                updateArticleId.add(articleId);
                if (status == 1) {
                    articleThumb.setArticleId(articleId);
                    articleThumb.setUser_id(userId);
                    QueryWrapper<ArticleThumb> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("article_id", articleId);
                    queryWrapper.eq("user_id", userId);
                    if (articleThumbMapper.selectCount(queryWrapper) == 0) {
                        articleThumbMapper.insert(articleThumb);
                    }
                } else if (status == 0) {
                    QueryWrapper<ArticleThumb> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("article_id", articleId);
                    queryWrapper.eq("user_id", userId);
                    articleThumbMapper.delete(queryWrapper);
                }
//            System.out.println(articleId);
//            System.out.println(userId);
            }
//            log.info("数据转入成功,并成功清除当前redis");
            //更新文章的点赞量
            for (int updateId : updateArticleId) {
                QueryWrapper queryWrapper = new QueryWrapper();
                queryWrapper.eq("article_id", updateId);
                Integer thumbCountsNew = articleThumbMapper.selectCount(queryWrapper);
                Article article = new Article();
                article.setArticleId(updateId);
                article.setThumbCount(thumbCountsNew);
                articleMapper.updateById(article);
            }
//            log.info("更新文章点赞数完成");

        }
    }
}
