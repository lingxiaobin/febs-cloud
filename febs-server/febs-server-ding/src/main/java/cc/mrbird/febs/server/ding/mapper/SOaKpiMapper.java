package cc.mrbird.febs.server.ding.mapper;

import cc.mrbird.febs.common.core.entity.ding.SDayDetailSum;
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

    List<Map<String, Object>> selectIsUpdate(@Param(value = "parMap") Map parMap);
    List<SOaKpi> findAnsy(@Param(value = "parMap") Map parMap);

    List<SOaKpi> findAll(@Param(value = "parMap") Map parMap);

    Long findAllCount(@Param(value = "parMap") Map parMap);

    List<Map<String,Object>> oaKpiAnsy(@Param(value = "parMap") Map parMap);
}
