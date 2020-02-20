package com.cloud.order.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.common.entity.oms.UmsMemberReceiveAddress;

/**
 * <p>
 * 会员收货地址表 Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
public interface UmsMemberReceiveAddressMapper extends BaseMapper<UmsMemberReceiveAddress> {

    void updateStatusByMember(Long memberId);
}
