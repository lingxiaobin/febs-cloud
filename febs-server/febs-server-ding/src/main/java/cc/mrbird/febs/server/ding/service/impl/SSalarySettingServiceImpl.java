package cc.mrbird.febs.server.ding.service.impl;

import cc.mrbird.febs.common.core.entity.FebsResponse;
import cc.mrbird.febs.common.core.entity.QueryRequest;
import cc.mrbird.febs.common.core.entity.ding.KUser;
import cc.mrbird.febs.common.core.entity.ding.SDayDetailSum;
import cc.mrbird.febs.common.core.entity.ding.SSalarySetting;
import cc.mrbird.febs.server.ding.controller.req.SKaoqinSumReq;
import cc.mrbird.febs.server.ding.controller.req.SSalarySettingReq;
import cc.mrbird.febs.server.ding.mapper.SKaoqinSumMapper;
import cc.mrbird.febs.server.ding.mapper.SOaMapper;
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

    public LinkedHashMap<String,Integer> findSSalarySetting(SSalarySettingReq sSalarySettingReq) {
        LambdaQueryWrapper<SSalarySetting> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.likeRight(SSalarySetting::getWorkDate, sSalarySettingReq.getWorkDate());
        SSalarySetting sSalarySettings = this.baseMapper.selectOne(queryWrapper);
        if (sSalarySettings!=null) {
            if (sSalarySettingReq.getType().equals("lockKaoqin")) {
                return sSalarySettings.getLockKaoqin();
            } else if (sSalarySettingReq.getType().equals("flushKaoqin")) {
                return sSalarySettings.getFlushKaoqin();
            }
        }
        return null;
    }

    @Override
    public int updateSSalarySetting(SSalarySettingReq sSalarySettingReq) {
        LambdaQueryWrapper<SSalarySetting> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.likeRight(SSalarySetting::getWorkDate, sSalarySettingReq.getWorkDate());
        SSalarySetting sSalarySettings = this.baseMapper.selectOne(queryWrapper);
        LinkedHashMap<String, Integer> lockKaoqin = null;
        if (sSalarySettingReq.getType().equals("lockKaoqin")) {
            lockKaoqin = sSalarySettings.getLockKaoqin();
        }else if (sSalarySettingReq.getType().equals("flushKaoqin")) {
            lockKaoqin = sSalarySettings.getFlushKaoqin();
        }
        lockKaoqin.put(sSalarySettingReq.getKey(), sSalarySettingReq.getValue());
        int i = baseMapper.updateById(sSalarySettings);
        return i;
    }

    @Override
    public int updateSSalarySetting(String workDate,String Type,String key,Integer value) {
        SSalarySettingReq sSalarySetting=new SSalarySettingReq();
        sSalarySetting.setType(Type);
        sSalarySetting.setWorkDate(workDate);
        sSalarySetting.setKey(key);
        sSalarySetting.setValue(value);
        return  this.updateSSalarySetting(sSalarySetting);
    }
}
