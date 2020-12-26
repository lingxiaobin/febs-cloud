package cc.mrbird.febs.server.ding.mapper;


import cc.mrbird.febs.common.core.entity.ding.SAll;
import cc.mrbird.febs.common.core.entity.ding.SDayDetailSum;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 *  Mapper
 *
 * @author MrBird
 * @date 2020-12-04 14:33:28
 */
@DS("winserver")
public interface SAllMapper extends BaseMapper<SAll> {
    List<String> findSAllByIds(@Param(value = "parMap") Map parMap);

    List<SAll> findSAll(@Param(value = "parMap") Map parMap);
    Long countSAll(@Param(value = "parMap") Map parMap);

    Integer updateByKaoqin(@Param("emps")  List<SDayDetailSum> sDayDetailSum);

    Integer insertBathByKaoqin(@Param("emps") Collection<SDayDetailSum> values);


    Integer updateByOaAward(@Param("emps")  List<Map<String, Object>> lists);

    Integer insertBathByOaAward(@Param("emps") List<Map<String, Object>> lists);

}
