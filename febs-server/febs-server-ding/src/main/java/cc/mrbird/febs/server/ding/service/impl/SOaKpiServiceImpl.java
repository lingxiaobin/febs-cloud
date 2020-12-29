package cc.mrbird.febs.server.ding.service.impl;


import cc.mrbird.febs.common.core.entity.constant.PageConstant;
import cc.mrbird.febs.common.core.entity.ding.SOaAward;
import cc.mrbird.febs.common.core.entity.ding.SOaKpi;
import cc.mrbird.febs.server.ding.controller.req.FlushReq;
import cc.mrbird.febs.server.ding.controller.req.SOaKpiReq;
import cc.mrbird.febs.server.ding.mapper.SOaKpiMapper;
import cc.mrbird.febs.server.ding.mapper.SOaMapper;
import cc.mrbird.febs.server.ding.service.ISOaKpiService;
import com.baomidou.dynamic.datasource.annotation.DS;
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
import java.util.stream.Collectors;

/**
 *  Service实现
 *
 * @author MrBird
 * @date 2020-12-26 14:58:08
 */
@DS("winserver")
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SOaKpiServiceImpl extends ServiceImpl<SOaKpiMapper, SOaKpi> implements ISOaKpiService {

    private final SOaKpiMapper sOaKpiMapper;

    private final SOaMapper sOaMapper;

    public boolean flush(FlushReq flushReq) {
        Map<String, Object> parMap = new HashMap<>();
        parMap.put("workDate", flushReq.getWorkDate());
        //原本数据信息
        List<Map<String, Object>> isUpdateList = sOaKpiMapper.selectIsUpdate(parMap);
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
        List<SOaKpi> addList = new ArrayList<>();
        List<SOaKpi> updateList = new ArrayList<>();

        List<Map<String, Object>> flushList = sOaMapper.findOaKpiFlush(parMap);
        for (Map<String, Object> map : flushList) {
            SOaKpi sOaAward = new SOaKpi();
            sOaAward.setId((Long) map.get("ID"));
            sOaAward.setJobnumber((String) map.get("field0052"));
            sOaAward.setUserName((String) map.get("field0043"));
            sOaAward.setRole((String) map.get("field0049"));
            sOaAward.setKpiLevel((String) map.get("field0046"));
            sOaAward.setKpiSum((BigDecimal) map.get("field0009"));
            sOaAward.setKpiNumber((BigDecimal) map.get("field0011"));
            sOaAward.setWorkDate((Date) map.get("field0005"));
            sOaAward.setCreateTime(new Date());
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
    public Map<String, Object> findSOaKpis(QueryRequest request, SOaKpiReq sOaKpiReq) {
        Map<String, Object> parMap = new HashMap<>();
        parMap.put("workDate", sOaKpiReq.getWorkDate());
        parMap.put("isAll", request.isAll());
        if (!request.isAll()) {
            parMap.put("pageNum", (request.getPageNum() - 1) * request.getPageSize());
            parMap.put("size", request.getPageSize());
        }
        List<SOaKpi> list = sOaKpiMapper.findAll(parMap);
        Long count = sOaKpiMapper.findAllCount(parMap);
        Map<String, Object> data = new HashMap<>(3);
        data.put(PageConstant.ROWS, list);
        data.put(PageConstant.TOTAL, count);
        return data;
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
