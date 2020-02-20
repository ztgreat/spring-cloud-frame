package com.cloud.member.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.common.entity.ums.UmsIntegrationChangeHistory;
import com.cloud.member.mapper.UmsIntegrationChangeHistoryMapper;
import com.cloud.member.service.IUmsIntegrationChangeHistoryService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 积分变化历史记录表 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Service
public class UmsIntegrationChangeHistoryServiceImpl extends ServiceImpl<UmsIntegrationChangeHistoryMapper, UmsIntegrationChangeHistory> implements IUmsIntegrationChangeHistoryService {

}