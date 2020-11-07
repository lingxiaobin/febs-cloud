package cc.mrbird.febs.server.ding.mapper;


import cc.mrbird.febs.common.core.entity.ding.EWiplineUser;
import cc.mrbird.febs.common.core.entity.ding.KUser;
import cc.mrbird.febs.common.core.entity.ding.Sale;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Mapper
 *
 * @author MrBird
 * @date 2020-09-07 11:15:39
 */

public interface WiplineMapper extends BaseMapper<KUser> {

    @DS("erpserver")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    List<Map<String, Object>> selectWipline(@Param(value = "parMap") Map parMap);

    EWiplineUser selectWiplineUser(@Param(value = "userName") String userName);

}
