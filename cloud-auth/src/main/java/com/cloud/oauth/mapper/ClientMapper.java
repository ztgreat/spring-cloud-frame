package com.cloud.oauth.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.db.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

import com.cloud.oauth.model.Client;

/**
 * @author mall
 */
public interface ClientMapper extends SuperMapper<Client> {
    List<Client> findList(Page<Client> page, @Param("params") Map<String, Object> params );
}
