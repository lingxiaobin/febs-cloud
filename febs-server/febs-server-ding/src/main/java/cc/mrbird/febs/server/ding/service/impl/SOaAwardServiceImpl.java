package cc.mrbird.febs.server.ding.service.impl;

import cc.mrbird.febs.common.core.entity.constant.PageConstant;
import cc.mrbird.febs.common.core.entity.ding.SAll;
import cc.mrbird.febs.common.core.entity.ding.SDayDetailSum;
import cc.mrbird.febs.common.core.entity.ding.SOaAward;
import cc.mrbird.febs.server.ding.common.constant.ConstantSalary;
import cc.mrbird.febs.server.ding.controller.req.FlushReq;
import cc.mrbird.febs.server.ding.controller.req.SKaoqinSumReq;
import cc.mrbird.febs.server.ding.controller.req.SOaAwardReq;
import cc.mrbird.febs.server.ding.mapper.SOaAwardMapper;
import cc.mrbird.febs.server.ding.mapper.SOaMapper;
import cc.mrbird.febs.server.ding.service.ISOaAwardService;
import cc.mrbird.febs.server.ding.service.ISSalarySettingService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import net.sf.cglib.beans.BeanCopier;
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
import java.util.stream.Collectors;

/**
 * Service实现
 *
 * @author MrBird
 * @date 2020-12-01 18:07:41
 */
@DS("winserver")
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SOaAwardServiceImpl extends ServiceImpl<SOaAwardMapper, SOaAward> implements ISOaAwardService {

    private final SOaAwardMapper sOaAwardMapper;

    private final SOaMapper sOaMapper;

    private final ISSalarySettingService isSalarySettingService;

    public boolean flush(FlushReq flushReq) {
        Map<String, Object> parMap = new HashMap<>();
        parMap.put("workDate", flushReq.getWorkDate());
        //原本数据信息
        List<Map<String, Object>> isUpdateList = sOaAwardMapper.selectIsUpdate(parMap);
        Set<String> deleteSet = new HashSet<>();
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
            deleteSet.add(id);
        }
        List<SOaAward> addList = new ArrayList<>();
        List<SOaAward> updateList = new ArrayList<>();

        List<Map<String, Object>> oaAwardFlush = sOaMapper.findOaAwardFlush(parMap);
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
            deleteSet.remove(sOaAward.getId().toString());   //剔除掉存在地数据
        }
        this.removeByIds(deleteSet);
        this.saveBatch(addList);
        if (updateList.size() > 0) {
            this.updateBatchById(updateList);
        }
        return true;
    }


    @Override
    public Map<String, Object> findSOaAwards(QueryRequest request, SOaAwardReq req) {
        Map<String, Object> parMap = new HashMap<>();
        parMap.put("workDate", req.getWorkDate());
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
        parMap.put("finishedflag", req.getFinishedflag());
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
        List<SOaAward> oaAwards = sOaAwardMapper.findOaAward(parMap);
        Long count = 0L;
        Map<Long, Long> map = null;
        if (!request.isAll()) {     //表格合并列
            count = sOaAwardMapper.findOaAwardCount(parMap);
            map = oaAwards.stream().
                    collect(Collectors.groupingBy(SOaAward::getUnitId, Collectors.counting()));
            for (SOaAward oaAward : oaAwards) {
                oaAward.setSpanNum(map.get(oaAward.getUnitId()) == null ? 0 : map.get(oaAward.getUnitId()).intValue());
                map.remove(oaAward.getUnitId());
            }
        }
        Map<String, Object> data = new HashMap<>(3);
        data.put(PageConstant.ROWS, oaAwards);
        data.put(PageConstant.TOTAL, count);
        data.put(PageConstant.ROW_SPAN_MAP, map);

        return data;
    }

    @Override
    public List<SOaAward> findSOaAwards(SOaAward sOaAward) {
        LambdaQueryWrapper<SOaAward> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createSOaAward(SOaAward sOaAward) {
        this.save(sOaAward);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSOaAward(SOaAward sOaAward) {
        this.updateById(sOaAward);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSOaAward(SOaAward sOaAward) {
        LambdaQueryWrapper<SOaAward> wapper = new LambdaQueryWrapper<>();
        // TODO 设置删除条件
        this.remove(wapper);
    }


}
