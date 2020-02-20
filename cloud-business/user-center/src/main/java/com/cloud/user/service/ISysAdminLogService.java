package com.cloud.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.common.model.SysAdminLog;
import com.cloud.common.vo.LogParam;
import com.cloud.common.vo.LogStatisc;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zscat
 * @since 2019-04-14
 */
public interface ISysAdminLogService extends IService<SysAdminLog> {
     List<LogStatisc> selectPageExt(LogParam entity);
}
