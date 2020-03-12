//package com.cloud.member.config;
//
//import com.cloud.member.filter.FeignBasicAuthRequestInterceptor;
//import feign.Logger;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class FeignConfiguration {
//
//    /**
//     * 日志级别
//     */
//    @Bean
//    Logger.Level feignLoggerLevel() {
//        return Logger.Level.FULL;
//    }
//
//    /**
//     * 创建 Feign 请求拦截器, 在发送请求前设置认证的 Token, 各个微服务将 Token 设置 到环境变量中来达到通用的目的
//     */
//    @Bean
//    public FeignBasicAuthRequestInterceptor basicAuthRequestInterceptor() {
//        return new FeignBasicAuthRequestInterceptor();
//    }
//
//}
