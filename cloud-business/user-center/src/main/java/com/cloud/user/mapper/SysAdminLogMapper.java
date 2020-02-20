package com.cloud.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.common.model.SysAdminLog;
import com.cloud.common.vo.LogParam;
import com.cloud.common.vo.LogStatisc;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zscat
 * @since 2019-04-14
 */
public interface SysAdminLogMapper extends BaseMapper<SysAdminLog> {

   List<LogStatisc> getLogStatisc(LogParam entity);


}
