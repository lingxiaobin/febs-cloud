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
public interface SKaoqinSumMapper extends BaseMapper<SDayDetailSum> {


    List<Map<String, Object>> selectIsUpdate(@Param(value = "parMap") Map parMap);
    //获取考勤详情表的汇总数据
    List<SDayDetailSum> findKDayDetailsSumByFlush(@Param(value = "parMap") Map parMap);
    //同步到薪资汇总表
    List<SDayDetailSum> findAnsy(@Param(value = "parMap") Map parMap);
    //查询
    List<SDayDetailSum> findKDayDetailsSum(@Param(value = "parMap") Map parMap);
    Long countKDayDetailsSum(@Param(value = "parMap") Map parMap);
    //无需打卡组
    List<Map<String, Object>> noKaoqinUser(@Param(value = "parMap") Map parMap);
    //获取当月的出勤天数, 白班加夜班
    List<Map<String, Object>> findDayCount(@Param(value = "parMap") Map parMap);

    //获取指定部门,班次的的考勤明细,判断用来计算伙食补助
    Map<String, Object> findKClass(@Param(value = "parMap") Map parMap);
    List<Map<String, Object>> findDayDetailbyEat(@Param(value = "parMap") Map parMap);
}
