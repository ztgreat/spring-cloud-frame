package com.cloud.order.service.impl;

import com.cloud.common.entity.oms.OmsOrderOperateHistory;
import com.cloud.order.mapper.OmsOrderOperateHistoryMapper;
import com.cloud.order.service.IOmsOrderOperateHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单操作历史记录 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-04-17
 */
@Service
public class OmsOrderOperateHistoryServiceImpl extends ServiceImpl<OmsOrderOperateHistoryMapper, OmsOrderOperateHistory> implements IOmsOrderOperateHistoryService {

}
