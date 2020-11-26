package cc.mrbird.febs.server.ding.mapper;


import cc.mrbird.febs.common.core.entity.ding.*;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *  Mapper
 *
 * @author MrBird
 * @date 2020-09-07 11:15:39
 */
@DS("winserver")
public interface SKaoqinSumMapper extends BaseMapper<SDayDetailSum> {


    List<SDayDetailSum> findKDayDetailsSumByFlush(@Param(value = "parMap") Map parMap);

    List<SDayDetailSum> findKDayDetailsSum(@Param(value = "parMap") Map parMap);

}
