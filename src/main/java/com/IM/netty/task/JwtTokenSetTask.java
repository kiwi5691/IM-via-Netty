package com.IM.netty.task;

import com.IM.netty.cache.JwtLocalCache;
import com.IM.netty.entity.JwtToken;
import com.IM.netty.service.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class JwtTokenSetTask {
    @Autowired
    private JwtTokenService jwtTokenService;
    //每小时执行一次
    @Scheduled(cron = "0 0 0/1 * * ?")
    private void jwtTokenUpdates() {
        Iterable<JwtToken> jwtTokens= jwtTokenService.listAll();
        JwtLocalCache.JwtLocalCacheUpdate(jwtTokens);
    }
}
