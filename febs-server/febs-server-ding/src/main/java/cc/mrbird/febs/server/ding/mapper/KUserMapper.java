package cc.mrbird.febs.server.ding.mapper;


import cc.mrbird.febs.common.core.entity.ding.DeptNum;
import cc.mrbird.febs.common.core.entity.ding.K24680;
import cc.mrbird.febs.common.core.entity.ding.KUser;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  Mapper
 *
 * @author MrBird
 * @date 2020-09-07 11:15:39
 */
@DS("winserver")
public interface KUserMapper extends BaseMapper<KUser> {


    List<DeptNum> selectDetpsNum(String parentId);

    List<KUser> selectUsersNum(String deptId);

    List<Map<String,Object>> selectUsersLikeName(@Param(value = "parMap") Map parMap);


    List<K24680> selectK24680Where(@Param(value = "parMap") Map parMap);

}
