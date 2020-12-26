package cc.mrbird.febs.server.ding.mapper;

import cc.mrbird.febs.common.core.entity.ding.SOaAward;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *  Mapper
 *
 * @author MrBird
 * @date 2020-12-01 18:07:41
 */
public interface SOaAwardMapper extends BaseMapper<SOaAward> {

    List<Map<String, Object>> selectIsUpdate(@Param(value = "parMap") Map parMap);

    List<SOaAward> findOaAward(@Param(value = "parMap") Map parMap);
    Long findOaAwardCount(@Param(value = "parMap") Map parMap);
    List<Map<String,Object>> sumOaAwardAdd(@Param(value = "parMap") Map parMap);
    List<Map<String,Object>> sumOaAwardSub(@Param(value = "parMap") Map parMap);
}
