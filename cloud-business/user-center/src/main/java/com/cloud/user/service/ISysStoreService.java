package com.cloud.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.common.model.SysStore;


/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zscat
 * @since 2019-05-18
 */
public interface ISysStoreService extends IService<SysStore> {


    boolean saveStore(SysStore entity);
}
