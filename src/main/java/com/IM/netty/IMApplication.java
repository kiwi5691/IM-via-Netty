package com.IM.netty;

import com.IM.netty.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@EnableCaching
@Slf4j
@SpringBootApplication(scanBasePackages = {"com.IM.netty"})
public class IMApplication  {


    @Bean
    public SpringUtil getSpringUtil() {
        return new SpringUtil();
    }


    public static void main(String[] args) {
        SpringApplication.run(IMApplication.class, args);
    }
}
