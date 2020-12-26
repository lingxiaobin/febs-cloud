package cc.mrbird.febs.server.ding.service.impl;


import cc.mrbird.febs.common.core.entity.ding.SOaAward;
import cc.mrbird.febs.common.core.entity.ding.SOaKpi;
import cc.mrbird.febs.server.ding.controller.req.FlushReq;
import cc.mrbird.febs.server.ding.mapper.SOaKpiMapper;
import cc.mrbird.febs.server.ding.service.ISOaKpiService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cc.mrbird.febs.common.core.entity.QueryRequest;

import java.math.BigDecimal;
import java.util.*;

/**
 *  Service实现
 *
 * @author MrBird
 * @date 2020-12-26 14:58:08
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SOaKpiServiceImpl extends ServiceImpl<SOaKpiMapper, SOaKpi> implements ISOaKpiService {

    private final SOaKpiMapper sOaKpiMapper;

    public boolean flush(FlushReq flushReq) {
        Map<String, Object> parMap = new HashMap<>();
        parMap.put("workDate", flushReq.getWorkDate());
        //原本数据信息
        List<Map<String, Object>> isUpdateList = sOaAwardMapper.selectIsUpdate(parMap);
        Set<String> updateSet01 = new HashSet<>();
        Set<String> updateSet_1 = new HashSet<>();
        for (Map<String, Object> map : isUpdateList) {
            String id = map.get("id").toString();
            Integer isUpdate = (Integer) map.get("is_update");
            if (isUpdate == -1) {
                updateSet_1.add(id);
            } else {
                updateSet01.add(id);
            }
        }
        List<SOaAward> addList = new ArrayList<>();
        List<SOaAward> updateList = new ArrayList<>();

        List<Map<String, Object>> oaAwardFlush = sOaMapper.findOaAwardFlush(parMap);
        List<SOaAward> sOaAwards = new ArrayList<>();
        for (Map<String, Object> map : oaAwardFlush) {
            SOaAward sOaAward = new SOaAward();
            sOaAward.setId((Long) map.get("bid"));
            sOaAward.setUnitId((Long) map.get("aid"));
            sOaAward.setFinishedflag((Integer) map.get("finishedflag"));
            sOaAward.setFactory((String) map.get("获取需求工厂值"));
            sOaAward.setAwardType((String) map.get("罚款类别"));
            sOaAward.setFromUser((String) map.get("发起人"));
            sOaAward.setWorkDate((Date) map.get("时间"));
            sOaAward.setFromUser((String) map.get("发起人"));
//            sOaAward.setFromUser(map.get("获取责任工序负责人"));
            sOaAward.setToAddUser((String) map.get("奖励人"));
            sOaAward.setToAddJobnumber((String) map.get("奖励人工号"));
            sOaAward.setToSubUser((String) map.get("罚款人"));
            sOaAward.setToSubJobnumber((String) map.get("罚款人工号"));
            try {
                sOaAward.setMoneyAdd((BigDecimal) map.get("奖励金额"));
            } catch (Exception e) {
            }
//            sOaAward.setMoneyAddText((String)map.get("奖励金额"));
            try {
                sOaAward.setMoneySub((BigDecimal) map.get("罚款金额"));
            } catch (Exception e) {
            }
//            sOaAward.setMoneySubText((String)map.get("罚款金额"));
            sOaAward.setErrorDetail((String) map.get("品质异常描述"));
            sOaAward.setErrorSolveIdea((String) map.get("品质异常处理方案"));

            if (updateSet01.contains(sOaAward.getId().toString())) {
                sOaAward.setIsUpdate(1);
                updateList.add(sOaAward);
            } else if (updateSet_1.contains(sOaAward.getId().toString())) {
            } else {
                sOaAward.setIsUpdate(0);
                addList.add(sOaAward);
            }
        }
        this.saveBatch(addList);
        if (updateList.size() > 0) {
            this.updateBatchById(updateList);
        }
        return true;
    }

    @Override
    public IPage<SOaKpi> findSOaKpis(QueryRequest request, SOaKpi sOaKpi) {
        LambdaQueryWrapper<SOaKpi> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        Page<SOaKpi> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<SOaKpi> findSOaKpis(SOaKpi sOaKpi) {
        LambdaQueryWrapper<SOaKpi> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createSOaKpi(SOaKpi sOaKpi) {
        this.save(sOaKpi);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSOaKpi(SOaKpi sOaKpi) {
        this.saveOrUpdate(sOaKpi);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSOaKpi(SOaKpi sOaKpi) {
        LambdaQueryWrapper<SOaKpi> wapper = new LambdaQueryWrapper<>();
        // TODO 设置删除条件
        this.remove(wapper);
    }
}
