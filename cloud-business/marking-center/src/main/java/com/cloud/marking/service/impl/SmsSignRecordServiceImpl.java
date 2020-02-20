package com.cloud.marking.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.common.entity.sms.SmsSignRecord;
import com.cloud.marking.mapper.SmsSignRecordMapper;
import com.cloud.marking.service.ISmsSignRecordService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 签到记录 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-10-17
 */
@Service
public class SmsSignRecordServiceImpl extends ServiceImpl<SmsSignRecordMapper, SmsSignRecord> implements ISmsSignRecordService {

}
