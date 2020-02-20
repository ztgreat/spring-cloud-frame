package com.cloud.marking.mapper;

import com.cloud.common.entity.sms.SmsGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
public interface SmsGroupMapper extends BaseMapper<SmsGroup> {

    SmsGroup getGroupByGoodsId(Long goodsId);
}
