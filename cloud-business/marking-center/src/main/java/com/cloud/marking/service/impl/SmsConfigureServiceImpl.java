package com.cloud.marking.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.common.entity.sms.SmsConfigure;

import com.cloud.marking.mapper.SmsConfigureMapper;
import com.cloud.marking.service.ISmsConfigureService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品配置表 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-10-18
 */
@Service
public class SmsConfigureServiceImpl extends ServiceImpl<SmsConfigureMapper, SmsConfigure> implements ISmsConfigureService {

}
