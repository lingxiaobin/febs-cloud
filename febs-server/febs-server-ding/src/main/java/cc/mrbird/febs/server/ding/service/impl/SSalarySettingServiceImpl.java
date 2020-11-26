package cc.mrbird.febs.server.ding.service.impl;

import cc.mrbird.febs.common.core.entity.FebsResponse;
import cc.mrbird.febs.common.core.entity.QueryRequest;
import cc.mrbird.febs.common.core.entity.ding.KUser;
import cc.mrbird.febs.common.core.entity.ding.SDayDetailSum;
import cc.mrbird.febs.common.core.entity.ding.SSalarySetting;
import cc.mrbird.febs.server.ding.controller.req.SKaoqinSumReq;
import cc.mrbird.febs.server.ding.controller.req.SSalarySettingReq;
import cc.mrbird.febs.server.ding.mapper.SKaoqinSumMapper;
import cc.mrbird.febs.server.ding.mapper.SSalarySettingMapper;
import cc.mrbird.febs.server.ding.service.ISKaoqinSumService;
import cc.mrbird.febs.server.ding.service.ISSalarySettingService;
import com.alibaba.nacos.api.config.filter.IFilterConfig;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Service实现
 *
 * @author MrBird
 * @date 2020-09-07 11:15:39
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SSalarySettingServiceImpl extends ServiceImpl<SSalarySettingMapper, SSalarySetting> implements ISSalarySettingService {

    private final SKaoqinSumMapper sKaoqinSumMapper;


    public SSalarySetting findSSalarySetting(SSalarySettingReq sSalarySetting) {
        LambdaQueryWrapper<SSalarySetting> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.likeRight(SSalarySetting::getWorkDate, sSalarySetting.getWorkDate());
        SSalarySetting sSalarySettings = this.baseMapper.selectOne(queryWrapper);
        return sSalarySettings;
    }

    @Override
    public int updateSSalarySetting(SSalarySettingReq sSalarySettingReq) {
        LambdaQueryWrapper<SSalarySetting> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.likeRight(SSalarySetting::getWorkDate, sSalarySettingReq.getWorkDate());
        SSalarySetting sSalarySettings = this.baseMapper.selectOne(queryWrapper);
        LinkedHashMap<String, Integer> lockKaoqin = null;
        if (sSalarySettingReq.getType().equals("lockKaoqin")) {
            lockKaoqin = sSalarySettings.getLockKaoqin();
        }
        lockKaoqin.put(sSalarySettingReq.getKey(), sSalarySettingReq.getValue() == 0 ? 1 : 0);
        int i = baseMapper.updateById(sSalarySettings);
        return i;
    }


}
