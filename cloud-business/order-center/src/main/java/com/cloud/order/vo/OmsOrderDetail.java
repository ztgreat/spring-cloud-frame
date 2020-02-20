package com.cloud.order.vo;


import com.cloud.common.entity.oms.OmsOrder;
import com.cloud.common.entity.oms.OmsOrderItem;
import com.cloud.common.entity.oms.OmsOrderOperateHistory;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 订单详情信息
 * https://github.com/shenzhuan/mallplus on 2018/10/11.
 */
public class OmsOrderDetail extends OmsOrder {
    @Getter
    @Setter
    private List<OmsOrderItem> orderItemList;
    @Getter
    @Setter
    private List<OmsOrderOperateHistory> historyList;
}
