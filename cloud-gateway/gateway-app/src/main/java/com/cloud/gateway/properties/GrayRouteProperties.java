package com.cloud.gateway.properties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrayRouteProperties implements Serializable {
    private String version;
    private String serverName;
    private String serverGroup;
    private String active;
    private double weight = 1.0D;
}