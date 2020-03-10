package com.cloud.db.properties;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "cloud.db")
public class DbProperties {

    /**
     * 是否开启多租户
     */
    private Boolean tenant = false;
}
