package com.cloud.goods.service;

import com.cloud.common.entity.pms.PmsProductAttributeCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.goods.vo.PmsProductAttributeCategoryItem;

import java.util.List;

/**
 * <p>
 * 产品属性分类表 服务类
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
public interface IPmsProductAttributeCategoryService extends IService<PmsProductAttributeCategory> {

    List<PmsProductAttributeCategoryItem> getListWithAttr();
}
