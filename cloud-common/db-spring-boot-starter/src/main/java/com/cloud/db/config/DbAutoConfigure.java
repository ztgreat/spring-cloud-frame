package com.cloud.db.config;


import com.cloud.db.properties.DbProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(DbProperties.class)
@Slf4j
public class DbAutoConfigure {


    public DbAutoConfigure() {
        log.info("============ DB 配置加载");
    }
}
