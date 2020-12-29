package cc.mrbird.febs.server.ding.mapper;


import cc.mrbird.febs.common.core.entity.ding.SDayDetailSum;
import cc.mrbird.febs.common.core.entity.ding.SOaKpi;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 *  Mapper
 *
 * @author MrBird
 * @date 2020-09-07 11:15:39
 */
@DS("oaserver")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public interface SOaMapper extends BaseMapper<SDayDetailSum> {


    List<Map<String,Object>> findOaAwardFlush(@Param(value = "parMap") Map parMap);

    List<Map<String,Object>> findOaKpiFlush(@Param(value = "parMap") Map parMap);

}
