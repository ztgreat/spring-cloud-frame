package com.cloud.ribbon;

import com.cloud.ribbon.config.RestTemplateProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.ribbon.PropertiesFactory;
import org.springframework.context.annotation.Bean;

/**
 * Ribbon扩展配置类
 */
@Slf4j
@EnableConfigurationProperties(RestTemplateProperties.class)
public class RibbonAutoConfigure {

    public RibbonAutoConfigure() {
        log.info("============ Ribbon扩展配置加载");
    }

    @Bean
    public PropertiesFactory defaultPropertiesFactory() {
        return new PropertiesFactory();
    }
}
