package cc.mrbird.febs.server.ding.service;

import cc.mrbird.febs.common.core.entity.FebsResponse;
import cc.mrbird.febs.common.core.entity.QueryRequest;
import cc.mrbird.febs.common.core.entity.ding.SDayDetailSum;
import cc.mrbird.febs.common.core.entity.ding.SSalarySetting;
import cc.mrbird.febs.server.ding.controller.req.SKaoqinSumReq;
import cc.mrbird.febs.server.ding.controller.req.SSalarySettingReq;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *  Service接口
 *
 * @author MrBird
 * @date 2020-09-07 11:15:39
 */
public interface ISSalarySettingService extends IService<SSalarySetting> {

    SSalarySetting findSSalarySetting(SSalarySettingReq sSalarySetting);

    int updateSSalarySetting(SSalarySettingReq sSalarySetting);
}
