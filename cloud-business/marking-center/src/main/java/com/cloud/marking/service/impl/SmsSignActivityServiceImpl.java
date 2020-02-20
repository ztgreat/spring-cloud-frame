package com.cloud.marking.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.common.entity.sms.SmsSignActivity;
import com.cloud.marking.mapper.SmsSignActivityMapper;
import com.cloud.marking.service.ISmsSignActivityService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 签到活动 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-10-17
 */
@Service
public class SmsSignActivityServiceImpl extends ServiceImpl<SmsSignActivityMapper, SmsSignActivity> implements ISmsSignActivityService {

}
