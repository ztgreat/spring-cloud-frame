package com.cloud.gateway.domain.DTO;

import com.cloud.gateway.domain.GatewayMysqlRouteFilter;
import com.cloud.gateway.domain.GatewayMysqlRoutePredicate;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 实体
 */
@Data
public class GatewayMysqlRouteDTO {

    private Integer id;

    private String serviceId;

    private String uri;

    private Integer sort;

    private List<GatewayMysqlRoutePredicate> predicates;

    private List<GatewayMysqlRouteFilter> filters;

    private String createdBy;

    private Date createdAt;

    private String updatedBy;

    private Date updatedAt;

    private String remarks;

    private Integer dr;


}