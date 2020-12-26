package cc.mrbird.febs.server.ding.service.impl;


import cc.mrbird.febs.common.core.entity.constant.PageConstant;
import cc.mrbird.febs.common.core.entity.ding.SAll;
import cc.mrbird.febs.common.core.entity.ding.SDayDetailSum;
import cc.mrbird.febs.common.core.entity.ding.SOaAward;
import cc.mrbird.febs.common.core.utils.DateUtil;
import cc.mrbird.febs.server.ding.common.constant.ConstantSalary;
import cc.mrbird.febs.server.ding.controller.req.BaseReq;
import cc.mrbird.febs.server.ding.controller.req.FlushReq;
import cc.mrbird.febs.server.ding.mapper.SAllMapper;
import cc.mrbird.febs.server.ding.mapper.SKaoqinSumMapper;
import cc.mrbird.febs.server.ding.mapper.SOaAwardMapper;
import cc.mrbird.febs.server.ding.service.ISAllService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cc.mrbird.febs.common.core.entity.QueryRequest;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

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


    @Override
    public Map<String, Object> findSAlls(QueryRequest request, BaseReq req) {
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
        if (!request.isAll()) {
            parMap.put("pageNum", (request.getPageNum() - 1) * request.getPageSize());
            parMap.put("size", request.getPageSize());
        }
        parMap.put("isAll", request.isAll());
        List<SAll> kDayDetailsSum = sAllMapper.findSAll(parMap);
        parMap.put("isAll", true);
        Long aLong = sAllMapper.countSAll(parMap);

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
        }
        log.info("同步成功!!!");
        return true;
    }

    private void ansyKaoqin(List<String> sAlls, Map<String, Object> parMap) {
        List<SDayDetailSum> sDayDetailSums = sKaoqinSumMapper.findKDayDetailsSumByPlace(parMap);
        Map<String, SDayDetailSum> kaoqinMap = new HashMap<>();
        for (SDayDetailSum sDayDetailSum : sDayDetailSums) {
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
        List<Map<String, Object>> listAdd = sOaAwardMapper.sumOaAwardAdd(parMap);
        List<Map<String, Object>> listSub = sOaAwardMapper.sumOaAwardSub(parMap);
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
}