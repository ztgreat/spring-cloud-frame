package com.cloud.goods.service.impl;

import com.cloud.common.entity.pms.PmsProductLadder;
import com.cloud.goods.mapper.PmsProductLadderMapper;
import com.cloud.goods.service.IPmsProductLadderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 产品阶梯价格表(只针对同商品) 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Service
public class PmsProductLadderServiceImpl extends ServiceImpl<PmsProductLadderMapper, PmsProductLadder> implements IPmsProductLadderService {

}
