package com.cloud.gateway.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 实体
 */
@Data
@TableName("gateway_route_filter")
public class GatewayMysqlRouteFilter {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer routeId;

    private String serviceId;

    private String name;

    private String argsName;

    private String argsValue;

    private Integer sort;

    private String createdBy;

    private Date createdAt;

    private String updatedBy;

    private Date updatedAt;

    private String remarks;

    private Integer dr;


}