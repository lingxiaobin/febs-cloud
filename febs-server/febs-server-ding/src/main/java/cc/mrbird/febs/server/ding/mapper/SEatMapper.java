package cc.mrbird.febs.server.ding.mapper;

import cc.mrbird.febs.common.core.entity.ding.SEat;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *  Mapper
 *
 * @author MrBird
 * @date 2021-01-04 08:39:20
 */
public interface SEatMapper extends BaseMapper<SEat> {

    List<Map<String, Object>> selectIsUpdate(@Param(value = "parMap") Map parMap);

    List<SEat> findAll(@Param(value = "parMap") Map parMap);

    Long findAllCount(@Param(value = "parMap") Map parMap);

    List<Map<String,Object>> eatAnsy(@Param(value = "parMap") Map parMap);
}
