package cc.mrbird.febs.server.ding.mapper;


import cc.mrbird.febs.common.core.entity.ding.SOaKpi;
import cc.mrbird.febs.common.core.entity.ding.SOaTeam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *  Mapper
 *
 * @author MrBird
 * @date 2021-01-02 11:43:09
 */
public interface SOaTeamMapper extends BaseMapper<SOaTeam> {

    List<Map<String, Object>> selectIsUpdate(@Param(value = "parMap") Map parMap);

    List<SOaTeam> findAll(@Param(value = "parMap") Map parMap);

    Long findAllCount(@Param(value = "parMap") Map parMap);

    List<Map<String,Object>> oaTeamAnsy(@Param(value = "parMap") Map parMap);
}
