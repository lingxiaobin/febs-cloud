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
public interface KKaoqinMapper extends BaseMapper<KUser> {

    List<KDayDetail> findKDayDetails(@Param(value = "parMap") Map parMap);

    List<KAttendance> selectAttendance(@Param(value = "parMap") Map parMap);

    KDayDetail findKDayDetailsSum(@Param(value = "parMap") Map parMap);

    KClass selectClass(@Param(value = "parMap") Map parMap);

    List<KLeave> selectLeave(@Param(value = "parMap") Map parMap);

}
