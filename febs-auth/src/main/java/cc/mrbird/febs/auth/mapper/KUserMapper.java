package cc.mrbird.febs.auth.mapper;

import cc.mrbird.febs.auth.entity.KUser;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 *  Mapper
 *
 * @author MrBird
 * @date 2020-04-24 16:36:41
 */
@DS("winserver")
public interface KUserMapper extends BaseMapper<KUser> {

}
