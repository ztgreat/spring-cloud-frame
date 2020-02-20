package com.cloud.goods.mapper;

import com.cloud.common.entity.pms.PmsProductCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.goods.vo.PmsProductCategoryWithChildrenItem;

import java.util.List;

/**
 * <p>
 * 产品分类 Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
public interface PmsProductCategoryMapper extends BaseMapper<PmsProductCategory> {

    List<PmsProductCategoryWithChildrenItem> listWithChildren();
}
