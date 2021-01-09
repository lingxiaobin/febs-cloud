package cc.mrbird.febs.server.ding.service.impl;


import cc.mrbird.febs.common.core.entity.constant.PageConstant;
import cc.mrbird.febs.common.core.entity.ding.SOaKpi;
import cc.mrbird.febs.common.core.entity.ding.SOaTeam;
import cc.mrbird.febs.common.core.utils.DateUtil;
import cc.mrbird.febs.server.ding.controller.req.FlushReq;
import cc.mrbird.febs.server.ding.controller.req.SOaKpiReq;
import cc.mrbird.febs.server.ding.mapper.SOaMapper;
import cc.mrbird.febs.server.ding.mapper.SOaTeamMapper;
import cc.mrbird.febs.server.ding.service.ISOaTeamService;
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
import java.text.ParseException;
import java.util.*;

/**
 * Service实现
 *
 * @author MrBird
 * @date 2021-01-02 11:43:09
 */
@DS("winserver")
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SOaTeamServiceImpl extends ServiceImpl<SOaTeamMapper, SOaTeam> implements ISOaTeamService {

    private final SOaTeamMapper sOaTeamMapper;
    private final SOaMapper sOaMapper;

    public boolean flush(FlushReq flushReq) throws ParseException {
        Map<String, Object> parMap = new HashMap<>();
        parMap.put("workDate", flushReq.getWorkDate());
        //原本数据信息
        List<Map<String, Object>> isUpdateList = sOaTeamMapper.selectIsUpdate(parMap);
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
        List<SOaTeam> addList = new ArrayList<>();
        List<SOaTeam> updateList = new ArrayList<>();

        List<SOaTeam> flushList = sOaMapper.findOaTeamFlush(parMap);
        for (SOaTeam soa : flushList) {
            soa.setJobnumber(soa.getWorkNumber());
            soa.setWorkDate(DateUtil.getDateParse(soa.getPeriodTime(), "yyyy-MM"));
            soa.setCreateTime(new Date());
            if (updateSet01.contains(soa.getId().toString())) {
                soa.setIsUpdate(1);
                updateList.add(soa);
            } else if (updateSet_1.contains(soa.getId().toString())) {
            } else {
                soa.setIsUpdate(0);
                addList.add(soa);
            }
        }
        this.saveBatch(addList);
        if (updateList.size() > 0) {
            this.updateBatchById(updateList);
        }
        return true;
    }

    @Override
    public Map<String, Object> findSOaTeams(QueryRequest request, SOaKpiReq req) {
        Map<String, Object> parMap = new HashMap<>();
        parMap.put("workDate", req.getWorkDate());
        if (req.getPayPlaces().length > 0) {
            parMap.put("payPlaces", req.getPayPlaces());
        }
        if (req.getPayComputeTypes() != null && req.getPayComputeTypes().length > 0) {
            parMap.put("payComputeTypes", req.getPayComputeTypes());
        }
        parMap.put("isAll", request.isAll());
        if (!request.isAll()) {
            parMap.put("pageNum", (request.getPageNum() - 1) * request.getPageSize());
            parMap.put("size", request.getPageSize());
        }
        List<SOaTeam> list = sOaTeamMapper.findAll(parMap);
        Long count = 0L;
        if (!request.isAll()) {
            count = sOaTeamMapper.findAllCount(parMap);
        }
        Map<String, Object> data = new HashMap<>(3);
        data.put(PageConstant.ROWS, list);
        data.put(PageConstant.TOTAL, count);
        return data;
    }

    @Override
    public List<SOaTeam> findSOaTeams(SOaTeam sOaTeam) {
        LambdaQueryWrapper<SOaTeam> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createSOaTeam(SOaTeam sOaTeam) {
        this.save(sOaTeam);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSOaTeam(SOaTeam sOaTeam) {
        this.saveOrUpdate(sOaTeam);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSOaTeam(SOaTeam sOaTeam) {
        LambdaQueryWrapper<SOaTeam> wapper = new LambdaQueryWrapper<>();
        // TODO 设置删除条件
        this.remove(wapper);
    }
}
