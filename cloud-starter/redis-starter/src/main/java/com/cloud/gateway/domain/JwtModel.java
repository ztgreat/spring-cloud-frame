package com.cloud.gateway.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 描述:jwt 模型
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtModel {

    private String userName;

    private List<String> roleIdList;

}
