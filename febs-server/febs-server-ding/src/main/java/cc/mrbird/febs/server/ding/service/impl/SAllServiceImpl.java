package cc.mrbird.febs.server.ding.service.impl;


import cc.mrbird.febs.common.core.entity.constant.PageConstant;
import cc.mrbird.febs.common.core.entity.ding.SAll;
import cc.mrbird.febs.common.core.entity.ding.SDayDetailSum;
import cc.mrbird.febs.common.core.utils.DateUtil;
import cc.mrbird.febs.server.ding.common.constant.ConstantSalary;
import cc.mrbird.febs.server.ding.controller.req.BaseReq;
import cc.mrbird.febs.server.ding.controller.req.FlushReq;
import cc.mrbird.febs.server.ding.controller.req.SAllReq;
import cc.mrbird.febs.server.ding.mapper.*;
import cc.mrbird.febs.server.ding.service.ISAllService;
import cc.mrbird.febs.server.ding.service.ISOaKpiService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import cc.mrbird.febs.common.core.entity.QueryRequest;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

/**
 * Service实现
 *
 * @author MrBird
 * @date 2020-12-04 14:33:28
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SAllServiceImpl extends ServiceImpl<SAllMapper, SAll> implements ISAllService {


    private final SAllMapper sAllMapper;
    private final SKaoqinSumMapper sKaoqinSumMapper;
    private final SOaAwardMapper sOaAwardMapper;
    private final SOaKpiMapper sOaKpiMapper;
    private final SOaTeamMapper sOaTeamMapper;
    private final SEatMapper sEatMapper;

    @Override
    public Map<String, Object> findSAlls(QueryRequest request, SAllReq req) {
        Map<String, Object> parMap = new HashMap<>();
        JSONArray jsonArray = JSON.parseArray(req.getDeptsAndUsers());
        if (jsonArray.size() > 0) {
            List<String> deptIds = new ArrayList<>();
            List<String> userIds = new ArrayList<>();
            for (Object o : jsonArray) {
                JSONObject oo = JSON.parseObject(o.toString());
                if (oo.getIntValue("type") == 1) {
                    deptIds.add(oo.getString("id"));
                } else {
                    userIds.add(oo.getString("id"));
                }

            }
            parMap.put("deptIds", deptIds);
            parMap.put("userIds", userIds);
        }

        parMap.put("workDate", req.getWorkDate());
        if (req.getPayPlaces().length > 0) {
            parMap.put("payPlaces", req.getPayPlaces());
        }
        if (req.getPayComputeTypes() != null && req.getPayComputeTypes().length > 0) {
            parMap.put("payComputeTypes", req.getPayComputeTypes());
        }
        //筛选框  暂时只有一个选项  直接大于0判断
        if (req.getOaAwardSum() != null && req.getOaAwardSum().length > 0) {
            parMap.put("oaAwardSum", true);
        }
        if (req.getOaKpiNumber() != null && req.getOaKpiNumber().length > 0) {
            parMap.put("oaKpiNumber", true);
        }
        if (req.getOaTeamPieceRate() != null && req.getOaTeamPieceRate().length > 0) {
            parMap.put("oaTeamPieceRate", true);
        }
        if (req.getEatNum() != null && req.getEatNum().length > 0) {
            parMap.put("eatNum", true);
        }

        if (!request.isAll()) {
            parMap.put("pageNum", (request.getPageNum() - 1) * request.getPageSize());
            parMap.put("size", request.getPageSize());
        }
        parMap.put("isAll", request.isAll());
        List<SAll> kDayDetailsSum = sAllMapper.findSAll(parMap);
        Long aLong = 0L;
        if (!request.isAll()) {
            aLong = sAllMapper.countSAll(parMap);
        }
        Map<String, Object> data = new HashMap<>(2);
        data.put(PageConstant.ROWS, kDayDetailsSum);
        data.put(PageConstant.TOTAL, aLong);

        return data;
    }

    @Override
    public List<SAll> findSAlls(SAll sAll) {
        LambdaQueryWrapper<SAll> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createSAll(SAll sAll) {
        this.save(sAll);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSAll(SAll sAll) {
        this.saveOrUpdate(sAll);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSAll(SAll sAll) {
        LambdaQueryWrapper<SAll> wapper = new LambdaQueryWrapper<>();
        // TODO 设置删除条件
        this.remove(wapper);
    }

    @Override
    public Boolean ayncDate(FlushReq flushReq) throws ParseException {
        Map<String, Object> parMap = new HashMap<>();
        parMap.put("workDate", flushReq.getWorkDate());
        List<String> sAlls = this.sAllMapper.findSAllByIds(parMap);
        parMap.put("payPlaces", flushReq.getPayPlaces());
        if (flushReq.getDataType().equals(ConstantSalary.KAO_QIN)) {
            ansyKaoqin(sAlls, parMap);
        } else if (flushReq.getDataType().equals(ConstantSalary.OA_AWARD)) {
            ansyOaAward(sAlls, parMap);
        } else if (flushReq.getDataType().equals(ConstantSalary.OA_KPI)) {
            ansyOaKpi(sAlls, parMap);
        } else if (flushReq.getDataType().equals(ConstantSalary.OA_TEAM)) {
            ansyOaTeam(sAlls, parMap);
        } else if (flushReq.getDataType().equals(ConstantSalary.EAT)) {
            ansyEat(sAlls, parMap);
        }
        log.info("同步成功!!!");
        return true;
    }

    private void ansyKaoqin(List<String> sAlls, Map<String, Object> parMap) throws ParseException {
        List<SDayDetailSum> sDayDetailSums = sKaoqinSumMapper.findAnsy(parMap);
        Map<String, SDayDetailSum> kaoqinMap = new HashMap<>();
        for (SDayDetailSum sDayDetailSum : sDayDetailSums) {
            sDayDetailSum.setWorkDate(DateUtil.getDateParse((String) parMap.get("workDate"), "yyyy-MM"));   //重置时间为当月的第一天
            kaoqinMap.put(sDayDetailSum.getId(), sDayDetailSum);
        }
        List<SDayDetailSum> updateList = new ArrayList<>();
        for (String id : sAlls) {
            SDayDetailSum sDayDetailSum = kaoqinMap.get(id);
            if (sDayDetailSum != null) {
                updateList.add(sDayDetailSum);
                kaoqinMap.remove(id);
            }
            if (updateList.size() == 500) {
                this.baseMapper.updateByKaoqin(updateList);
                updateList.clear();
            }
        }
        if (updateList.size() > 0) {
            this.baseMapper.updateByKaoqin(updateList);
        }
        Collection<SDayDetailSum> values = kaoqinMap.values();
        if (values.size() > 0) {
            this.baseMapper.insertBathByKaoqin(values);
        }
    }

    private void ansyOaAward(List<String> sAlls, Map<String, Object> parMap) throws ParseException {
        String workDate = (String) parMap.get("workDate");
        this.baseMapper.clearByOaAward(parMap); //清除当月全部数据
        List<Map<String, Object>> listAdd = sOaAwardMapper.sumOaAwardAddAnsy(parMap);
        List<Map<String, Object>> listSub = sOaAwardMapper.sumOaAwardSubAnsy(parMap);
        Map<String, BigDecimal> mapAll = new HashMap<>();
        for (Map<String, Object> map : listAdd) {
            mapAll.put((String) map.get("id"), ((BigDecimal) map.get("addSum")));
        }
        for (Map<String, Object> map : listSub) {
            BigDecimal bigDecimal = mapAll.get(map.get("id"));
            if (bigDecimal != null) {
                mapAll.put((String) map.get("id"), bigDecimal.subtract((BigDecimal) map.get("subSum")));
            } else {
                mapAll.put((String) map.get("id"), ((BigDecimal) map.get("subSum")).negate());
            }
        }
        List<Map<String, Object>> updateList = new ArrayList<>();
        List<Map<String, Object>> saveList = new ArrayList<>();
        for (String id : sAlls) {
            BigDecimal decimal = mapAll.get(id.substring(7));
            if (decimal != null) {
                Map<String, Object> map = new HashMap<>(2);
                map.put("id", id);
                map.put("oaAwardSum", decimal);
                updateList.add(map);
                mapAll.remove(id.substring(7));
            }
            if (updateList.size() == 500) {
                this.baseMapper.updateByOaAward(updateList);
                updateList.clear();
            }
        }
        if (updateList.size() > 0) {
            this.baseMapper.updateByOaAward(updateList);
        }
        for (Map.Entry<String, BigDecimal> entry : mapAll.entrySet()) {
            Map<String, Object> map = new HashMap<>(2);
            map.put("id", workDate + entry.getKey());
            map.put("userid", entry.getKey());
            map.put("workDate", DateUtil.getDateParse(workDate, "yyyy-MM"));
            map.put("oaAwardSum", entry.getValue());
            saveList.add(map);
        }
        if (saveList.size() > 0) {
            this.baseMapper.insertBathByOaAward(saveList);
        }
    }

    private void ansyOaKpi(List<String> sAlls, Map<String, Object> parMap) throws ParseException {
        String workDate = (String) parMap.get("workDate");
        this.baseMapper.clearByOaKpi(parMap); //清除当月全部数据
        List<Map<String, Object>> list = sOaKpiMapper.oaKpiAnsy(parMap);
        Map<String, BigDecimal> mapAll = new HashMap<>();
        for (Map<String, Object> map : list) {
            mapAll.put((String) map.get("id"), ((BigDecimal) map.get("kpi_number")));
        }
        List<Map<String, Object>> updateList = new ArrayList<>();
        List<Map<String, Object>> saveList = new ArrayList<>();
        for (String id : sAlls) {
            BigDecimal decimal = mapAll.get(id.substring(7));
            if (decimal != null) {
                Map<String, Object> map = new HashMap<>(2);
                map.put("id", id);
                map.put("oaKpiNumber", decimal);
                updateList.add(map);
                mapAll.remove(id.substring(7));
            }
            if (updateList.size() == 500) {
                this.baseMapper.updateByOaKpi(updateList);
                updateList.clear();
            }
        }
        if (updateList.size() > 0) {
            this.baseMapper.updateByOaKpi(updateList);
        }
        for (Map.Entry<String, BigDecimal> entry : mapAll.entrySet()) {
            Map<String, Object> map = new HashMap<>(2);
            map.put("id", workDate + entry.getKey());
            map.put("userid", entry.getKey());
            map.put("workDate", DateUtil.getDateParse(workDate, "yyyy-MM"));
            map.put("oaKpiNumber", entry.getValue());
            saveList.add(map);
        }
        if (saveList.size() > 0) {
            this.baseMapper.insertBathByOaKpi(saveList);
        }
    }

    private void ansyOaTeam(List<String> sAlls, Map<String, Object> parMap) throws ParseException {
        String workDate = (String) parMap.get("workDate");
        this.baseMapper.clearByOaTeam(parMap); //清除当月全部数据
        List<Map<String, Object>> list = sOaTeamMapper.oaTeamAnsy(parMap);
        Map<String, BigDecimal> mapAll = new HashMap<>();
        for (Map<String, Object> map : list) {
            if (map.get("piece_rate") != null) {
                mapAll.put((String) map.get("id"), new BigDecimal((String) map.get("piece_rate")));
            }
        }
        List<Map<String, Object>> updateList = new ArrayList<>();
        List<Map<String, Object>> saveList = new ArrayList<>();
        for (String id : sAlls) {
            BigDecimal decimal = mapAll.get(id.substring(7));
            if (decimal != null) {
                Map<String, Object> map = new HashMap<>(2);
                map.put("id", id);
                map.put("oaTeamPieceRate", decimal);
                updateList.add(map);
                mapAll.remove(id.substring(7));
            }
            if (updateList.size() == 500) {
                this.baseMapper.updateByOaTeam(updateList);
                updateList.clear();
            }
        }
        if (updateList.size() > 0) {
            this.baseMapper.updateByOaTeam(updateList);
        }
        for (Map.Entry<String, BigDecimal> entry : mapAll.entrySet()) {
            Map<String, Object> map = new HashMap<>(2);
            map.put("id", workDate + entry.getKey());
            map.put("userid", entry.getKey());
            map.put("workDate", DateUtil.getDateParse(workDate, "yyyy-MM"));
            map.put("oaTeamPieceRate", entry.getValue());
            saveList.add(map);
        }
        if (saveList.size() > 0) {
            this.baseMapper.insertBathByOaTeam(saveList);
        }
    }

    private void ansyEat(List<String> sAlls, Map<String, Object> parMap) throws ParseException {
        String workDate = (String) parMap.get("workDate");
        this.baseMapper.clearByEat(parMap); //清除当月全部数据
        List<Map<String, Object>> list = sEatMapper.eatAnsy(parMap);
        Map<String, BigDecimal> mapAll = new HashMap<>();
        for (Map<String, Object> map : list) {
            if (map.get("num") != null) {
                mapAll.put((String) map.get("id"), (BigDecimal) map.get("num"));
            }
        }
        List<Map<String, Object>> updateList = new ArrayList<>();
        List<Map<String, Object>> saveList = new ArrayList<>();
        for (String id : sAlls) {
            BigDecimal decimal = mapAll.get(id.substring(7));
            if (decimal != null) {
                Map<String, Object> map = new HashMap<>(2);
                map.put("id", id);
                map.put("eatNum", decimal);
                updateList.add(map);
                mapAll.remove(id.substring(7));
            }
            if (updateList.size() == 500) {
                this.baseMapper.updateByEat(updateList);
                updateList.clear();
            }
        }
        if (updateList.size() > 0) {
            this.baseMapper.updateByEat(updateList);
        }
        for (Map.Entry<String, BigDecimal> entry : mapAll.entrySet()) {
            Map<String, Object> map = new HashMap<>(2);
            map.put("id", workDate + entry.getKey());
            map.put("userid", entry.getKey());
            map.put("workDate", DateUtil.getDateParse(workDate, "yyyy-MM"));
            map.put("eatNum", entry.getValue());
            saveList.add(map);
        }
        if (saveList.size() > 0) {
            this.baseMapper.insertBathByEat(saveList);
        }
    }
}
