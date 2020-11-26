package cc.mrbird.febs.server.ding.mapper;

import cc.mrbird.febs.common.core.entity.ding.KLeave;
import cc.mrbird.febs.common.core.entity.ding.PClass;
import cc.mrbird.febs.common.core.entity.ding.PClassDetail;
import cc.mrbird.febs.common.core.entity.ding.PClassDetailAll;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *  Mapper
 *
 * @author MrBird
 * @date 2020-11-06 08:37:21
 */
public interface PClassDetailMapper extends BaseMapper<PClassDetail> {

    List<PClass> selectPClass(@Param(value = "parMap") Map parMap);

    Long selectPClassCount(@Param(value = "parMap") Map parMap);

    List<PClassDetailAll> selectPClassDetail(@Param(value = "parMap") Map parMap);

    Long selectPClassDetailCount(@Param(value = "parMap") Map parMap);

    List<Map<String,Object>> selectPClassDetailStateIn(@Param(value = "parMap") Map parMap);


}
