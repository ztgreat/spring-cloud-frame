package com.cloud.gateway.domain;

import lombok.Data;

import java.util.Date;

/**
 * 实体
 */
@Data
public class GatewayMysqlRoute {

    private Long id;

    private String serviceId;

    private String uri;

    private String predicates;

    private String filters;

    private String sort;

    private String createdBy;

    private Date createdAt;

    private String updatedBy;

    private Date updatedAt;

    private String remarks;

    private Integer dr;


}