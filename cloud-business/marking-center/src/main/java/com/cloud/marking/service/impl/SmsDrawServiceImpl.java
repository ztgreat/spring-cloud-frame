package com.cloud.marking.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.common.entity.sms.SmsDraw;
import com.cloud.marking.mapper.SmsDrawMapper;
import com.cloud.marking.service.ISmsDrawService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 一分钱抽奖 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-10-17
 */
@Service
public class SmsDrawServiceImpl extends ServiceImpl<SmsDrawMapper, SmsDraw> implements ISmsDrawService {

}
