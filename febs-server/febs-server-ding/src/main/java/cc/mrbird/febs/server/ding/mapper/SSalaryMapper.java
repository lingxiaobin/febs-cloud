package cc.mrbird.febs.server.ding.mapper;


import cc.mrbird.febs.common.core.entity.ding.SDayDetailSum;
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
public interface SSalaryMapper{

    List<Map<String, Object>> selectFlushState(@Param(value = "parMap") Map parMap);


    List<Map<String, Object>> selectFlushStateByAll(@Param(value = "parMap") Map parMap);
}
