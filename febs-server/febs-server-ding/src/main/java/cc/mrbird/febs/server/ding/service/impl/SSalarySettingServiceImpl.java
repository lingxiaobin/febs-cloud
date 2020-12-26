package cc.mrbird.febs.server.ding.service.impl;

import cc.mrbird.febs.common.core.entity.ding.*;
import cc.mrbird.febs.server.ding.common.constant.ConstantSalary;
import cc.mrbird.febs.server.ding.controller.req.SSalarySettingReq;
import cc.mrbird.febs.server.ding.mapper.SSalarySettingMapper;
import cc.mrbird.febs.server.ding.service.ISSalarySettingService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;

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


    public LinkedHashMap<String, Integer> findSSalarySetting(SSalarySettingReq sSalarySettingReq) {
        LambdaQueryWrapper<SSalarySetting> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.likeRight(SSalarySetting::getWorkDate, sSalarySettingReq.getWorkDate());
        SSalarySetting sSalarySettings = this.baseMapper.selectOne(queryWrapper);
        if (sSalarySettings != null) {
            if (sSalarySettingReq.getDataType().equals(ConstantSalary.KAO_QIN)) {
                if (sSalarySettingReq.getType().equals("get")) {
                    return sSalarySettings.getLockKaoqin();
                } else if (sSalarySettingReq.getType().equals("flush")) {
                    return sSalarySettings.getFlushKaoqin();
                }
            } else if (sSalarySettingReq.getDataType().equals(ConstantSalary.OA_AWARD)) {
                if (sSalarySettingReq.getType().equals("get")) {
                    return sSalarySettings.getLockOaAward();
                } else if (sSalarySettingReq.getType().equals("flush")) {
                    return sSalarySettings.getFlushOaAward();
                }
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
        if (sSalarySettingReq.getDataType().equals(ConstantSalary.KAO_QIN)) {
            if (sSalarySettingReq.getType().equals("get")) {
                lockKaoqin = sSalarySettings.getLockKaoqin();
            } else if (sSalarySettingReq.getType().equals("flush")) {
                lockKaoqin = sSalarySettings.getFlushKaoqin();
            }
        } else if (sSalarySettingReq.getDataType().equals(ConstantSalary.OA_AWARD)) {
            if (sSalarySettingReq.getType().equals("get")) {
                lockKaoqin = sSalarySettings.getLockOaAward();
            } else if (sSalarySettingReq.getType().equals("flush")) {
                lockKaoqin = sSalarySettings.getFlushOaAward();
            }
        }
        lockKaoqin.put(sSalarySettingReq.getKey(), sSalarySettingReq.getValue());
        int i = baseMapper.updateById(sSalarySettings);
        return i;
    }

    @Override
    public int updateSSalarySetting(String workDate, String dataType,String type, String key, Integer value) {
        SSalarySettingReq sSalarySetting = new SSalarySettingReq();
        sSalarySetting.setDataType(dataType);
        sSalarySetting.setType(type);
        sSalarySetting.setWorkDate(workDate);
        sSalarySetting.setKey(key);
        sSalarySetting.setValue(value);
        return this.updateSSalarySetting(sSalarySetting);
    }
}
