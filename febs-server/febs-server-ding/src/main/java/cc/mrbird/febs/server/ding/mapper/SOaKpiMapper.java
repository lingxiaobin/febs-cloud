package cc.mrbird.febs.server.ding.mapper;

import cc.mrbird.febs.common.core.entity.ding.SOaKpi;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *  Mapper
 *
 * @author MrBird
 * @date 2020-12-26 14:58:08
 */
public interface SOaKpiMapper extends BaseMapper<SOaKpi> {
    List<Map<String,Object>> flush(@Param(value = "parMap") Map parMap);
}
