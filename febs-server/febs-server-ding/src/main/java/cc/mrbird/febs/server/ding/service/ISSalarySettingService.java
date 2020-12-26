package cc.mrbird.febs.server.ding.service;

import cc.mrbird.febs.common.core.entity.FebsResponse;
import cc.mrbird.febs.common.core.entity.QueryRequest;
import cc.mrbird.febs.common.core.entity.ding.SDayDetailSum;
import cc.mrbird.febs.common.core.entity.ding.SSalarySetting;
import cc.mrbird.febs.server.ding.controller.req.SKaoqinSumReq;
import cc.mrbird.febs.server.ding.controller.req.SSalarySettingReq;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.LinkedHashMap;
import java.util.List;

/**
 *  Service接口
 *
 * @author MrBird
 * @date 2020-09-07 11:15:39
 */
public interface ISSalarySettingService extends IService<SSalarySetting> {

    LinkedHashMap<String,Integer> findSSalarySetting(SSalarySettingReq sSalarySetting);

    int updateSSalarySetting(SSalarySettingReq sSalarySetting);

    int updateSSalarySetting(String workDate,String dataType,String type,String key,Integer value);


}
